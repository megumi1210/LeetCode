package com.megumi.util.list;

import org.junit.Test;

public class ArrayListTest {

    public static  class  Person{
        private int age;
        private String name;

        public Person(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{age=" + age +", name=" + name  + "}";

        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println(this.name +"被清空");
        }
    }

    @Test
    public void test(){
        ArrayList<Integer> list = new ArrayList<>();

        System.out.println();

    }


}
