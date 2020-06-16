package com.megumi.util.map;



/**Map 接口
 * 映射
 * @param <K> key
 * @param <V>  value
 */
public interface Map<K,V> {

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
     * @param key key
     * @return 集合是否包含这个key
     */
    boolean containsKey(K key);


    /**
     *
     * @param value value
     * @return 是否包含value
     */
    boolean containsValue(V value);

    /**  添加元素到集合
     * @param key 要添加的元素的key
     * @param  value  要添加元素的value
     *
     */
      V  put(K key ,V value);


    /**
     *    拿到指定Key值的value
     * @param key  key
     * @return  value
     */
    V get(K key);


    /**
     * 从集合中删除元素 返回删除key值的value
     * @param key 要删除的元素
     */
    V remove(K key);

    /**
     *   集合的遍历接口
     * @param visitor 自定义访问器
     */
    void traversal(Visitor<K,V> visitor);


    /*访问器*/
    abstract  class Visitor<K,V>{
        boolean stop;
        protected abstract  boolean visit(K key, V value);
    }


}
