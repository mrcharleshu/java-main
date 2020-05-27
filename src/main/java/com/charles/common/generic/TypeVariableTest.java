package com.charles.common.generic;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

/**
 * Interface TypeVariable<D extends GenericDeclaration> ，
 * D - the type of generic declaration that declared the underlying type variable.
 * 类型变量是类型变量的公共超接口。类型变量是第一次使用反射方法创建的，如在这个包中指定的。
 * 如果类型变量T由类型（即类、接口或注释类型）T引用，并且T由第n个封闭类T（参见JLS.1.2）来声明，
 * 那么T的创建需要T的第i个包围类的分辨率（参见JVMS 5），对于i＝0到n，包含。创建类型变量不能导致其边界的创建。
 * 重复创建类型变量没有任何效果。
 * <p>
 * 可以在运行时实例化多个对象以表示给定的类型变量。即使类型变量只创建一次，
 * 但这并不意味着缓存表示类型变量的实例的任何要求。
 * 但是，表示一个类型变量的所有实例必须是相等的（）。因此，类型变量的用户不能依赖实现该接口的类实例的标识。
 * <p>
 * 泛型的类型变量，指的是List<T>、Map<K,V>中的T，K，V等值，实际的Java类型是TypeVariableImpl
 * （TypeVariable的子类）；
 * 此外，还可以对类型变量加上extend限定，这样会有类型变量对应的上限；值得注意的是，类型变量的上限可以为多个，
 * 必须使用&符号相连接，例如 List<T extends Number & Serializable>；其中，& 后必须为接口；
 * 没有指定的话 ，V 的 上边界 属于  Object
 * @author Charles
 */
@Slf4j
public class TypeVariableTest<T extends Number & Serializable, V> {
    /**
     * TypeVariable
     */
    private T key;

    /**
     * TypeVariable
     */
    private V value;

    /**
     * GenericArrayType V[]-> V TypeVariable 两种混合起来了
     */
    private V[] values;
    /**
     * 原始类型，不仅仅包含我们平常所指的类，还包括枚举、数组、注解等；
     * 基本类型，也就是我们所说的java的基本类型，即int,float,double等
     */
    private String str;

    /**
     * 获取ParameterizedType List<T> -> T TypeVariable 两种混合起来了
     */
    private List<T> tList;

    /**
     * 从这个例子中可以看出来各种类型之间是相互在使用的
     * TypeVariable<D extends GenericDeclaration>
     * GenericDeclaration  All Known Implementing Classes: Class, Constructor, Method
     */
    public static void testTypeVariableTest() {
        Field f = null;
        try {
            Field[] fields = TypeVariableTest.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                f = fields[i];
                if (f.getName().equals("log")) {
                    continue;
                }
                log.info("begin ******当前field:" + f.getName() + " *************************");
                if (f.getGenericType() instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) f.getGenericType();
                    for (Type type : parameterizedType.getActualTypeArguments()) {
                        log.info(f.getName() + ": 获取ParameterizedType:" + type);
                        if (type instanceof TypeVariable) {
                            printTypeVariable(f.getName(), (TypeVariable) type);
                        }
                    }
                    if (parameterizedType.getOwnerType() != null) {
                        log.info(f.getName() + ":getOwnerType:" + parameterizedType.getOwnerType());
                    } else {
                        log.info(f.getName() + ":getOwnerType is null");
                    }
                    if (parameterizedType.getRawType() != null) {
                        log.info(f.getName() + ":getRawType:" + parameterizedType.getRawType());
                    }
                } else if (f.getGenericType() instanceof GenericArrayType) {
                    GenericArrayType genericArrayType = (GenericArrayType) f.getGenericType();
                    log.info("GenericArrayType type :" + genericArrayType);
                    Type genericComponentType = genericArrayType.getGenericComponentType();
                    if (genericComponentType instanceof TypeVariable) {
                        TypeVariable typeVariable = (TypeVariable) genericComponentType;
                        printTypeVariable(f.getName(), typeVariable);
                    }
                } else if (f.getGenericType() instanceof TypeVariable) {
                    TypeVariable typeVariable = (TypeVariable) f.getGenericType();
                    printTypeVariable(f.getName(), typeVariable);
                } else {
                    log.info("type :" + f.getGenericType());
                }
                log.info("end ******当前field:" + f.getName() + " *************************");
            }
        } catch (Exception e) {
            log.error("error", e);
        }
    }

    /**
     * 1、Type[] getBounds() 类型对应的上限，默认为Object
     * 2、D getGenericDeclaration()  获取声明该类型变量实体，也就是TypeVariableTest<T>中的TypeVariableTest
     * 3、String getName() 获取类型变量在源码中定义的名称；
     * @param fieldName
     * @param typeVariable
     */
    private static void printTypeVariable(String fieldName, TypeVariable typeVariable) {
        for (Type type : typeVariable.getBounds()) {
            log.info(fieldName + ": TypeVariable getBounds " + type);
        }
        log.info("定义Class getGenericDeclaration: " + typeVariable.getGenericDeclaration());
        log.info("getName: " + typeVariable.getName());
    }

    public static void main(String[] args) {
        testTypeVariableTest();
    }

}