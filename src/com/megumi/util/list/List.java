package com.megumi.util.list;

public interface List<E> {

    int ELEMENT_NOT_FOUNT =-1;

    //返回元素的数量
    int size();


    //是否为空
    public boolean isEmpty();


    //是否包含某个元素
    boolean contains(E element);



    //添加元素到最后面
    void  add(E element);

     //拿到指定位置元素
    E get(int index);

    //设置index位置的元素
    E set(int index ,E element);


    //往index的位置添加元素
    void add(int index ,E element);


    //删除指定位置的元素
    E remove(int index);



    //查看元素的位置
    int indexOf(E element);


    //清楚所有元素
    void clear();


}
