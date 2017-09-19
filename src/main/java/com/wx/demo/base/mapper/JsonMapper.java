package com.wx.demo.base.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 简单封装Jackson，实现JSON String<->Java Object之间转换
 * 封装不同的输出风格, 使用不同的builder函数创建实例.
 */
public class JsonMapper {
    private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);

    private ObjectMapper mapper;

    public JsonMapper() {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * 将对象序列化
     * Object可以是POJO，也可以是Collection或数组。
     * 如果对象为Null, 返回"null".
     * 如果集合为空集合, 返回"[]".
     */
    public String toJson(Object object){
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.warn("write to json string error:" + object, e);
            return null;
        }
    }

    /**
     * 反序列化对象字符串
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if ((jsonString == null) || "".equals(jsonString.trim())) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return null ;
        }

    }

    /**
     * 反序列化字符串成为对象
     */
    public <T> T fromJson(String jsonString, TypeReference typeReference){
        if ((jsonString == null) || "".equals(jsonString.trim())) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return null ;
    }

    /**
     * 将对象序列化为JsonP格式
     */
    public String toJsonP(String functionName, Object object){
        return toJson(new JSONPObject(functionName, object));
    }

}
