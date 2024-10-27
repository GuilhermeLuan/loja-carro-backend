package dev.guilhermeluan.lojacarro.exception.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import dev.guilhermeluan.lojacarro.model.enums.VehicleBrand;

import java.io.IOException;

public class VehicleBrandDeserializer extends JsonDeserializer<VehicleBrand> {
    @Override
    public VehicleBrand deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getText();
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return VehicleBrand.valueOf(value);
    }

}
