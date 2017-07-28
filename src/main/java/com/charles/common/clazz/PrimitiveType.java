package com.charles.common.clazz;

import static com.charles.utils.LineSeparators.hyphenSeparator;

public class PrimitiveType {

    public static void main(String[] args) {
        testInteger();
        hyphenSeparator();
        testShort();
        hyphenSeparator();
        testFloat();
        hyphenSeparator();
        offset();
    }

    /**
     * 如果不明就里很容易认为两个输出要么都是true要么都是false。
     * 首先需要注意的是f1、f2、f3、f4四个变量都是Integer对象引用，所以下面的==运算比较的不是值而是引用。
     * 装箱的本质是什么呢？当我们给一个Integer对象赋一个int值的时候，会调用Integer类的静态方法valueOf，
     * 如果看看valueOf的源代码就知道发生了什么。
     * <p>
     * 简单的说，如果整型字面量的值在-128到127之间，那么不会new新的Integer对象，而是直接引用常量池中的Integer对象，
     * 所以下面的面试题中f1==f2的结果是true，而f3==f4的结果是false。
     */
    public static void testInteger() {
        Integer a = new Integer(3);
        Integer b = 3;// 将3自动装箱成Integer类型
        int c = 3;
        System.out.println(a == b);// false 两个引用没有引用同一对象
        System.out.println(a == c);// true a自动拆箱成int类型再和c比较
        System.out.println();
        Integer f1 = 100, f2 = 100, f3 = 150, f4 = 150;
        System.out.println(f1 == f2);
        System.out.println(f3 == f4);
    }

    /**
     * 对于short s1 = 1; s1 = s1 + 1;
     * 由于1是int类型，因此s1+1运算结果也是int 型，需要强制转换类型才能赋值给short型。
     * 而short s1 = 1; s1 += 1;可以正确编译，
     * 因为s1+= 1;相当于s1 = (short)(s1 + 1);其中有隐含的强制类型转换。
     */
    public static void testShort() {
        short s1 = 1;
        s1 = (short) (s1 + 1);
        System.out.println(s1);

        short s2 = 1;
        s2 += 1;
        System.out.println(s2);
    }

    /**
     * 不能写成float f=3.4;
     * 3.4是双精度数，将双精度型（double）赋值给浮点型（float）属于下转型（down-casting，也称为窄化）会造成精度损失，
     * 因此需要强制类型转换float f =(float)3.4; 或者写成float f =3.4F;。
     */
    public static void testFloat() {
        float f1 = (float) 3.4;
        float f2 = 3.4F;
        System.out.println(f1);
        System.out.println(f2);
    }

    /**
     * 用最有效率的方法计算2乘以8
     * 左移3位相当于乘以2的3次方，右移3位相当于除以2的3次方
     */
    public static void offset() {
        System.out.println(1 << 3);
        System.out.println(2 << 3);
        System.out.println(32 >> 3);
        // 首先会将5转为2进制表示形式(java中，整数默认就是int类型,也就是32位):
        // 0000 0000 0000 0000 0000 0000 0000 0101           然后左移2位后，低位补0：
        // 0000 0000 0000 0000 0000 0000 0001 0100           换算成10进制为20
        System.out.println(5 << 2);
        System.out.println(5 >> 1);
    }
}
