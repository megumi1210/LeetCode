package com.megumi.util.tree;

import com.mj.printer.BinaryTrees;
import org.junit.Test;

public class AVLTreeTest {

    @Test
    public void testAdd(){
        Integer[] data = new Integer[]{
                85,19,69,3,7,99,95
        };
        AVLTree<Integer>  avlTree= new AVLTree<>();

        for(int i : data){
            System.out.println("添加【" + i+"】:");
            avlTree.add(i);
            BinaryTrees.println(avlTree);
            System.out.println("--------------------------------------");
        }


    }

    @Test
    public void testRemove(){

        Integer[] data = new Integer[]{
                85,19,69,3,7,99,95
        };
        AVLTree<Integer>  avlTree= new AVLTree<>();

        for(int i : data){
            avlTree.add(i);
        }
        BinaryTrees.println(avlTree);
        System.out.println("______________________________");

        for(int i : data){
            System.out.println("删除【" + i+"】:");
            avlTree.remove(i);
            BinaryTrees.println(avlTree);
            System.out.println("--------------------------------------");
        }

    }


}
