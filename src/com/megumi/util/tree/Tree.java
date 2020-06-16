package com.megumi.util.tree;


/**  树的接口
 * @author megumi
 * @version  1.0
 */
public interface Tree<E> {

    int size(); //元素的数量

    boolean isEmpty(); //是否为空

    void clear();  //清空所有的元素

    void add(E element); //增加元素

    void remove(E element); //删除元素

    boolean contains(E element); //是否包含某元素


}
