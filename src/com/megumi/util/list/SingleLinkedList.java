package com.megumi.util.list;


/**单向链表
 * @author megumi
 * @version 1.0
 * @see java.util.LinkedList
 * @see List
 */

public class SingleLinkedList<E> extends AbstractList<E> implements List<E> {


    private Node<E> first;



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

        if (index == 0) {//插入为头节点
            first = new Node<>(element, first);
        } else {
            Node<E> pre = getNode(index - 1);
            pre.next = new Node<>(element, pre.next);
        }
        size++;

    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> node = first;
       if (index == 0) {//插入为头节点
            first = first.next;

        } else {
            Node<E> pre = getNode(index - 1);
            node =pre.next;
            pre.next = node.next;
        }
        size--;
       return  node.element;

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
    }


    /*
     *  线性表节点
     */
    private static class Node<E> {

        E element; //存储的元素
        Node<E> next; //只想下一个节点

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }


    /*
          拿到指定节点
     */
    private Node<E> getNode(int index) {
        rangeCheck(index);
        Node<E> root = first;
        for (int i = 0; i < index; i++) {
            root = root.next;
        }
        return root;
    }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("size=").append(size).append(", [");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(",");
            }
            if (get(i) == null) {
                sb.append("null");
            } else {
                sb.append(get(i).toString());
            }
        }
        sb.append("]");
        return sb.toString();
    }

}


