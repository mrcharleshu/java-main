package com.charles.lib.guava;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Maps集合类扩展使用
 */
public class MapsTest {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Person p1 = new Person("001", "Charles");
        Person p2 = new Person("002", "Lily");

        List<Person> personList = Lists.newArrayList(p1, p2);

        // 将主键当作Map的Key
        // Map<String, Person> personMap = Maps.uniqueIndex(personList.iterator(), new Function<Person, String>() {
        //    @Override
        //    public String apply(Person input) {
        //        return input.getId();
        //    }
        // });
        Map<String, Person> personMap = Maps.uniqueIndex(personList.iterator(), Person::getId);
        System.out.println("将主键当作Map的Key:" + personMap);
        // 转换Map中的value值
        Map<String, String> transformValuesMap = Maps.transformValues(personMap, Person::getName);
        System.out.println("转换Map中的value值" + transformValuesMap);

        // 可以说是Maps.uniqueIndex相反的作用
        Set<Person> personSet = Sets.newHashSet(p1, p2);
        Map<Person, String> personAsMap = Maps.asMap(personSet, (Function) input -> ((Person) input).getId());
        System.out.println(personAsMap);
    }

    private static class Person {
        private String Id;
        private String name;

        Person(String Id, String name) {
            this.Id = Id;
            this.name = name;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" + "Id='" + Id + '\'' + ", name='" + name + '\'' + '}';
        }
    }
}

