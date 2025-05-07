package ir.piana.boot.spuerapp.auth.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
public class RedisEntry<T> {
    private final String uniqueKey;
    private final int ttl;
    private final RedisTemplate<String, T> redisTemplate;

    public Optional<T> get(final String sessionId) {
        return Optional.ofNullable(redisTemplate.opsForValue()
                .get(getUniqueSessionKey(sessionId)));
    }

    public Optional<T> getAndDelete(final String sessionId) {
        return Optional.ofNullable(redisTemplate.opsForValue()
                .getAndDelete(getUniqueSessionKey(sessionId)));
    }

    public void put(final String sessionId, T template) {
        redisTemplate.opsForValue().set(
                getUniqueSessionKey(sessionId),
                template,
                getDuration());
    }

    private String getUniqueSessionKey(String sessionId) {
        return getUniqueSessionKey(uniqueKey, sessionId);
    }

    private Duration getDuration() {
        return Duration.ofSeconds(ttl);
    }

    public static String getUniqueSessionKey(String uniqueKey, String sessionId) {
        return String.format("%s:%s", uniqueKey, sessionId);
    }
}
