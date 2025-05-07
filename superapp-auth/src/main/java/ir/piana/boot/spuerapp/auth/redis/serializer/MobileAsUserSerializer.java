package ir.piana.boot.spuerapp.auth.redis.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.piana.boot.spuerapp.auth.data.entity.MobileAsUserEntity;
import ir.piana.boot.spuerapp.auth.redis.OtpSessionEntry;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.IOException;

public class MobileAsUserSerializer implements RedisSerializer<MobileAsUserEntity> {

    private ObjectMapper objectMapper;

    public MobileAsUserSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] serialize(MobileAsUserEntity value) throws SerializationException {
        if (value == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsBytes(value);
        } catch (JsonProcessingException e) {
            throw new SerializationException("error to serialize OtpSessionEntry", e);
        }
    }

    @Override
    public MobileAsUserEntity deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return objectMapper.readValue(bytes, MobileAsUserEntity.class);
        } catch (IOException e) {
            throw new SerializationException("error to deserialize OtpSessionEntry", e);
        }
    }


}
