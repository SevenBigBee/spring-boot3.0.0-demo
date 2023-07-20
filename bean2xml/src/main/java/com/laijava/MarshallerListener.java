package com.laijava;

import jakarta.xml.bind.Marshaller;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * @author
 * @date 2021年11月10日 上午10:01:32
 * @Description 做一些特殊默认值处理
 */
public class MarshallerListener extends Marshaller.Listener {
    public static final String BLANK_CHAR = "";

    @Override
    public void beforeMarshal(Object source) {
        super.beforeMarshal(source);
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                if (f.getType() == String.class && f.get(source) == null) {
                    f.set(source, BLANK_CHAR);
                } else if (f.getType() == BigDecimal.class && f.get(source) == null) {
                    f.set(source, BigDecimal.ZERO);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
