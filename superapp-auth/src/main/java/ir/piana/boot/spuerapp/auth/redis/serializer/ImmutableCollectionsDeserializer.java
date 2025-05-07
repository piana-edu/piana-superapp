package ir.piana.boot.spuerapp.auth.redis.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImmutableCollectionsDeserializer extends JsonDeserializer<List> {
    @Override
    public List deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode node = mapper.readTree(jsonParser);
        List<Object> resultSet = new ArrayList<>();
        if (node != null) {
            if (node instanceof ArrayNode) {
                ArrayNode arrayNode = (ArrayNode) node;
                Iterator<JsonNode> nodeIterator = arrayNode.iterator();
                while (nodeIterator.hasNext()) {
                    JsonNode elementNode = nodeIterator.next();
                    resultSet.add(mapper.readValue(elementNode.traverse(mapper), Object.class));
                }
            } else {
                resultSet.add(mapper.readValue(node.traverse(mapper), Object.class));
            }
        }
        return resultSet;
    }
}
