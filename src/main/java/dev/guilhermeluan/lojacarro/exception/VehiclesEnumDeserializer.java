package dev.guilhermeluan.lojacarro.exception;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import dev.guilhermeluan.lojacarro.model.enums.VehicleBrand;

import java.io.IOException;

public class VehiclesEnumDeserializer extends JsonDeserializer<VehicleBrand> {
    @Override
    public VehicleBrand deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String value = jsonParser.getText();
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return VehicleBrand.valueOf(value);
    }
}
