package com.charles.common.clazz;

/**
 * Static Nested Class是被声明为静态（static）的内部类，它可以不依赖于外部类实例被实例化。
 * 而通常的内部类需要在外部类实例化后才能实例化，其语法看起来挺诡异的，如下所示。
 */
public class Inner_Static_Class {

    public static void main(String[] args) {
        Poker poker = new Poker();
        poker.shuffle();                     // 洗牌
        Poker.Card c1 = poker.deal(0); // 发第一张牌
        // 对于非静态内部类Card
        // 只有通过其外部类Poker对象才能创建Card对象
        Poker.Card c2 = poker.new Card("红心", 1);    // 自己创建一张牌
        System.out.println(c1);     // 洗牌后的第一张
        System.out.println(c2);     // 打印: 红心A
    }
}

/**
 * 扑克类（一副扑克）
 */
class Poker {
    private static String[] suites = {"黑桃", "红桃", "草花", "方块"};
    private static int[] faces = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    private Card[] cards;

    /**
     * 构造器
     */
    public Poker() {
        cards = new Card[52];
        for (int i = 0; i < suites.length; i++) {
            for (int j = 0; j < faces.length; j++) {
                cards[i * 13 + j] = new Card(suites[i], faces[j]);
            }
        }
    }

    /**
     * 洗牌 （随机乱序）
     */
    public void shuffle() {
        for (int i = 0, len = cards.length; i < len; i++) {
            int index = (int) (Math.random() * len);
            Card temp = cards[index];
            cards[index] = cards[i];
            cards[i] = temp;
        }
    }

    /**
     * 发牌
     * @param index 发牌的位置
     */
    public Card deal(int index) {
        return cards[index];
    }

    /**
     * 卡片类（一张扑克）
     * [内部类]
     * @author 骆昊
     */
    public class Card {
        private String suite;   // 花色
        private int face;       // 点数

        public Card(String suite, int face) {
            this.suite = suite;
            this.face = face;
        }

        @Override
        public String toString() {
            String faceStr;
            switch (face) {
                case 1:
                    faceStr = "A";
                    break;
                case 11:
                    faceStr = "J";
                    break;
                case 12:
                    faceStr = "Q";
                    break;
                case 13:
                    faceStr = "K";
                    break;
                default:
                    faceStr = String.valueOf(face);
            }
            return suite + faceStr;
        }
    }
}

/**
 * Java中非静态内部类对象的创建要依赖其外部类对象，上面的面试题中foo和main方法都是静态方法，
 * 静态方法中没有this，也就是说没有所谓的外部类对象，因此无法创建内部类对象，如果要在静态方法中创建内部类对象，可以这样做：
 * new Outer().new Inner();
 */
//class Outer {
//
//    class Inner {}
//
//    public static void foo() { new Inner(); }
//
//    public void bar() { new Inner(); }
//
//    public static void main(String[] args) {
//        new Inner();
//    }
//}