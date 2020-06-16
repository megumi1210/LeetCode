package com.megumi.util.map;

import org.junit.Test;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.util.Objects;

public class HashMapTest {
    //测试用的对象类
    public static class Person {
        int age;
        String name;
        float height;

        public Person(int age, String name, float height) {
            this.age = age;
            this.name = name;
            this.height = height;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return age == person.age &&
                    Float.compare(person.height, height) == 0 &&
                    Objects.equals(name, person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(age, name, height);
        }

        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    ", height=" + height +
                    '}';
        }
    }


    @Test
    public void testPut() {
        Person p1 = new Person(10, "jack", 1.67f);
        Person p2 = new Person(10, "jack", 1.67f);

        Map<Object, Integer> map = new HashMap<>();
        map.put(p1, 1);
        map.put(p2, 2);
        map.put("jack",2);
        map.put("rose",3);
        map.put("jack",3);
        System.out.println(map.size());

    }

    @Test
    public void testGet(){
        Person p1 = new Person(10, "jack", 1.67f);
        Person p2 = new Person(10, "jack", 1.67f);

        Map<Object, Integer> map = new HashMap<>();
        map.put(p1, 1);
        map.put(p2, 2);
        map.put("jack",3);
        map.put("rose",4);
        map.put("jack",5);
        map.put("null",6);

        System.out.println(map.get("jack"));
        System.out.println(map.get("rose"));
        System.out.println(map.get("null"));
        System.out.println(map.get(p1));
    }

    @Test
    public void testRemove(){
        Person p1 = new Person(10, "jack", 1.67f);
        Person p2 = new Person(10, "jack", 1.67f);

        Map<Object, Integer> map = new HashMap<>();
        map.put(p1, 1);
        map.put(p2, 2);
        map.put("jack",3);
        map.put("rose",4);
        map.put("jack",5);
        map.put("null",6);

        System.out.println(map.size());
        System.out.println(map.remove("jack"));
        System.out.println(map.get("jack"));
        System.out.println(map.size());

    }

    @Test
    public void testTraversal(){
        Person p1 = new Person(10, "jack", 1.67f);
        Person p2 = new Person(10, "jack", 1.67f);

        HashMap<Object, Integer> map = new HashMap<>();
        map.put(p1, 1);
        map.put(p2, 2);
        map.put("jack",3);
        map.put("rose",4);
        map.put("jack",5);
        map.put("null",6);

//        map.traversal(new Map.Visitor<Object, Integer>() {
//            @Override
//            protected boolean visit(Object key, Integer value) {
//                System.out.println(key.toString()+":" +value.toString());
//                return false;
//            }
//        });

         map.print();

    }
}
