package com.megumi.util.tree;

import com.mj.printer.BinaryTrees;
import org.junit.Test;

public class RedBlackTreeTest {

    @Test
    public void testAdd(){
        Integer[] data = new Integer[]{
              55,87,56,74,96,22,62,20,70,68,90,50
        };
        RedBlackTree<Integer> rbt = new RedBlackTree<>();

       for(int i :data){
           rbt.add(i);
           System.out.println("添加【"+i+"】:"+"");
           BinaryTrees.println(rbt);
           System.out.println("-------------------------------------------------");

       }

    }

    @Test
    public void testRemove(){
        Integer[] data = new Integer[]{
                55,87,56,74,96,22,62,20,70,68,90,50
        };
        RedBlackTree<Integer> rbt = new RedBlackTree<>();

        for(int i :data){
            rbt.add(i);
        }
        BinaryTrees.println(rbt);

        System.out.println("____________________________");

        for(int i :data){
            rbt.remove(i);
            System.out.println("删除【"+i+"】:"+"");
            BinaryTrees.println(rbt);
            System.out.println("-------------------------------------------------");
        }
    }
}
