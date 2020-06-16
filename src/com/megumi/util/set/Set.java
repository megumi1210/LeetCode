package com.megumi.util.set;


/**
 *   Set(集合)
 * 特点 不存放重复的元素 常用于去重
 *      没有索引 无序
 *
 * @author megumi
 * @version 1.0
 * @param <E> 要存储的元素
 */
public interface Set<E> {
    /**
     * @return 元素的数量
     */
    int size();

    /**
     * @return 集合是否为空
     */
    boolean isEmpty();

    /**
     * 清空集合
     */
    void clear();

    /**
     *
     * @param element 要判断的元素
     * @return 集合是否包含这个元素
     */
    boolean contains(E element);

    /**  添加元素到集合
     * @param element 要添加的元素
     */
    void  add(E element);

    /**
     * 从集合中删除元素
     * @param element 要删除的元素
     */
    void remove(E element);

    /**
     *   集合的遍历接口
     * @param visitor 自定义访问器
     */
    void traversal(Visitor<E> visitor);

     abstract  class Visitor<E>{
        boolean stop;
        abstract  boolean visit(E element);
    }
}
