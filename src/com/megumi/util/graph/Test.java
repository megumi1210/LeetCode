package com.megumi.util.graph;


import com.megumi.util.graph.Data;
import com.megumi.util.graph.Graph;
import com.megumi.util.graph.ListGraph;

public class Test {


    public static void main(String[] args) {
//
//        ListGraph<String,Integer> graph = new ListGraph<>();
//        graph.addEdge("v1","v0",9);
//        graph.addEdge("v1","v2",3);
//        graph.addEdge("v2","v0",2);
//        graph.addEdge("v2","v3",5);
//        graph.addEdge("v3","v4",1);
//        graph.addEdge("v0","v4",6);

        //graph.removeEdge("v0","v4");
//        graph.removeVertex("v0");

//        graph.print();
        //graph.bfs("v1");

      testBfs();
    }

    static  void testBfs(){
        Graph<Object,Double> graph =undirectedGraph(Data.BFS_01);
        graph.bfs("A");
    }

    /**
     *   解析数组拿到有向图
     * @param data
     * @return
     */
    public  static Graph<Object,Double> directedGraph(Object[][] data){
         Graph<Object,Double> graph = new ListGraph<>();
         for(Object[] edge:data){
              if(edge.length ==1){
                  graph.addVertex(edge[0]);
              }else if(edge.length ==2){
                  graph.addEdge(edge[0],edge[1]);
              }else if(edge.length ==3){
                   graph.addEdge(edge[0],edge[1],Double.parseDouble(edge[2].toString()));
              }
         }

         return graph;
    }

    /**
     * 解析数组拿到无向图
     * @param data
     * @return
     */
    public  static Graph<Object,Double>  undirectedGraph(Object[][] data){
        Graph<Object,Double> graph = new ListGraph<>();
        for(Object[] edge:data){
            if(edge.length ==1){
                graph.addVertex(edge[0]);
            }else if(edge.length ==2){
                graph.addEdge(edge[0],edge[1]);
                graph.addEdge(edge[1],edge[0]);
            }else if(edge.length ==3){
              double weight =Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0],edge[1],weight);
                graph.addEdge(edge[1],edge[0],weight);
            }
        }

        return graph;
    }
}
