package com.charles.jdk8;

import java.util.function.UnaryOperator;

/**
 * lambda是利用接口实现的，所以首先需要有一个接口来接收lambda表达式的函数。
 * 这里我们使用UnaryOperator<T>接口来说明问题，UnaryOperator<T>接口是JAVA8内置的函数式接口，
 * UnaryOperator<T>接口接收一个T类型参数，并且返回一个T类型参数，如果递归函数的输入和输出有其他的特性，
 * 那么需要选择其他的函数式接口，JAVA8提供了一些，还可以自己实现函数式接口。
 * 按照传统的方式，我们来实现递归的逻辑，却发现会有编译错误。
 * 原因是此时的fact实际上是一个域而不是一个函数，这个域在这一行被完整的定义，
 * 在没有定义完成之前，没办法引用自身。此时可以通过this引用来指向fact。
 */
public class LambdaFactorial {

    private static UnaryOperator<Integer> factStatic =
            x -> ((x == 1 || x == 0) ? 1 : x * LambdaFactorial.factStatic.apply(x - 1));

    private UnaryOperator<Integer> factPrivate = x -> ((x == 1 || x == 0) ? 1 : x * this.factPrivate.apply(x - 1));

    private static int factorial(int x) {
        if (x == 1 || x == 0) {
            return 1;
        }
        return x * factorial(x - 1);
    }

    public static void main(String[] args) {
        System.out.println(factorial(5));
        System.out.println(factStatic.apply(5));
        System.out.println(new LambdaFactorial().factPrivate.apply(5));
    }
}
