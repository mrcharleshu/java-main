package com.charles.common_object.hash;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HashSetString {

    public static void main(String args[]) {
        String s1 = new String("aaa");
        String s2 = new String("aaa");
        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());
        Set hashSet = new HashSet();
        hashSet.add(s1);
        hashSet.add(s2);
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
