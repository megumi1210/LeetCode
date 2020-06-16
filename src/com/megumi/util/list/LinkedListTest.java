package com.megumi.util.list;

import org.junit.Test;


public class LinkedListTest {
    @Test
    public void test(){
//        LinkedList<ArrayListTest.Person> list = new LinkedList<>();
//
//        list.add(new ArrayListTest.Person(10,"mike"));
//        list.add(new ArrayListTest.Person(11,"tom"));
//        list.add(new ArrayListTest.Person(12,"cat"));
        LinkedList<Integer> list = new LinkedList<>();

        for(int i = 0;i<10;i++){
            list.add(i);
        }

        System.out.println(list);
    }
}
