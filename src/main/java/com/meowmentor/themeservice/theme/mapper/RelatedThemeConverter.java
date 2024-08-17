package com.meowmentor.themeservice.theme.mapper;

import com.meowmentor.themeservice.theme.RelatedTheme;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class RelatedThemeConverter implements AttributeConverter<List<RelatedTheme>, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<RelatedTheme> attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert List<RelatedTheme> to JSON", e);
        }
    }

    @Override
    public List<RelatedTheme> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, new TypeReference<List<RelatedTheme>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert JSON to List<RelatedTheme>", e);
        }
    }
}