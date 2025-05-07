package ir.piana.boot.spuerapp.auth.redis;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ir.piana.boot.spuerapp.auth.data.entity.MobileAsUserEntity;
import ir.piana.boot.spuerapp.auth.dto.AuthenticatedUser;
import ir.piana.boot.spuerapp.auth.dto.AuthenticatedUserMixin;
import ir.piana.boot.spuerapp.auth.dto.SecurityContextImplMixin;
import ir.piana.boot.spuerapp.auth.redis.serializer.MobileAsUserSerializer;
import ir.piana.boot.spuerapp.auth.redis.serializer.OtpSessionEntrySerializer;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.jackson2.SecurityJackson2Modules;

@Configuration
public class RedisEntryConfig implements BeanClassLoaderAware {
    private ClassLoader loader;

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        ObjectMapper om = JsonMapper.builder()
                .findAndAddModules()
                .activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.PROPERTY)
                .build();

//        ObjectMapper om = new ObjectMapper();
        om.registerModules(SecurityJackson2Modules.getModules(this.loader));
        om.addMixIn(AuthenticatedUser.class, AuthenticatedUser.class);
        om.addMixIn(SecurityContextImpl.class, SecurityContextImplMixin.class);
        return new GenericJackson2JsonRedisSerializer(om);
    }

    @Bean
    public RedisEntry<OtpSessionEntry> redisOtpSessionEntry (LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, OtpSessionEntry> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);

        ObjectMapper om = new ObjectMapper();
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());

        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(om));
        template.setValueSerializer(new OtpSessionEntrySerializer(om));
//        template.setValueSerializer(new GenericJackson2JsonRedisSerializer(om));

        template.afterPropertiesSet();
        return new RedisEntry<>("sign-in-otp", 120, template);
    }

    @Bean
    public RedisEntry<MobileAsUserEntity> redisMobileAsUserEntity (LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, MobileAsUserEntity> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);

        ObjectMapper om = new ObjectMapper();
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());

        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(om));
        template.setValueSerializer(new MobileAsUserSerializer(om));
//        template.setValueSerializer(new GenericJackson2JsonRedisSerializer(om));

        template.afterPropertiesSet();
        return new RedisEntry<>("mobile-as-user", 120, template);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.loader = classLoader;
    }
}
