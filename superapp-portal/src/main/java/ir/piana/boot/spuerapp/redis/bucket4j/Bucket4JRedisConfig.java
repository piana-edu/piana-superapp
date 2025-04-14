package ir.piana.boot.spuerapp.redis.bucket4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import ir.piana.boot.spuerapp.redis.OtpSessionEntry;
import ir.piana.boot.spuerapp.redis.RedisEntry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.function.Supplier;

@Configuration
public class Bucket4JRedisConfig {
    @Bean
    public ProxyManager<String> lettuceBasedProxyManager(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisClient redisClient = (RedisClient) lettuceConnectionFactory.getRequiredNativeClient();
        StatefulRedisConnection<String, byte[]> redisConnection = redisClient
                .connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE));

        return LettuceBasedProxyManager.builderFor(redisConnection)
                .withExpirationStrategy(ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(
                        Duration.ofMinutes(1L)))
                .build();
    }

    /*@Bean
    public Supplier<BucketConfiguration> bucketConfiguration() {
        return () -> BucketConfiguration.builder()
                .addLimit(Bandwidth.simple(100L, Duration.ofSeconds(5L)))
                .build();
    }*/

    @Bean
    public Supplier<BucketConfiguration> signInOtpBucketConfiguration() {
        Bandwidth bandwidth = Bandwidth.builder().capacity(1)
                .refillGreedy(1, Duration.ofSeconds(60L))
                .build();
        return () -> BucketConfiguration.builder()
                .addLimit(bandwidth)
                .build();
    }
}
