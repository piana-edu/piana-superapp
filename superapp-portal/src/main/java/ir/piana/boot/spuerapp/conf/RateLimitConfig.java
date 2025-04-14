package ir.piana.boot.spuerapp.conf;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import ir.piana.boot.spuerapp.common.ratelimit.RateLimitFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.Duration;
import java.util.function.Supplier;

@Configuration
public class RateLimitConfig {
    @Bean
    public Supplier<BucketConfiguration> bucketConfiguration() {
        return () -> BucketConfiguration.builder()
                .addLimit(Bandwidth.simple(100L, Duration.ofSeconds(5L)))
                .build();
    }

    @Bean
    @Order(1)
    RateLimitFilter rateLimitFilter(
            Supplier<BucketConfiguration> bucketConfiguration,
            ProxyManager<String> proxyManager
    ) {
        return new RateLimitFilter(bucketConfiguration, proxyManager);
    }
}
