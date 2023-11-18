package com.run.nextjsapi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/11/17
 */
public class ObjectConvertUtil {
    private static final Logger logger = LoggerFactory.getLogger(ObjectConvertUtil.class);

    public static <T, V> List<V> convertList(List<T> sourceList, Class<V> targetClass, BiConsumer<T, V> extraMapping) {
        return sourceList.stream()
                .map(source -> convertObject(source, targetClass, extraMapping))
                .collect(Collectors.toList());
    }

    public static <T, V> V convertObject(T source, Class<V> targetClass, BiConsumer<T, V> extraMapping) {
        try {
            V target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            if (extraMapping != null) {
                extraMapping.accept(source, target);
            }
            return target;
        } catch (Exception e) {
            throw new RuntimeException("convert object exception", e);
        }
    }
}
