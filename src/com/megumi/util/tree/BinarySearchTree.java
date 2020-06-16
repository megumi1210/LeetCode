package com.megumi.util.tree;

import java.util.Comparator;

/**
 *
                 二叉搜索树

       @author megumi
       @version 1.0
 */


public class BinarySearchTree<E>  extends  BinaryTree<E> implements Tree<E>{


    //可以接收自定义比较规则的比较器
   private Comparator<E> comparator;



    public BinarySearchTree() {
        this(null);
    }


    //比较规则可以由外传入
    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    //添加元素
    @Override
    public void add(E element) {//值相同的情况不处理
        elementNotNullCheck(element);

        //添加第一个节点
        if (root == null) {
            root = createNode(element,null);
            size++;
            afterAdd(root);
            return;
        }

        //不是第一个节点
        //找到父节点
        Node<E> node = root;

        Node<E> parent = null;
        int cmp = 0;

        while (node != null) {
            cmp = compare(element, node.element);
            parent = node;
            if (cmp > 0) {//大于根节点的值
                node = node.right;

            } else if (cmp < 0) {//小于根节点的值
                node = node.left;
            } else {//相等
                return;
            }
        }
        //node 为 null
        //看插入到父节点的哪里
        Node<E> newNode = createNode(element,parent);
        if (cmp > 0) { //大于根节点
            parent.right = newNode;
        } else if (cmp < 0) {//小于根节点
            parent.left = newNode;
        } else {//相等的时候
            //node.element = element;
        }
        size++;
        //新添加之后的实现
        afterAdd(newNode);
    }

    //删除元素
    @Override
    public void remove(E element) {
        remove(node(element));
    }


    //是否包含某个元素
    @Override
    public boolean contains(E element) {

        return node(element) != null;
    }

    //删除某个节点 讨论3种情况 度为 2，1，0
    private void remove(Node<E> node) {
        if (node == null) return;
        size--;
        if (node.hasTwoChildren()) {//度为2的节点 删除时可以找前驱或者后继节点
            //找到后继节点
            Node<E> s = successor(node);
            //用后继节点的值覆盖度为2节点的值
            node.element = s.element;
            //删除后继节点
            node = s; // 变更要被删除的节点为 后继节点
        }
        // node 的度为1
        //删除node
        //删除度为一的节点用字节点取代父节点的位置
        Node<E> replacement = node.left != null ? node.left : node.right;

        if (replacement != null) { //node度为1的节点
            //更改parent
            replacement.parent = node.parent;
            //更改parent的 left 或者right 的指针
            if (node.parent == null) { //node是度为1的节点 而且为根节点
                root = replacement;
            } else if (node == node.parent.left) { //父类的left 或者 right 指向 child(replacement)
                node.parent.left = replacement;
            } else {//node == node.parent.right
                node.parent.right = replacement;
            }
            //删除节点后的处理
            afterRemove(replacement);
        } else if (node.parent == null) {//node为叶子节点 并且是根节点
            root = null;

            //删除根节点后的处理
            afterRemove(node);
        } else {//是叶子，并不是根节点
            if (node == node.parent.right) { //叶子在右边
                node.parent.right = null;
            } else {
                node.parent.left = null;
            }

            //删除叶子节点后的处理
            afterRemove(node);
        }
    }

    //根据某个元素找节点
   protected Node<E> node(E element) {
        Node<E> node = root;
        while (node != null) {
            int cmp = compare(element, node.element);
            if (cmp == 0) return node;
            if (cmp > 0) {
                node = node.right;
            } else {//cmp < 0
                node = node.left;
            }
        }
        return null;
    }


    //检查元素不能为空
    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }


    /**  元素的比较逻辑
     * @return 返回值等于0相等，大于零e1大于e2 ，小于零 e1<22
     */
    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        } else {
            return ((Comparable<E>) e1).compareTo(e2);
        }
    }


    /**   平衡操作由字类实现
     *   添加node 之后的调整
     * @param node 添加的节点
     *
     */
    protected  void afterAdd(Node<E> node){

    }

    /** 平衡操作由字类实现
     *  删除节点后的调整
     * @param node 删除的节点
     */
     protected void afterRemove(Node<E> node){

     }

}
