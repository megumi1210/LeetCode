package com.megumi.util.map;



import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * TreeMap
 *  key 值 必须具备可比较性
 *   存储按一定顺序
 * @param <K>
 * @param <V>
 * @author megumi
 * @version 1.0
 * @see java.util.TreeMap
 */
public class TreeMap<K, V> implements Map<K, V> {

    private static final boolean RED = false;

    private static final boolean BLACK = true;


    private int size;

    private Comparator<K> comparator;

    //树的根节点
    private Entry<K, V> root;

    public TreeMap() {
        this(null);
    }

    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }


    /**
     *  层序遍历判断是否有值
     * @param value value
     * @return  是否包含某个值
     */
    @Override
    public boolean containsValue(V value) {
        if(root == null) return false;
        Queue<Entry<K,V>> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            Entry<K,V> node = queue.poll();
            if(Objects.equals(value,node.value)){
                return  true;
            }

            if(node.left!=null){
                queue.offer(node.left);
            }

            if(node.right !=null){
                queue.offer(node.right);
            }
        }

        return false;
    }

    @Override
    public V put(K key, V value) {

        keyNotNullCheck(key);

        //添加第一个节点
        if (root == null) {
            root = new Entry<>(key, value, null);
            size++;
            afterPut(root);
            return null;
        }

        //不是第一个节点
        //找到父节点
        Entry<K, V> node = root;

        Entry<K, V> parent = null;
        int cmp = 0;

        while (node != null) {
            cmp = compare(key, node.key);
            parent = node;
            if (cmp > 0) {//大于根节点的值
                node = node.right;

            } else if (cmp < 0) {//小于根节点的值
                node = node.left;
            } else {//相等
                node.key = key;
                V old = node.value;
                node.value = value;
                return old;
            }
        }
        //node 为 null
        //看插入到父节点的哪里
        Entry<K, V> newNode = new Entry<>(key, value, parent);
        if (cmp > 0) { //大于根节点
            parent.right = newNode;
        } else if (cmp < 0) {//小于根节点
            parent.left = newNode;
        } else {//相等的时候
            //node.element = element;
        }
        size++;
        //新添加之后的实现
        afterPut(newNode);
        return null;
    }

    @Override
    public V get(K key) {
        Entry<K, V> node = node(key);
        return node == null ? null : node.value;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
            traversal(root,visitor);
    }





    /**
     * 红黑树节点
     *
     * @param <K>
     * @param <V>
     */
    public static class Entry<K, V> {
        K key;
        V value;

        boolean color = RED;

        Entry<K, V> left;
        Entry<K, V> right;
        Entry<K, V> parent;


        public Entry(K key, V value, Entry<K, V> parent) {
            this.key = key;
            this.value = value;
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
        public boolean isLeftChild() {
            return parent != null && parent.left == this;
        }

        //是右子节点
        public boolean isRightChild() {
            return parent != null && parent.right == this;
        }

        //返回兄弟节点
        public Entry<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }

            if (isRightChild()) {
                return parent.left;
            }

            return null;
        }
    }

    //检查key不能为空
    private void keyNotNullCheck(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
    }



   private void traversal(Entry<K,V> root,Visitor<K,V> visitor){
        if(root == null || visitor ==null) return;
        traversal(root.left,visitor);
        if(visitor.visit(root.key,root.value)) return;
        traversal(root.right,visitor);
   }


    /**
     * 元素的key比较逻辑
     *
     * @return 返回值等于0相等，大于零e1大于e2 ，小于零 e1<22
     */
    private int compare(K e1, K e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        } else {
            return ((Comparable<K>) e1).compareTo(e2);
        }
    }


    //染成红色
    private Entry<K, V> red(Entry<K, V> node) {
        return color(node, RED);
    }

    //染成黑色
    private Entry<K, V> black(Entry<K, V> node) {
        return color(node, BLACK);
    }

    //判断是否是黑色
    private boolean isBlack(Entry<K, V> node) {
        return colorOf(node) == BLACK;
    }

    //判断是否是红色
    private boolean isRed(Entry<K, V> node) {
        return colorOf(node) == RED;
    }


    //染色完节点返回此节点
    private Entry<K, V> color(Entry<K, V> node, boolean color) {
        if (node == null) return node;
        node.color = color;
        return node;
    }


    //得到节点的颜色
    private boolean colorOf(Entry<K, V> node) {
        return node == null ? BLACK : node.color;
    }


    //左旋转
    private void rotateLeft(Entry<K, V> grand) {
        //交换指针
        Entry<K, V> parent = grand.right; //右子节点parent
        Entry<K, V> child = parent.left;  //child 节点
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);//旋转后更新


    }

    //右旋转
    private void rotatedRight(Entry<K, V> grand) {

        //交换指针
        Entry<K, V> parent = grand.left;
        Entry<K, V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);//旋转后更新

    }

    //旋转之后的更新
    private void afterRotate(Entry<K, V> grand, Entry<K, V> parent, Entry<K, V> child) {
        //更新父节点
        //更新p(parent) 为子树的根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else { //grand 是root 节点
            root = parent;
        }

        //更新child 的parent
        if (child != null) {
            child.parent = grand;
        }

        //更新grand 的父节点
        grand.parent = parent;

    }


    //根据某个元素找节点
    private Entry<K, V> node(K key) {
        Entry<K, V> node = root;
        while (node != null) {
            int cmp = compare(key, node.key);
            if (cmp == 0) return node;
            if (cmp > 0) {
                node = node.right;
            } else {//cmp < 0
                node = node.left;
            }
        }
        return null;
    }


    private V remove(Entry<K, V> node) {
        if (node == null) return null;
        size--;
        V old =node.value;
        if (node.hasTwoChildren()) {//度为2的节点 删除时可以找前驱或者后继节点
            //找到后继节点
            Entry<K, V> s = successor(node);
            //用后继节点的值覆盖度为2节点的值
            node.key = s.key;
            node.value = s.value;
            //删除后继节点
            node = s; // 变更要被删除的节点为 后继节点
        }
        // node 的度为1
        //删除node
        //删除度为一的节点用字节点取代父节点的位置
        Entry<K, V> replacement = node.left != null ? node.left : node.right;

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
            return node.value;
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
        return  old;
    }


    //返回后继节点 原理同前驱节点
    private Entry<K, V> successor(Entry<K, V> node) {
        if (node == null) return null;
        //后继节点在右子树中
        if (node.right != null) {
            Entry<K, V> p = node.right;
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


    private void afterRemove(Entry<K, V> node) {
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

        Entry<K, V> parent = node.parent;
        //1.删除的是根节点
        if (parent == null) return;


        boolean isLeft = parent.left == null || node.isLeftChild();
        Entry<K, V> sibling = isLeft ? parent.right : parent.left;//找到兄弟节点

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

    private void afterPut(Entry<K, V> node) {

        Entry<K, V> parent = node.parent;
        if (parent == null) {//添加的是根节点
            black(node);
            return;
        }
        //如果父节点是黑色 ，直接返回
        if (isBlack(parent)) return;

        //uncle 节点是否是红色
        Entry<K, V> uncle = parent.sibling();
        Entry<K, V> grand = red(parent.parent);

        if (isRed(uncle)) { //uncle 节点为红色 上溢情况
            black(parent);
            black(uncle);
            //把祖父节点染红 向上合并
            afterPut(grand);
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
}
