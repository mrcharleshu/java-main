package com.charles.common.generic;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.List;

/**
 * GenericArrayType—— 泛型数组
 * 泛型数组，描述的是形如：A<T>[]或T[]类型
 * is either a parameterized type or a type variable.
 * @author Charles
 */
@Slf4j
public class GenericArrayTypeTest<T> {

    /**
     * 含有泛型数组的才是GenericArrayType
     * @param pTypeArray GenericArrayType type :java.util.List<java.lang.String>[];
     *                   genericComponentType:java.util.List<java.lang.String>
     * @param vTypeArray GenericArrayType type :T[];genericComponentType:T
     * @param list       ParameterizedType type :java.util.List<java.lang.String>;
     * @param strings    type :class [Ljava.lang.String;
     * @param test       type :class [Lcom.wangji.demo.GenericArrayTypeTest;
     */
    public void testGenericArrayType(List<String>[] pTypeArray, T[] vTypeArray
            , List<String> list, String[] strings, GenericArrayTypeTest[] test) {
    }

    /**
     * 1、getGenericComponentType
     * 返回泛型数组中元素的Type类型，即List<String>[] 中的 List<String>（ParameterizedTypeImpl）
     * 、T[] 中的T（TypeVariableImpl）；
     * 值得注意的是，无论是几维数组，getGenericComponentType()方法都只会脱去最右边的[]，返回剩下的值；
     */
    public static void testGenericArrayType() {
        Method[] declaredMethods = GenericArrayTypeTest.class.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.getName().startsWith("main")) {
                continue;
            }
            log.info("declare Method:" + method);
            /**
             * 获取当前参数所有的类型信息
             */
            Type[] types = method.getGenericParameterTypes();
            for (Type type : types) {
                if (type instanceof ParameterizedType) {
                    log.info("ParameterizedType type :" + type);
                } else if (type instanceof GenericArrayType) {
                    log.info("GenericArrayType type :" + type);
                    Type genericComponentType = ((GenericArrayType) type).getGenericComponentType();
                    /**
                     * 获取泛型数组中元素的类型，要注意的是：无论从左向右有几个[]并列，
                     * 这个方法仅仅脱去最右边的[]之后剩下的内容就作为这个方法的返回值。
                     */
                    log.info("genericComponentType:" + genericComponentType);
                } else if (type instanceof WildcardType) {
                    log.info("WildcardType type :" + type);
                } else if (type instanceof TypeVariable) {
                    log.info("TypeVariable type :" + type);
                } else {
                    log.info("type :" + type);
                }
            }
        }
    }

    public static void main(String[] args) {
        testGenericArrayType();
    }
}