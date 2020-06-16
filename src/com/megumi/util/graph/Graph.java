package com.megumi.util.graph;


import java.util.List;

public interface Graph<V,E> {
    /**
     *
     * @return   返回边的数量
     */
    int edgeSize();

    /**
     *
     * @ return 返回顶点的数量
     */
    int verticesSize();

    /**
     *  增加一个顶点
     * @param v  顶点对象参数
     */
    void addVertex(V v);

    /**
     * 插入一条边
     * @param from 插入顶点的起点
     * @param to  插入顶点的终点
     * @param weight 边的比重
     */
    void addEdge(V from , V to, E weight);

    /**
     *  插入一条边 默认没有权重
     * @param from 起始顶点
     * @param to  终点
     */
    void addEdge(V from, V to);

    /**
     *  删除顶点
     * @param v 需要删除的顶点
     */
    void removeVertex(V v);

    /**
     *  删除两个顶点之间的边
     * @param from
     * @param to
     */
    void removeEdge(V from ,V to);

    void bfs(V begin);


    void dfs(V begin);

    List<V> topologicalSort();

}
