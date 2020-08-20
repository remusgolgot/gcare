package com.gcare.utils;

import com.gcare.model.BaseDto;
import com.gcare.model.BaseEntity;
import org.springframework.beans.BeanUtils;

public class ClassUtils {
    public static <E> BaseEntity copyPropertiesFromDTO(Class<E> clazz, BaseDto baseDto) throws Exception {
        BaseEntity entity = (BaseEntity) Class.forName(clazz.getName()).getConstructor().newInstance();
        BeanUtils.copyProperties(baseDto, entity);
        return entity;

    }
}
