package com.charles.common.clazz;

/**
 * Error表示系统级的错误和程序不必处理的异常，是恢复不是不可能但很困难的情况下的一种严重问题；比如内存溢出，不可能指望程序能处理这样的情况；
 * Exception表示需要捕捉或者需要程序进行处理的异常，是一种设计或实现问题；也就是说，它表示如果程序运行正常，从不会发生的情况。
 */
public class Error_Exception {

    /**
     * 2005年摩托罗拉的面试中曾经问过这么一个问题
     * “If a process reports a stack overflow run-time error, what’s the most possible cause?”，
     * 给了四个选项
     * a. lack of memory;
     * b. write on an invalid memory space;
     * c. recursive function calling;
     * d. array index out of boundary.
     * Java程序在运行时也可能会遭遇StackOverflowError，这是一个无法恢复的错误，只能重新修改代码了，
     * 这个面试题的答案是c。如果写了不能迅速收敛的递归，则很有可能引发栈溢出的错误，如下所示：
     * @param args
     */
    public static void main(String[] args) {
        // 用递归编写程序时一定要牢记两点：1. 递归公式；2. 收敛条件（什么时候就不再继续递归）。
        // main(null);
        // assertTest(0);
        try {
            try {
                throw new Sneeze();
            } catch (Annoyance a) {
                System.out.println("Caught Annoyance");
                throw a;
            }
        } catch (Sneeze s) {
            System.out.println("Caught Sneeze");
            return;
        } finally {
            // 总是执行代码块，这就意味着程序无论正常执行还是发生异常，这里的代码只要JVM不关闭都能执行，可以将释放外部资源的代码写在finally块中
            // try{}里有一个return语句，那么紧跟在这个try后的finally{}里的代码还是会被执行，在方法返回调用者前执行。
            // 在finally中改变返回值的做法是不好的，因为如果存在finally代码块，try中的return语句不会立马返回调用者，
            // 而是记录下返回值待finally代码块执行完毕之后再向调用者返回其值，然后如果在finally中修改了返回值，就会返回修改后的值。
            // 显然，在finally中返回或者修改返回值会对程序造成很大的困扰，
            // C#中直接用编译错误的方式来阻止程序员干这种龌龊的事情，Java中也可以通过提升编译器的语法检查级别来产生警告或错误，
            // Eclipse中可以在如图所示的地方进行设置，强烈建议将此项设置为编译错误。
            // Preferences -> Java -> Compiler -> Errors/Warnings -> finally doesn't complete normally
            System.out.println("Hello World!");
        }
    }

    /**
     * 断言在软件开发中是一种常用的调试方式，很多开发语言中都支持这种机制。
     * 一般来说，断言用于保证程序最基本、关键的正确性。断言检查通常在开发和测试时开启。
     * 为了保证程序的执行效率，在软件发布后断言检查通常是关闭的。
     * 断言是一个包含布尔表达式的语句，在执行这个语句时假定该表达式为true；
     * 如果表达式的值为false，那么系统会报告一个AssertionError。
     * <p>
     * 断言可以有两种形式：
     * assert Expression1;
     * assert Expression1 : Expression2 ;
     * Expression1 应该总是产生一个布尔值。
     * Expression2 可以是得出一个值的任意表达式；这个值用于生成显示更多调试信息的字符串消息。
     * 要在运行时启用断言，可以在启动JVM时使用-enableassertions或者-ea标记。
     * 要在运行时选择禁用断言，可以在启动JVM时使用-da或者-disableassertions标记。
     * 要在系统类中启用或禁用断言，可使用-esa或-dsa标记。还可以在包的基础上启用或者禁用断言。
     * @implNote 断言不应该以任何方式改变程序的状态。简单的说，如果希望在不满足某些条件时阻止代码的执行，就可以考虑用断言来阻止它。
     */
    public static void assertTest(int num) {
        assert (num > 0); // throws an AssertionError if a <= 0
    }
}

class Annoyance extends Exception {
}

class Sneeze extends Annoyance {
}