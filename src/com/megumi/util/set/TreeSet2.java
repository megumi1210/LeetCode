package com.megumi.util.set;

import com.megumi.util.tree.BinaryTree;
import com.megumi.util.tree.RedBlackTree;

public class TreeSet2<E> implements Set<E> {

   private RedBlackTree<E> redBlackTree = new RedBlackTree<>();

    @Override
    public int size() {
        return redBlackTree.size();
    }

    @Override
    public boolean isEmpty() {
        return redBlackTree.isEmpty();
    }

    @Override
    public void clear() {
        redBlackTree.clear();
    }

    @Override
    public boolean contains(E element) {
        return redBlackTree.contains(element);
    }

    @Override
    public void add(E element) {
         redBlackTree.add(element);
    }

    @Override
    public void remove(E element) {
       redBlackTree.add(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {

        redBlackTree.inOrderTraversal(new BinaryTree.Visitor<E>() {

            public  boolean visit(E element){
                  return visitor.visit(element);
            }

        });
    }
}
