package com.megumi.util.tree;

import java.util.Comparator;

/**
 * AVLTree 是自平衡的二叉搜索树
 * 每个节点的平衡因子的绝对值 小于1
 * 通过平衡因子来保证平衡性
 * 针对四种方式 LL RR  LR RL
 * 进行 单旋转 或者 双旋转
 *
 * @param <E> 添加的元素
 * @author megumi
 * @version 1.0
 */

/**
//   g grand 祖父节点
//   p  parent 父节点
//   n  node  子节点
//  1. LL   g 失衡   g 右旋1次
//              ┌──g──┐
//              │     │
//            ┌─p─┐   n
//            │   │
//          ┌─n─┐ n
//          │   │
//          n   n
//
//
//    2. RR   g 失衡  g 左旋1次
//              ┌──g──┐
//              │     │
//             n    ┌─p─┐
//                  │   │
//                 n  ┌─n─┐
//                    │   │
//                    n   n

//    3. LR   p 失衡   p左旋1次  g右旋1次
//              ┌──g──┐
//              │     │
//            ┌─p─┐   n
//            │ ┌─n─┐
//            n │   │
//              n    n
//                   │
//                   n

//    2. RL   p 失衡   p 右旋1次 g 左旋1次
//              ┌──g──┐
//              │     │
//             n    ┌─p─┐
//                  │   │
//                ┌─n─┐ n
//                │   │
//                n   n
//                │
//                n
*/

public class AVLTree<E> extends  BalancedBinarySearchTree<E> {

    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }


    /**
     * 添加之后的平衡操作
     *
     * @param node 添加的节点
     */
    @Override
    protected void afterAdd(Node<E> node) {
        //失衡一定发生在祖先节点
        //解决方案时 顺着node 的parent 找， 直到找到最先失衡的节点 进行平衡
        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                //更新高度 这里很妙 利用外循环更新高度，不用递归
                //添加的节点都是叶子节点，那么它的第一个父类必定平衡，然后更新高度
                updateHeight(node);
            } else {
                //恢复平衡
                //node一定是 最先失衡的父节点
                reBalance(node);
                break;
            }
        }

    }

    @Override
    protected void afterRemove(Node<E> node) { //replacement 和 node 作为传递在AVL树中没有影响

        //失衡一定发生在祖先节点
        //解决方案时 顺着node 的parent 找， 直到找到最先失衡的节点 进行平衡
        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                //更新高度 这里很妙 利用外循环更新高度，不用递归
                //添加的节点都是叶子节点，那么它的第一个父类必定平衡，然后更新高度
                updateHeight(node);
            } else {
                //恢复平衡
                //node一定是 最先失衡的父节点
                reBalance(node);

            }
        }

    }


    /**
     * 平衡节点恢复平衡
     *
     * @param grand 高度最低的不平衡点
     */
    private void reBalance(Node<E> grand) {
        //拿到后两代中高度最高的两个节点 用来判断 LL LR RR RL
        Node<E> parent = ((AVLNode<E>) grand).tallerChild();
        Node<E> child = ((AVLNode<E>) parent).tallerChild();

        if (parent.isLeftChild()) {//L

            if (child.isLeftChild()) {//LL
                rotatedRight(grand);
            } else { //LR
                rotateLeft(parent);
                rotatedRight(grand);
            }

        } else { //R

            if (child.isLeftChild()) {//RL
                rotatedRight(parent);
                rotateLeft(grand);

            } else { //RR
                rotateLeft(grand);
            }
        }

    }

    //执行完旋转后更新高度
    @Override
    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        super.afterRotate(grand, parent, child);

        //更新高度
        updateHeight(grand);
        updateHeight(parent);
    }

    /* 创建AVL 节点 又是一种行为参数化
     */
    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<>(element, parent);
    }


    //判断是否平衡
    private boolean isBalanced(Node<E> node) {
        return Math.abs(((AVLNode<E>) node).balanceFactor()) <= 1;
    }

    //更新高度的封装
    private void updateHeight(Node<E> node) {
        ((AVLNode<E>) node).updateHeight();

    }


    //自定义AVL节点
    private static class AVLNode<E> extends Node<E> {
        int height = 1;

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        //平衡因子
        public int balanceFactor() {
            int lefHeight = getLeftHeight();
            int rightHeight = getRightHeight();
            return lefHeight - rightHeight;
        }

        //更新自己的高度
        public void updateHeight() {
            int lefHeight = getLeftHeight();
            int rightHeight = getRightHeight();
            height = 1 + Math.max(lefHeight, rightHeight);
        }


        //拿到左右子节点最高的子节点 若相同则返回同方向的
        public Node<E> tallerChild() {
            int lefHeight = getLeftHeight();
            int rightHeight = getRightHeight();
            if (lefHeight > rightHeight) return left;
            if (rightHeight > lefHeight) return right;
            return isLeftChild() ? left : right;
        }

        //拿到左子树的高度
        private int getLeftHeight() {
            return left == null ? 0 : ((AVLNode<E>) left).height;
        }

        //拿到右子树的高度
        private int getRightHeight() {
            return right == null ? 0 : ((AVLNode<E>) right).height;
        }

    }

}
