package com.megumi.util.tree;


import java.util.Comparator;


/**
 * 红黑树的实现
 *
 * @param <E> 存储的元素
 * @author megumi
 * @version 1.0
 * <p>
 * 红黑树 的性质
 * 1. 根节点必须是黑色
 * 2. 红色节点的子节点必须是黑色 <=>  红色节点的父节点必须是黑色
 * 3. 每一条路径 黑色节点的数量相同
 * 4.叶子节点(外部节点，空节点nuLl)都是黑色
 * 5.根节点到叶子节点不能有两个连续的 红色节点
 *
 * 红黑树的最大高度 为 2* log2(n+1)  还是 log(n)级别
 * <p>
 * 本质就是 4阶 B树
 * 红黑树的等价变换 (红色节点向上和黑色节点合并）
 * <p>
 *     B树节点存储的节点个数为 1 ,2 ,3
 *     可知节点的全部情况只有4种  ：  黑 ||  黑红 || 红黑 ||  红黑红
 *
 * 添加时有12种情况
 *
 * 是先按照父节点是否为黑分类 若父节点为黑 不用处理
 * 其中有4种是不用处理的
 *
 *
 * <p>
 * 后8种需要处理的 按照叔父节点是否为红色 分 2个 4 类 情况
 * <p>
 * 叔父节点不是红色时 LL LR RR RL 旋转染色
 * <p>
 * 是红色时 为上溢处理 染色 后递归调用
 * <p>
 * 所有情况的判断都是根据四种情况来判断
 * <p>
 * 1. 红<-黑->红    ||  红<-黑    || 黑->红 ||  黑
 *
 * 删除时情况复杂
 *
 * 从所有情况来讨论
 *
 *    如果删除的节点是红色则不用处理
 *
 *     如果删除的是黑色的
 *
 *          1. 红 黑 红  默认删除的是后继节点 不用处理
 *          2.  红 黑 || 黑红 情况 将它的替代 直接染黑
 *          3    黑  情况比较多  删除时产生下溢
 *                   3）   兄弟为黑 是能借的前提
 *                         1. 兄弟节点能借 （红色节点至少1个），旋转解决
 *                         2. 兄弟节点借不出 ， 父节点向下合并 ，会导致父节点下溢，直接当成被删除节点递归处理
 *
 *                         兄弟为红 不能借
 *                         3. 进行旋转 ，把侄子节点转换为兄弟节点 再往上两种情况讨论
 *
 */
public class RedBlackTree<E> extends BalancedBinarySearchTree<E> {

    private static final boolean RED = false;

    private static final boolean BLACK = true;

    public RedBlackTree() {
        this(null);
    }

    //得到外部自定义的比较规则
    public RedBlackTree(Comparator<E> comparator) {
        super(comparator);
    }


    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;
        if (parent == null) {//添加的是根节点
            black(node);
            return;
        }
        //如果父节点是黑色 ，直接返回
        if (isBlack(parent)) return;

        //uncle 节点是否是红色
        Node<E> uncle = parent.sibling();
        Node<E> grand = red(parent.parent);

        if (isRed(uncle)) { //uncle 节点为红色 上溢情况
            black(parent);
            black(uncle);
            //把祖父节点染红 向上合并
            afterAdd(grand);
            return;

        }

