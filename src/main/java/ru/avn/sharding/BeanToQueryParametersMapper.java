package ru.avn.sharding;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;

public class BeanToQueryParametersMapper {

    public static MultiValueMap<String, String> map(Object bean) {
        try {
            return doMap(bean);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static MultiValueMap<String, String> doMap(Object bean) throws IllegalAccessException {
        MultiValueMap<String, String> result = new LinkedMultiValueMap<>();

        for (Field field : bean.getClass()
                .getDeclaredFields()) {

            doAccessible(field);

            mapIfIterable(result, field, bean);
            mapIfNotIterable(result, field, bean);
        }

        return result;
    }

    private static void doAccessible(Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    private static void mapIfNotIterable(MultiValueMap<String, String> result, Field field, Object bean) throws IllegalAccessException {
        if (Iterable.class.isAssignableFrom(field.getType())) {
            return;
        }

        Object object = field.get(bean);

        if (object == null) {
            return;
        }

        result.add(field.getName(), object.toString());
    }

    private static void mapIfIterable(MultiValueMap<String, String> result, Field field, Object bean) throws IllegalAccessException {
        if (!Iterable.class.isAssignableFrom(field.getType())) {
            return;
        }
        Iterable<?> iterable = (Iterable<?>) field.get(bean);

        if (iterable == null) {
            return;
        }

        String fieldName = field.getName();
        for (Object object : iterable) {
            if (object == null) {
                continue;
            }
            result.add(fieldName, object.toString());
        }

    }

}
