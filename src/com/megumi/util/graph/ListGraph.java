package com.megumi.util.graph;


import com.megumi.util.graph.Graph;

import java.util.*;


public class ListGraph<V,E> implements Graph<V,E> {

    private Map<V,Vertex<V,E>> vertices = new HashMap<>();//存储所有顶点的集合
    private Set<Edge<V,E>> edges = new HashSet<>(); //存储所有边的集合


    /* 测试结果用*/
    public void print(){
        vertices.forEach((V v, Vertex<V,E> vertex) ->{
            System.out.println();
            System.out.println("********"+v+"*******");
            System.out.println(v);
            System.out.println("out------------");
            System.out.println(vertex.outEdges);
            System.out.println("in-----------");
            System.out.println(vertex.inEdges);
            System.out.println("********"+v+"*******");
        });

        edges.forEach((Edge<V,E> edge)->{
            System.out.println(edge);

        });

    }

    @Override
    public int edgeSize() {
        return edges.size();
    }

    @Override
    public int verticesSize() {
        return  vertices.size();
    }

    @Override
    public void addVertex(V v) {
        if(vertices.containsKey(v)) return; //如果存在顶点则返回
        vertices.put(v ,new Vertex<>(v));
    }

    @Override
    public void addEdge(V from, V to, E weight) {
         Vertex<V,E> fromVertex = vertices.get(from);
         if( fromVertex ==null){
             fromVertex = new Vertex<>(from);
             vertices.put(from,fromVertex);
         }

         Vertex<V,E> toVertex = vertices.get(to);
         if( toVertex ==null){
             toVertex = new Vertex<>(to);
             vertices.put(to,toVertex);
         }
         Edge<V,E> edge = new Edge<>(fromVertex,toVertex);
         edge.weight = weight;

         if(fromVertex.outEdges.remove(edge)){//删除原有的代替遍历
             toVertex.inEdges.remove(edge);
             edges.remove(edge);
         }


         fromVertex.outEdges.add(edge);
         toVertex.inEdges.add(edge);
         edges.add(edge);

    }

    @Override
    public void addEdge(V from, V to) {
         addEdge(from ,to ,null);
    }

    @Override
    public void removeVertex(V v) {
      Vertex<V,E> vertex = vertices.remove(v);
      if(vertex ==null) return;
        //边遍历边删除很危险，需要使用迭代器去删除
        for(Iterator<Edge<V,E>> iterator =vertex.outEdges.iterator();iterator.hasNext();){
             Edge<V,E> edge =iterator.next();
             edges.remove(edge);
             edge.to.inEdges.remove(edge);
             iterator.remove();//删除当前遍历到的元素
        }

        for(Iterator<Edge<V,E>> iterator =vertex.inEdges.iterator();iterator.hasNext();){
            Edge<V,E> edge =iterator.next();
            edges.remove(edge);
            edge.from.outEdges.remove(edge);
            iterator.remove();
        }


    }

    @Override
    public void removeEdge(V from, V to) {
         Vertex<V,E> fromVertex=vertices.get(from);
         if(fromVertex == null) return;
         Vertex<V,E> toVertex = vertices.get(to);
         if(toVertex ==null) return;

         Edge<V,E> edge = new Edge(fromVertex,toVertex);
         if(fromVertex.outEdges.remove(edge)){
             toVertex.inEdges.remove(edge);
             edges.remove(edge);
         }



    }

    @Override
    public void bfs(V begin) {
        Vertex<V,E> beginVertex = vertices.get(begin);
        if(beginVertex == null) return;

        Set<Vertex<V,E>> visited = new HashSet<>();
        Queue<Vertex<V,E>> queue= new LinkedList<>();
        queue.offer(beginVertex);
        visited.add(beginVertex);
        while(!queue.isEmpty()){
            Vertex<V,E> vertex = queue.poll();
            System.out.println(vertex.value);

            for(Edge<V,E> edge: vertex.outEdges){
                if(visited.contains(edge.to)) continue;
                 queue.offer(edge.to);
                 visited.add(edge.to);
            }
        }
    }

    @Override
    public void dfs(V begin) {//迭代方式实现
        Vertex<V,E> beginVertex = vertices.get(begin);
        if(beginVertex==null) return;
        Stack<Vertex<V,E>> stack = new Stack<>();
        Set<Vertex<V,E>> visited = new HashSet<>();
        stack.push(beginVertex);
        System.out.println(beginVertex.value);
        visited.add(beginVertex);

        while(!stack.isEmpty()){
            Vertex<V,E>  vertex = stack.pop();
            for(Edge<V,E> edge : vertex.outEdges){
                if(visited.contains(edge.to)) continue;
                visited.add(edge.to);
                stack.push(edge.from);
                stack.push(edge.to);
                System.out.println(edge.to.value);
                break;
            }
        }
    }

    @Override
    public List<V> topologicalSort() {
        List<V> list = new ArrayList<>();
        Queue<Vertex<V,E>> queue = new LinkedList<>();
        Map<Vertex<V,E>,Integer> map = new HashMap<>();

        //初始化将度为零的节点放入队列
        vertices.forEach( (V v,Vertex<V,E> vertex) ->{
            if(vertex.inEdges.size() == 0){
                 queue.offer(vertex);
            }else{
                 map.put(vertex,vertex.inEdges.size());
            }
        });

        while(!queue.isEmpty()){
            Vertex<V,E> vertex = queue.poll();
            list.add(vertex.value);
            for(Edge<V,E> edge : vertex.outEdges){
                  int ins = map.get(edge.to)-1;
                  if(ins == 0){
                      queue.offer(edge.to);
                  }else{
                       map.put(edge.to,ins);
                  }
            }
        }

        return list;

    }

    /**
     * 图的内部顶点内部类
     * @param <V> 顶点对象参数  V 是对外提供的对象，Vertex 是其对映的对象 一对一关系
     * @param <E>  边的对象的参数
     */
    private  static class Vertex<V, E> {
        V value;
        Set<Edge<V,E>> inEdges = new HashSet<>(); //此顶点为终点的边的集合无序
        Set<Edge<V,E>> outEdges = new HashSet<>();//此顶点为起点的边的集合无序

        public Vertex(V value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            return value == null ? 0: value.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return Objects.equals(value,((Vertex<V,E>)obj).value);
        }

        @Override
        public String toString() {
          return value ==null ? "null": value.toString();
        }
    }

    /**
     *  图的边
     * @param <V> 顶点值
     * @param <E> 边的权重
     */
    private  static  class Edge<V,E>{
        Vertex<V,E> from;
        Vertex<V,E> to;
        E  weight;


        public Edge(Vertex<V, E> from, Vertex<V, E> to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public int hashCode() {
            return Objects.hash(from,to);
        }

        @Override
        public boolean equals(Object obj) {
             Edge<V,E> edge = (Edge<V,E>) obj;
            return Objects.equals(from,edge.from) && Objects.equals(to,edge.to);
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }


}