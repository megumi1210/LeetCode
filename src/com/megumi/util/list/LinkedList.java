package com.megumi.util.list;


/**
 * @author megumi
 * @version 1.0
 * @see java.util.LinkedList
 * @see com.megumi.util.list.List
 */

public class LinkedList<E> extends AbstractList<E> implements List<E> {


    private Node<E> first;

    private Node<E> last;

    /*
       拿到 给定index的 节点元素
     */
    @Override
    public E get(int index) {
        return getNode(index).element;
    }

    @Override
    public E set(int index, E element) {

        Node<E> oldNode = getNode(index);
        E old = oldNode.element;
        oldNode.element = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);

        if (index == size) {//插入节点为尾节点

            Node<E> oldLast = last;
            last = new Node<>(oldLast, element, null);
            if (oldLast == null) {//链表添加的第一个元素
                first = last;
            } else {
                oldLast.next = last;
            }


        } else {

            Node<E> cur = getNode(index);
            Node<E> pre = cur.pre;
            Node<E> node = new Node<>(pre, element, cur);
            cur.pre = node;
            if (pre == null) {//index == 0 头节点
                first = node;
            } else {
                pre.next = node;
            }
        }
        size++;

    }

    @Override
    public E remove(int index) {
        rangeCheck(index);


        Node<E> cur = getNode(index);
        Node<E> pre = cur.pre;
        Node<E> next = cur.next;

        if (pre == null) {//为第一个节点
            first = next;

        } else {
            pre.next = next;

        }

        if (next == null) {//是最后一个节点
            last = pre;
        } else {
            next.pre = pre;
        }

        size--;
        return cur.element;

    }

    @Override
    public int indexOf(E element) {

        for (int i = 0; i < size; i++) {
            if (get(i).equals(element)) {
                return i;
            }
        }
        return ELEMENT_NOT_FOUNT;
    }

    @Override
    public void clear() {
        size = 0;
        //GC 采用的是可达性算法 其他节点即使相互引用也会被清除
        first = null;
        last = null;
    }


    /*
     *  线性表节点
     */
    private static class Node<E> {

        E element; //存储的元素
        Node<E> next; //指向下一个节点
        Node<E> pre; //指向上一个节点

        public Node(Node<E> pre, E element, Node<E> next) {
            this.pre = pre;
            this.element = element;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("");
            if(pre !=null){
                sb.append(pre.element);
            }else {
                sb.append("null");
            }
            sb.append("_").append(element).append("_");

            if(next !=null){
                sb.append(next.element);
            }else {
                sb.append("null");
            }

            return  sb.toString();

        }
    }


    /*
          拿到指定节点
     */
    private Node<E> getNode(int index) {
        rangeCheck(index);
        Node<E> root;
        if (index < (size >> 1)) { //小于一半，从前往后找
            root = first;
            for (int i = 0; i < index; i++) {
                root = root.next;
            }
        } else {
            root = last;
            for (int i = size - 1; i > index; i--) {
                root = root.pre;
            }
        }
        return root;
    }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("size=").append(size).append(", [");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(", ");
            }

            sb.append(getNode(i).toString());

        }
        sb.append("]");
        return sb.toString();
    }

}


