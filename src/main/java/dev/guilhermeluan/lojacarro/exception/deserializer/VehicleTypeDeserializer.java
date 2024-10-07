package dev.guilhermeluan.lojacarro.exception.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import dev.guilhermeluan.lojacarro.model.enums.VehicleType;

import java.io.IOException;

public class VehicleTypeDeserializer extends JsonDeserializer<VehicleType> {
    @Override
    public VehicleType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String value = jsonParser.getText();
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return VehicleType.valueOf(value);
    }
}