        //uncle 节点不是红色 则进行相应的旋转操作 和染色操作
        if (parent.isLeftChild()) {

            if (node.isLeftChild()) {//LL
                black(parent);
            } else {//LR
                black(node);
                rotateLeft(parent);
            }
            rotatedRight(grand);
        } else {//parent is rightChild

            if (node.isLeftChild()) {//RL
                black(node);
                rotatedRight(parent);
            } else {//RR
                black(parent);
            }
            rotateLeft(grand);
        }

    }



    //删除节点后的处理
    // 考虑的情况有四种 ，其中一种 红黑红 的度为2的情况（站在B树的角度看）直接找前驱或者后继取代
    // 在remove 中已经处理过了 所以在这里不需要考虑
    @Override
    protected void afterRemove(Node<E> node) {// 此处传值有2种，1.replacement 2.要删除的node
        //如果删除的时红色 直接返回
       // if (isRed(node)) return; 与下一段合并了

        //删除的节点时黑色
        //判断用于取代的颜色是否为红色（replacement）
        if (isRed(node)) {  //此处传值 replacement
            //直接将替代的子节点染成黑色
            black(node);
            return;
        }

        //删除的时黑色叶子节点

        Node<E> parent = node.parent;
        //1.删除的是根节点
        if (parent == null) return;

        //2. 删除的是非根节点的黑色叶子节点
        //   出现下溢
        //   1）解决 先找兄弟节点借节点，能借的前提是兄弟节点必须是黑色
        //   可向兄弟借的情况为  1 (红 <-黑->红 ,(黑))  ||  2(黑->红,(黑))  ||  3(红<-黑,(黑))
        // 删除后要旋转  旋转后的中心节点必须继承 parent 的颜色
        //  2） 兄弟节点借不来 ，并且兄弟节点是黑色 把父节点下移合并
        //      兄弟节点染红父节点染黑可解决下溢
        //        其中 如果父节点 为红 直接下移合并 ，如果父节点为黑 ，父节点会产生下溢
        //            递归调用即可
        // 3） 兄弟节点为红色
        // 为了向 （侄子节点） 借 ，要把兄弟节点进行旋转 染色  => 兄弟节点变为黑色变成上面的情况

        //判断删除的节点的node是左还是右
        //因为被删除的节点是叶子, 父节点的引用（left,right) 被清空 无法通过 sibling()方法获取
        //还有一种清空是递归调用传进来的值
        //自己传进来的节点父节点的引用不为空
        boolean isLeft = parent.left == null || node.isLeftChild();
        Node<E> sibling = isLeft ? parent.right : parent.left;//找到兄弟节点

        if (isLeft) {//被删除的节点在左边 ，兄弟节点在右边

            if (isRed(sibling)) {//兄弟节点是红色，要转换为兄弟节点是黑色的节点
                black(sibling);
                red(parent);
                rotateLeft(parent);
                //更换兄弟
                sibling = parent.right;
            }

            //兄弟节点是黑色
            // 看它红色节点是否至少有1个
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有一个红色节点，父节点要向下跟兄弟节点合并
                boolean parentIsBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentIsBlack) {
                    afterRemove(parent);
                }

            } else {//兄弟节点至少有一个红色节点,向兄弟节点借一个节点
                if (isBlack(sibling.right)) {//左边是黑色
                    rotatedRight(sibling);
                    sibling = parent.right;
                }

                //统一对父节点右旋转
                //旋转之后中心节点继承parent 颜色
                //旋转之后左右节点染黑
                //这里先染色再旋转
                color(sibling, colorOf(parent));//先给兄弟节点继承颜色
                black(sibling.right);
                black(parent);
                rotateLeft(parent);

            }


        } else {//被删除的节点在右边 ，兄弟节点在左边

            if (isRed(sibling)) {//兄弟节点是红色，要转换为兄弟节点是黑色的节点
                black(sibling);
                red(parent);
                rotatedRight(parent);
                //更换兄弟
                sibling = parent.left;
            }

            //兄弟节点是黑色
            // 看它红色节点是否至少有1个
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有一个红色节点，父节点要向下跟兄弟节点合并
                boolean parentIsBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentIsBlack) {
                    afterRemove(parent);
                }

            } else {//兄弟节点至少有一个红色节点,向兄弟节点借一个节点
                if (isBlack(sibling.left)) {//左边是黑色
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                //统一对父节点右旋转
                //旋转之后中心节点继承parent 颜色
                //旋转之后左右节点染黑
                //这里先染色再旋转
                color(sibling, colorOf(parent));//先给兄弟节点继承颜色
                black(sibling.left);
                black(parent);
                rotatedRight(parent);

            }

        }


    }

    //染成红色
    private Node<E> red(Node<E> node) {
        return color(node, RED);
    }

    //染成黑色
    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }

    //判断是否是黑色
    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }

    //判断是否是红色
    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent);
    }

    //红黑树节点
    private static class RBNode<E> extends Node<E> {
        //默认颜色是红色 能够尽快满足红黑树的性质
        boolean color = RED;

        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }
    }


    //染色完节点返回此节点
    private Node<E> color(Node<E> node, boolean color) {
        if (node == null) return node;
        ((RBNode<E>) node).color = color;
        return node;
    }


    //得到节点的颜色
    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : ((RBNode<E>) node).color;
    }

    //-----------------------------------------------------------------------
    //------ 打印红黑树的节点的toString() 重写
    // --- 红色节点 前缀为(R)   黑色节点前缀为(B)
    @Override
    public Object string(Object node) {

        return ((RBNode<E>) node).color== RED ?"(R)" + ((RBNode<E>) node).element
                                : "(B)" + ((RBNode<E>) node).element   ;
    }
}
