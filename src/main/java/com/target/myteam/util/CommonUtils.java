package com.target.myteam.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtils {


    public static <T> T getObjectFromStringJson(String json, Class<T> className)  {
        ObjectMapper objectMapper = new ObjectMapper();
        T obj = null;
        try {
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.registerModule(new JavaTimeModule());
            obj = objectMapper.readValue(json, className);
        } catch (Exception e) {
            log.error("e");

        }
        return obj;
    }


    public static String getJsonFromObject(Object object)  {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = null;
        if (object == null) {
            return null;
        }
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("",e);
        }
        return json;
    }

}
