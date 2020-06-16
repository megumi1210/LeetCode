package com.megumi.util.map;

import com.mj.printer.BinaryTreeInfo;
import com.mj.printer.BinaryTrees;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class HashMap<K, V> implements Map<K, V> {

    private int size;

    private static final boolean RED = false;

    private static final boolean BLACK = true;

    private static final int DEFAULT_CAPACITY = (1 << 4);

    //红黑树根节点的数组
    //哈希表的底层实现
    private Entry<K, V>[] table;


    public HashMap() {
        table = new Entry[DEFAULT_CAPACITY];
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
        if (size == 0) return;
        size = 0;
        Arrays.fill(table, null);
    }

    @Override
    public boolean containsKey(K key) {
       return  node(key) !=null;
    }

    @Override
    public boolean containsValue(V value) {
        if(size == 0) return false;
        Queue<Entry<K,V>> queue = new LinkedList<>();
        for (Entry<K, V> kvEntry : table) {
            if (kvEntry == null) continue;
            queue.offer(kvEntry);
            while (!queue.isEmpty()) {
                Entry<K, V> node = queue.poll();
                if (Objects.equals(value, node.value)) return true;

                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }

        return  false;
    }

    @Override
    public V put(K key, V value) {
        int index = index(key);
        //取出index位置的根节点
        Entry<K, V> root = table[index];
        if (root == null) {
            root = new Entry<>(key, value, null);
            table[index] = root;
            size++;
            afterPut(root);
            return null;
        }

        //hash 冲突了
        //添加新的节点到红黑树上
        //添加第一个节点


        //找到父节点
        Entry<K, V> node = root;
        Entry<K, V> parent = null;
        int cmp = 0;
        int h1 = key == null ? 0 : key.hashCode();
        do {
            cmp = compare(key, node.key, h1, node.hash);
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
        } while (node != null);

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
      Entry<K,V> node  = node(key);
      return  node !=null ? node.value : null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if(size == 0 || visitor ==null) return ;
        Queue<Entry<K,V>> queue = new LinkedList<>();
        for (Entry<K, V> kvEntry : table) {
            if (kvEntry == null) continue;
            queue.offer(kvEntry);
            while (!queue.isEmpty()) {
                Entry<K, V> node = queue.poll();

                if(visitor.visit(node.key,node.value)) return;

                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }


    }


    /**
     * 红黑树节点
     *
     * @param <K>
     * @param <V>
     */
    private static class Entry<K, V> {
        K key;
        V value;
        int hash;

        boolean color = RED;

        Entry<K, V> left;
        Entry<K, V> right;
        Entry<K, V> parent;


        public Entry(K key, V value, Entry<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.hash = key == null ? 0 : key.hashCode();
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

        @Override
        public String toString() {
            return key.toString() +"_" + value.toString();
        }
    }

    /**
     * @param key key
     * @return 根据 key 生成对应的hash值 也支持nuLl
     */
    private int index(K key) {
        if (key == null) return 0;
        int hash = key.hashCode();
        //table length 最好是2的n次
        return (hash ^ (hash >>> 16)) & (table.length - 1);
    }


    private int index(Entry<K,V> node){
        return  (node.hash ^ ( node.hash >>> 16)) & (table.length -1);
    }


    /**
     * HashMap 不强制比较key
     * 元素的key比较逻辑 比较特殊
     *
     * @return 返回值等于0相等，大于0 ，k1大于e2 ，小于零0 k1<k2
     */
    private int compare(K k1, K k2, int h1, int h2) {
        //用hash值比较
        int result = h1 - h2;

        if (result != 0) return result;

        //hash相同
        //比较equals
        if (Objects.equals(k1, k2)) return 0;

        //hash 值相等 , 但是不是同类型key
        //比较类名
        if (k1 != null && k2 != null) {
            String k1Cls = k1.getClass().getName();
            String k2Cls = k2.getClass().getName();
            result = k1Cls.compareTo(k2Cls);
            if (result != 0) return result;

            //同一种类型
            if (k1 instanceof Comparable) {
                return ((Comparable) k1).compareTo(k2);
            }
        }

        //同一种类型但是不具备可比较性
        //k1 不为null ,k2 为null
        //k1  为null k2 不为null

      //用内存地址比较
        return System.identityHashCode(k1) -System.identityHashCode(k2);
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
           table[index(grand)] = parent; //同一棵红黑树
        }

        //更新child 的parent
        if (child != null) {
            child.parent = grand;
        }

        //更新grand 的父节点
        grand.parent = parent;

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


    //根据某个元素找节点
    private Entry<K, V> node(K key) {

        Entry<K, V> node = table[index(key)];
        int h1 =key ==null? 0 :key.hashCode();
        while (node != null) {
            int cmp = compare(key, node.key,h1,node.hash);
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
        V old = node.value;
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
        int index = index(node);

        if (replacement != null) { //node度为1的节点
            //更改parent
            replacement.parent = node.parent;
            //更改parent的 left 或者right 的指针
            if (node.parent == null) { //node是度为1的节点 而且为根节点
                table[index] = replacement;
            } else if (node == node.parent.left) { //父类的left 或者 right 指向 child(replacement)
                node.parent.left = replacement;
            } else {//node == node.parent.right
                node.parent.right = replacement;
            }
            //删除节点后的处理
            afterRemove(replacement);
            return node.value;
        } else if (node.parent == null) {//node为叶子节点 并且是根节点
              table[index] = null;

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
        return old;
    }

    //用于调试红黑树
    public void print(){
      if(size == 0) return;
      for(int i  = 0 ;i< table.length;i++){
          final  Entry<K,V> node = table[i];
          System.out.println("index:【"+i+"】 ");
          BinaryTrees.println(new BinaryTreeInfo() {
              @Override
              public Object root() {
                  return node;
              }

              @Override
              public Object left(Object node) {
                  return ((Entry<K,V>)node).left;
              }

              @Override
              public Object right(Object node) {
                  return ((Entry<K,V>)node).right;
              }

              @Override
              public Object string(Object node) {
                  return ((Entry<K,V>)node).key.toString() +"_" + ((Entry<K,V>)node).value.toString();
              }
          });
          System.out.println("-----------------------------------");
      }
    }

}
