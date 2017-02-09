package com.charles.jdk8_test;

import java.util.function.Supplier;

public class InterfaceFeatures {

    private interface Defaulable {
        // Interfaces now allow default methods, the implementer may or
        // may not implement (override) them.
        default String notRequired() {
            return "Default implementation";
        }
    }

    private static class DefaultableImpl implements Defaulable {
    }

    private static class OverridableImpl implements Defaulable {
        @Override
        public String notRequired() {
            return "Overridden implementation";
        }
    }

    private interface DefaulableFactory {
        // Interfaces now allow static methods
        static Defaulable create(Supplier<Defaulable> supplier) {
            return supplier.get();
        }
    }

    public static void main(String[] args) {
        Defaulable defaulable = DefaulableFactory.create(DefaultableImpl::new);
        System.out.println(defaulable.notRequired());

        defaulable = DefaulableFactory.create(OverridableImpl::new);
        System.out.println(defaulable.notRequired());

//        Converter<String, Integer> converter1 = (from) -> Integer.valueOf(from);
//        Java 8 允许你使用 :: 关键字来传递方法或者构造函数引用
        Converter<String, Integer> converter1 = Integer::valueOf;//引用一个静态方法
        System.out.println(converter1.convert("123"));
        System.out.println(converter1.test());
        Converter<String, Float> converter2 = new Converter<String, Float>() {
            @Override
            public Float convert(String from) {
                return Float.parseFloat(from);
            }
        };
        System.out.println(converter2.convert("123.123"));
        System.out.println(converter2.test());
    }
}

/**
 * 每一个lambda表达式都对应一个类型，通常是接口类型。而“函数式接口”是指仅仅只包含一个抽象方法的接口，
 * 每一个该类型的lambda表达式都会被匹配到这个抽象方法。因为 默认方法 不算抽象方法，所以你也可以给你的函数式接口添加默认方法。
 * 我们可以将lambda表达式当作任意只包含一个抽象方法的接口类型，确保你的接口一定达到这个要求，
 * 你只需要给你的接口添加 @FunctionalInterface 注解，编译器如果发现你标注了这个注解的接口有多于一个抽象方法的时候会报错的。
 * 需要注意如果@FunctionalInterface如果没有指定，上面的代码也是对的。
 * @param <F>
 * @param <T>
 */
@FunctionalInterface
interface Converter<F, T> {
    T convert(F from);

    default String test() {
        return "This is just a test";
    }
}
