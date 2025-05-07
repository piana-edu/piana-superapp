package ir.piana.boot.spuerapp.auth.redis.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.piana.boot.spuerapp.auth.redis.OtpSessionEntry;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.IOException;

public class OtpSessionEntrySerializer implements RedisSerializer<OtpSessionEntry> {

    private ObjectMapper objectMapper;

    public OtpSessionEntrySerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] serialize(OtpSessionEntry value) throws SerializationException {
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
    public OtpSessionEntry deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return objectMapper.readValue(bytes, OtpSessionEntry.class);
        } catch (IOException e) {
            throw new SerializationException("error to deserialize OtpSessionEntry", e);
        }
    }


}
