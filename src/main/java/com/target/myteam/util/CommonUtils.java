package com.target.myteam.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CommonUtils {


    public static <T> T getObjectFromStringJson(String json, Class<T> className)  {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        T obj = null;
        try {
            objectMapper.registerModule(new JavaTimeModule());
            obj = objectMapper.readValue(json, className);
        } catch (Exception e) {
            log.error("e", e);

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


    public static String readContentFromFile(String resourceFilePath){

        String data = "";
        ClassPathResource cpr = new ClassPathResource(resourceFilePath);
        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            data = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.warn("IOException", e);
            throw new RuntimeException("exception occurred");
        }

        return data;
    }

}
