package t1.openschool.jwtdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import t1.openschool.jwtdemo.service.security.RefreshTokenService;

import java.time.Duration;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class SchedulingConfig implements SchedulingConfigurer {
    private final RefreshTokenService refreshTokenService;
    private final Duration refreshTokenExpiration;

    public SchedulingConfig(RefreshTokenService refreshTokenService, @Value("${jwt.refreshTokenExpiration}") Duration refreshTokenExpiration) {
        this.refreshTokenService = refreshTokenService;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(1));
        taskRegistrar.addFixedRateTask(() -> refreshTokenService.deleteExpiredTokens(), refreshTokenExpiration.toMillis());
    }
}
