package com.megumi.util.list;


import org.omg.CORBA.Object;

import java.util.Arrays;

/**
 *  自我实现的ArrayList
 *
 *
 * @author megumi
 * @version 1.1
 * @see java.util.ArrayList
 *
 */


public class ArrayList<E>  extends  AbstractList<E> implements List<E> {



    private E[] elements;

    //默认的初始化容量
    private static final int DEFAULT_CAPACITY = 10;
    //没有找到元素的返回值


    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int capacity) {
        //如果给定的容量小于默认的 则为默认的初始化容量
        capacity = (capacity >= DEFAULT_CAPACITY) ? capacity : DEFAULT_CAPACITY;
        elements = (E[]) new Object[capacity];
    }




    @Override
    public E get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        E old = elements[index];
        elements[index] = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        ensureCapacity(size +1);//确保容量
        rangeCheckForAdd(index);

        if(index == size) {
            elements[size] = element;
        }else{
            for(int i = size; i> index ; i--){
                 elements[i] = elements[i-1];
            }
            elements[index] =element;
        }
        size++;

    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        E old = elements[index];
        for(int i = index ;i< size -1 ;i++){
            elements[i] =elements[i+1];
        }
        elements[--size] =null;

        trim();
        return old;
    }

    @Override
    public int indexOf(E element) {
        if(element == null){
            for(int i = 0; i< size ;i++) {
                if (elements[i] == null) return i;
            }
        }else{
            for(int i = 0; i< size ;i++) {
                if (elements[i].equals(element)) return i;
            }
        }


        return ELEMENT_NOT_FOUNT;
    }


    @Override
    public void clear() {
        size =0;
        Arrays.fill(elements, null);
        //动态缩容
        if(elements !=null && elements.length >DEFAULT_CAPACITY)
        elements = (E[])new Object[DEFAULT_CAPACITY];
    }


    //确保容量够 会进行动态扩容
    private void ensureCapacity(int capacity){
        int oldCapacity = elements.length;
        if(oldCapacity >=capacity) return;
        //新容量为旧容量1.5 倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        //创建新数组进行拷贝
        Object[] newElements = new Object[newCapacity];
        System.arraycopy(elements,0,newElements,0,size);

        elements =(E[])newElements;
    }

    //动态缩容
    private void trim(){
        int OldCapacity  = elements.length;
        int newCapacity = (OldCapacity >> 1 );
         //大于一半或者小于默认的容量不用缩容
        if(size >=newCapacity || OldCapacity <= DEFAULT_CAPACITY) return;

        Object[] newElements = new Object[newCapacity];
        System.arraycopy(elements,0,newElements,0,size);
        elements =(E[])newElements;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("size=").append(size).append(", [");
        for(int i = 0;i<size;i++){
            if(i != 0){
                sb.append(",");
            }
            if(elements[i] == null){
                sb.append("null");
            }else{
                sb.append(elements[i].toString());
            }
        }
           sb.append("]");
        return  sb.toString();
    }
}
