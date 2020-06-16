package com.megumi.util.tree;


import java.util.Comparator;

/**
 *
 *
 *  自平衡的二叉搜索树
 *
 *  @author megumi
 *  * @version 1.0
 *
 *  提取公共的代码做了抽象
 *  自平衡二叉搜索树的 旋转操作是相同的
 *
 * @see com.megumi.util.tree.RedBlackTree
 * @see com.megumi.util.tree.AVLTree
 *
 * AVL Tree 和 RedBlackTree  同样都是 自平衡二叉树
 *   AVL tree 是通过平衡因子来判断  左右子树高度 差小于1
 *  相比AVL ，红黑树的平衡标准比较宽松： 没有一条路径会大于其他路径的2倍
 *  红黑树是一种弱平衡  ，但性能优于 AVL树
 *
 *
 *   红黑树在
 *      添加时： O(log(n) ) O(1)次的旋转操作
 *
 *      删除时： O(log(n) ) O(1)次的旋转操作
 *
 *
 * @param <E> 容器的元素
 */
public abstract class BalancedBinarySearchTree<E> extends  BinarySearchTree<E> {


    public BalancedBinarySearchTree() {
        this(null);
    }

    public BalancedBinarySearchTree(Comparator<E> comparator) {
        super(comparator);
    }



    //左旋转
   protected void rotateLeft(Node<E> grand) { //Assert grand has parent Node and child Node
        //交换指针
        Node<E> parent = grand.right; //右子节点parent
        Node<E> child = parent.left;  //child 节点
        grand.right = child;
        parent.left  = grand;
        afterRotate(grand,parent,child);//旋转后更新


    }

    //右旋转
    protected void rotatedRight(Node<E> grand) {

        //交换指针
        Node<E> parent = grand.left;
        Node<E> child = parent.right;
        grand.left = child;
        parent.right =grand;
        afterRotate(grand,parent,child);//旋转后更新

    }

    //旋转之后的更新
    protected void afterRotate(Node<E> grand ,Node<E> parent ,Node<E> child){
        //更新父节点
        //更新p(parent) 为子树的根节点
        parent.parent  =grand.parent;
        if(grand.isLeftChild()){
            grand.parent.left = parent;
        }else  if(grand.isRightChild()){
            grand.parent.right =parent;
        }else { //grand 是root 节点
            root =parent;
        }

        //更新child 的parent
        if(child != null){
            child.parent =grand;
        }

        //更新grand 的父节点
        grand.parent =parent;


    }


}
