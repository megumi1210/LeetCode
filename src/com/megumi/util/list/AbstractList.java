package com.megumi.util.list;




/**
 * List的公共代码抽取复用
 *
 * @author megumi
 * @version 1.0
 * @see com.megumi.util.list.ArrayList
 * @see com.megumi.util.list.LinkedList
 */

public abstract class AbstractList<E>  implements  List<E>{

    protected int size;



    /**
     *
     * @return  List 的元素数量
     */
    public int size() {
        return size;
    }

    /**
     * 实现 List
     * @return 是否为空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     *   是否包含某个元素
     * @param element 元素
     * @return   是否包含
     */
    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUNT;
    }

    /**
     * 往最后添加元素
     * @param element 增加所有的元素
     */
    public void add(E element) {
        add(size,element);
    }

    /**
     *  检查的索引检查
     * @param index 元素索引
     */
    protected void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index:" + index + ",Size:" + size);
        }

    }

    /**
     *  检查添加时候的索引检查
     * @param index 元素索引
     */
    protected void rangeCheckForAdd(int index) {

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index:" + index + ",Size:" + size);
        }
    }


}
