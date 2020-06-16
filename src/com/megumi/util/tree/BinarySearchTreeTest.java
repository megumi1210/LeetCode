package com.megumi.util.tree;


import com.mj.printer.BinaryTrees;
import org.junit.Test;


public class BinarySearchTreeTest {

    public static class Person implements Comparable<Person> {
        private int age;
        private String name;

        public Person(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{age=" + age + ", name=" + name + "}";

        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println(this.name + "被清空");
        }

        @Override
        public int compareTo(Person o) {//根据年龄比较大小
            if (this.age < o.age) {
                return -1;
            } else if (this.age > o.age) {
                return 1;
            } else {
                return 0;
            }

        }
    }

   public  Integer[] data = new Integer[]{
           7, 4, 9, 2, 5, 8, 11, 3
   };

    BinarySearchTree<Integer> bst = new BinarySearchTree<>();


    @Test
    public void testAdd() {

        for (int i : data) {
            System.out.println("添加【" + i+"】:");
            bst.add(i);
            BinaryTrees.println(bst);
            System.out.println("--------------------------------------");

        }

        BinaryTrees.println(bst);

    }


    @Test
    public void testRemove(){

        for(int i : data){
           bst.add(i);
        }
        BinaryTrees.println(bst);
        System.out.println("______________________________");

        for(int i : data){
            System.out.println("删除【" + i+"】:");
            bst .remove(i);
            BinaryTrees.println(bst);
            System.out.println("--------------------------------------");
        }
    }

    @Test  //测试插入person 对象
    public void testAddObject(){

        Person[] persons = new Person[]{
                new Person(7, "mike"),
                new Person(4, "tom"),
                new Person(9, "cat"),
                new Person(2, "kim"),
                new Person(5, "jack"),
                new Person(8, "ming"),
                new Person(11, "dog"),
                new Person(3, "pig")
        };

        //测试对象类
        BinarySearchTree<Person> bst2 = new BinarySearchTree<>();
        for (Person p : persons) {
            bst2.add(p);
        }
        BinaryTrees.println(bst2);
    }

    @Test
    public void testTraversal(){


        for (int i : data) {
            bst.add(i);
        }

        BinaryTrees.println(bst);
        System.out.println("______________________________");

        //前序
        bst.preOrderTraversal(new BinaryTree.Visitor<Integer>() {
            @Override
            protected boolean visit(Integer element) {
                System.out.print(element+" ");
                return element == 7;
            }
        });
        System.out.println("--------------------------------");

        //中序
        bst.inOrderTraversal(new BinaryTree.Visitor<Integer>() {
            @Override
            protected boolean visit(Integer element) {
                System.out.print(element+" ");
                return element == 3;
            }
        });
        System.out.println("--------------------------------");
        //后序
        bst.postOrderTraversal(new BinaryTree.Visitor<Integer>() {
            @Override
            protected boolean visit(Integer element) {
                System.out.print(element+" ");
                return element == 5;
            }
        });
        System.out.println("--------------------------------");
        //层序
        bst.levelOrderTraversal(new BinaryTree.Visitor<Integer>() {
            @Override
            protected boolean visit(Integer element) {
                System.out.print(element+" ");
                return element == 2;
            }
        });
        System.out.println("--------------------------------");




    }

    @Test
    public void testOthers(){

        for (int i : data) {
            bst.add(i);
        }

        BinaryTrees.println(bst);
        System.out.println("______________________________");

        System.out.println(bst.getHeightByPreOder());

        System.out.println(bst.getHeightByLevel());

        System.out.println(bst.contains(11));
    }

    @Test
    public void testSuccessor(){

        for(int i : data){
            bst.add(i);
        }
        BinaryTrees.println(bst);
        System.out.println("______________________________");

        System.out.println( bst.successor(bst.node(11)));


    }
}
