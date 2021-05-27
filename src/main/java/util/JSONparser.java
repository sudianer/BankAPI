package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class JSONparser {
    private static ObjectMapper objectMapper = getObjectMapper();

    private static ObjectMapper getObjectMapper(){
        ObjectMapper defaultObjectMapper = new ObjectMapper();

        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return defaultObjectMapper;
    }

    public static JsonNode parse(String source) {
        try {
            return objectMapper.readTree(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode parse(File json){
        Scanner scanner = null;
        try {
            scanner = new Scanner(json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
        StringBuilder source = new StringBuilder();

        while(true){
            assert scanner != null;
            if (!scanner.hasNext()) break;
            source.append(scanner.nextLine());
        }
        return parse(source.toString());
    }

    public static<T> List<T> parse(File json, Class T) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, T));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static<A> A fromJson(JsonNode node, Class<A> clazz) {
        try {
            return objectMapper.treeToValue(node,clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode toJson(Object object){
        return objectMapper.valueToTree(object);
    }

    public static String toJsonString(JsonNode node){
        ObjectWriter objectWriter = objectMapper.writer();
        objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        String result = "";
        try {
            result = objectWriter.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String toJsonString(Object object){
        JsonNode node = toJson(object);
        return toJsonString(node);
    }
}
