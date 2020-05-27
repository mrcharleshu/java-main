package com.charles.common.generic;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ParameterizedType 使用测试 参数化类型
 * @author Charles
 */
@Slf4j
public class ParameterizedTypeTest {

    /**
     * 1、map: 获取ParameterizedType:class java.lang.String
     * 2、map: 获取ParameterizedType:class com.wangji.demo.ParameterizedTypeTest
     * 3、map:getOwnerType is null
     * 4、map:getRawType:interface java.util.Map
     */
    private Map<String, ParameterizedTypeTest> map;
    /**
     * 1、set1: 获取ParameterizedType:class java.lang.String
     * 2、set1:getOwnerType is null
     * 3、set1:getRawType:interface java.util.Set
     */
    private Set<String> set1;
    /**
     * 1、 clz: 获取ParameterizedType:?
     * 2、 clz:getOwnerType is null
     * 3、clz:getRawType:class java.lang.Class
     */
    private Class<?> clz;
    /**
     * 1、holder: 获取ParameterizedType:class java.lang.String
     * 2、holder:getOwnerType:class com.wangji.demo.ParameterizedTypeTest
     * 3、holder:getRawType:class com.wangji.demo.ParameterizedTypeTest$Holder
     */
    private Holder<String> holder;

    /**
     * 1、list: 获取ParameterizedType:class java.lang.String
     * 2、list:getOwnerType is null
     * 3、list:getRawType:interface java.util.List
     */
    private List<String> list;
    /**
     * str:is not ParameterizedType
     */
    private String str;
    /**
     * i:is not ParameterizedType
     */
    private Integer i;
    /**
     * set:is not ParameterizedType
     */
    private Set set;
    /**
     * aList:is not ParameterizedType
     */
    private List aList;
    /**
     * 1、entry: 获取ParameterizedType:class java.lang.String
     * 2、entry: 获取ParameterizedType:class java.lang.String
     * 3、entry:getOwnerType:interface java.util.Map
     * 4、entry:getRawType:interface java.util.Map$Entry
     */
    private Map.Entry<String, String> entry;

    static class Holder<V> {

    }

    public static void testParameterizedType() {
        Field f = null;
        try {
            Field[] fields = ParameterizedTypeTest.class.getDeclaredFields();
            for (Field field : fields) {
                f = field;
                if (f.getName().equals("log")) {
                    continue;
                }
                if (f.getGenericType() instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) f.getGenericType();
                    for (Type type : parameterizedType.getActualTypeArguments()) {
                        log.info(f.getName() + ": 获取ParameterizedType:" + type);
                    }
                    if (parameterizedType.getOwnerType() != null) {
                        log.info(f.getName() + ":getOwnerType:" + parameterizedType.getOwnerType());
                    } else {
                        log.info(f.getName() + ":getOwnerType is null");
                    }
                    if (parameterizedType.getRawType() != null) {
                        log.info(f.getName() + ":getRawType:" + parameterizedType.getRawType());
                    }
                } else {
                    log.info(f.getName() + ":is not ParameterizedType ");
                }
            }
        } catch (Exception e) {
            log.error("error", e);
        }
    }

    public static void main(String[] args) {
        testParameterizedType();
    }

}