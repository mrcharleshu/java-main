package com.charles.common.fp;

public class FunctionAsParameter {
    // 例如我们有这么个接口
    interface StringFunction {
        String apply(String s);
    }

    // 还有这么个函数
    public String run(StringFunction func) {
        return func.apply("Hello World");
    }

    public static void main(String[] args) {
        // 现在就可以把“匿名函数”当做参数传递给另外一个函数了
        FunctionAsParameter fap = new FunctionAsParameter();
        System.out.println(fap.run(s -> s.toUpperCase()));
        // 如果我们传递进去一个别的Lambda 表达式：
        System.out.println(fap.run(s -> s.toLowerCase()));

        // 当我们用s -> s.toUpperCase() 去调用的时候，就会知道 s 就是apply的参数，
        // s.toUpperCase()其实就是这个方法的实现了， 这个方法的返回值也是String

        // 其实就是我们之前的匿名类嘛，我们完全可以这么写
        System.out.println(fap.run(new StringFunction() {
            public String apply(String s) {
                return s.toUpperCase();
            }
        }));
    }
}