package uz.ollobergan.appsubscriber.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.ollobergan.appsubscriber.dto.DemoBigDto;

public class ReportMapper {

    public static DemoBigDto jsonToDto(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(json, DemoBigDto.class);
    }
}
