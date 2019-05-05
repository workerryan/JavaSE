package com.dragon.test;

import com.alibaba.fastjson.JSONObject;
import com.dragon.model.Person;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author wanglei
 * @since 1.0.0
 */
public class Main {
    public static void main(String[] args) {
        Person peron = new Person(18, "李四", 18L, 28.8D, new Date(), 32.5F);

        Map<String, String> params = JSONObject.parseObject(JSONObject.toJSONString(peron), Map.class);
        System.out.println(params);

        System.out.println(Main.objectToMap(peron));

    }

    public static Map<String, String> objectToMap(Object obj){
        Map<String, String> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            try{
                Object value = field.get(obj);
                if(value instanceof  Date){
                    map.put(fieldName, String.valueOf(((Date) value).getTime()));
                }else{
                    map.put(fieldName, value.toString());
                }
            }catch (Exception e ){

            }
        }
        return map;
    }

}
