package com.megumi.util.tree;

import com.mj.printer.BinaryTreeInfo;
import java.util.LinkedList;
import java.util.Queue;


/**
 *      二叉树
 * @param <E>  存储的元素 E
 *     没有插入 和删除逻辑
 */
public  abstract class BinaryTree<E> implements BinaryTreeInfo {//接口用于打印

    protected int size;

    //树的根节点
    protected Node<E> root;


    //返回树的元素个数
    public int size() {
        return size;
    }

    //是否为空
    public boolean isEmpty() {
        return size == 0;
    }

  //清空树的元素
    public void clear() {
        root = null;
        size = 0;
    }


    //创建节点
    protected  Node<E> createNode(E element, Node<E> parent){
        return new Node<>(element, parent);
    }
    //树的节点
   protected static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        //判断是否是叶子
        public boolean isLeaf() {
            return left == null && right == null;
        }

        //是否有两个字节点 即度为2
        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        //是左子节点
        public boolean isLeftChild(){
            return  parent !=null && parent.left ==this;
        }

        //是右子节点
        public  boolean isRightChild(){
            return  parent !=null && parent.right ==this;
        }

        //返回兄弟节点
        public Node<E> sibling(){
            if(isLeftChild()){
                return parent.right;
            }

            if(isRightChild()){
                return  parent.left;
            }

            return null;
        }

        @Override
        public String toString() {
            return  element.toString();
        }
    }

    //访问者模式，对外提供的数据访问规则，行为参数化
    public static abstract class Visitor<E> {
         boolean stop = false;

        /**
         * @param element 要遍历的元素
         * @return 返回true 停止遍历 ，false 继续遍历
         */
        protected abstract boolean visit(E element);


    }

    //返回前驱节点 讨论3钟情况 无论是后继还是前驱节点它的度都为1
    protected Node<E> predecessor(Node<E> node) {
        if (node == null) return null;

        //前驱节点在左子树中
        if (node.left != null) { //左节点不为空，前驱节点为右节点为空的节点
            Node<E> p = node.left;
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }
        //node.left ==null
        //前驱为叶子节点,不在左子树中，反复网上找 父节点直到为父节点的右子树中
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }

        // node.parent = null
        // node == node.parent.right

        return node.parent;
    }

    //返回后继节点 原理同前驱节点
    protected Node<E> successor(Node<E> node) {
        if (node == null) return null;
        //后继节点在右子树中
        if (node.right != null) {
            Node<E> p = node.right;
            while (p.left != null) {//循环遍历左子树 找后继
                p = p.left;
            }
            return p;
        }

        // node.right ==null
        //从父节点中找 后继节点
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }
        // node.parent == null
        // node == node.parent.left
        return node.parent;
    }


    /*---------------------------------------------------------------------------------*/
    /*                         树的遍历                                                 */
    /*---------------------------------------------------------------------------------*/
    //对外提供的接口
    //树的前序遍历
    public void preOrderTraversal(Visitor<E> visitor) {
        if (visitor == null) return;
        preOrderTraversal(root, visitor);
    }

    //树的前序遍历
    private void preOrderTraversal(Node<E> root, Visitor<E> visitor) {
        if (root == null || visitor.stop) return;
        if (visitor.stop) return;//用于停止visit
        visitor.stop = visitor.visit(root.element);
        preOrderTraversal(root.left, visitor);
        preOrderTraversal(root.right, visitor);

    }

    //对外提供的接口
    //树的中序遍历
    public void inOrderTraversal(Visitor<E> visitor) {
        if (visitor == null) return;
        inOrderTraversal(root, visitor);
    }

    //树的中序遍历
    private void inOrderTraversal(Node<E> root, Visitor<E> visitor) {
        if (root == null || visitor.stop) return;
        inOrderTraversal(root.left, visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(root.element);
        inOrderTraversal(root.right, visitor);
    }

    //对外提供的接口
    //树的后序遍历
    public void postOrderTraversal(Visitor<E> visitor) {
        if (visitor == null) return;
        postOrderTraversal(root, visitor);
    }

    //树的后序遍历
    private void postOrderTraversal(Node<E> root, Visitor<E> visitor) {
        if (root == null || visitor.stop) return;
        postOrderTraversal(root.left, visitor);
        postOrderTraversal(root.right, visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(root.element);

    }

    //树的层序遍历
    //对外提供的接口
    public void levelOrderTraversal(Visitor<E> visitor) {
        if (visitor == null) return;
        levelOrderTraversal(root, visitor);
    }

    //树的层序遍历
    private void levelOrderTraversal(Node<E> root, Visitor<E> visitor) {
        if (root == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (visitor.visit(node.element)) return;
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    //层序拿高度树的高度
    //对外提供的接口
    public int getHeightByLevel() {
        return getHeightByLevel(root);
    }

    //层序拿高度树的高度
    private int getHeightByLevel(Node<E> root) {
        if (root == null) return 0;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(root);
        int height = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node<E> node = queue.poll();

                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            height++;
        }
        return height;
    }

    //层序拿高度树的高度
    //对外提供的接口
    public int getHeightByPreOder() {
        return getHeightByPreOder(root);
    }

    //层序拿高度树的高度
    private int getHeightByPreOder(Node<E> root) {
        if (root == null) return 0;
        int left = getHeightByPreOder(root.left);
        int right = getHeightByPreOder(root.right);
        return Math.max(left, right) + 1;
    }


    /*---------------------------------------------------------------------------------*/
    /*                          打印工具类的接口                                         */
    /*---------------------------------------------------------------------------------*/

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        return ((Node<E>) node).element;
    }

}
