package com.mcoding.base.core.orm;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wzt on 2020/2/11.
 * @version 1.0
 */
public class MetaModelUtils {

    /**
     * 根据Class定义生成模型属性
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Map<String, MetaModelField> generateMetaModelField(Class<T> clazz) {

        Field[] fields = ReflectUtil.getFields(clazz);

        Map<String, MetaModelField> result = new HashMap<>(fields.length);

        for (Field field : fields) {
            TableField tableField = field.getAnnotation(TableField.class);
            TableId tableId = field.getAnnotation(TableId.class);

            if (tableField == null && tableId == null) {
                continue;
            }

            String tableFieldName = tableField != null ? tableField.value() : tableId.value();

            result.put(field.getName(), new MetaModelField(tableFieldName, field.getType().getTypeName()));
        }

        return result;
    }

}
