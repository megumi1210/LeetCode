package com.example;


import com.sun.javafx.image.IntPixelGetter;
import org.junit.Test;

import java.util.*;

public class Solution {

    private static List<Integer> l = new ArrayList<>();
    private static List<List<Integer>> lists = new ArrayList<>();

    public static TreeNode getTree(List<Integer> list) {
        if (list == null || list.size() == 0) return null;
        TreeNode root = new TreeNode(list.get(0));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int index = 1;

        while (!queue.isEmpty() && index < list.size()) {
            TreeNode node = queue.poll();

            if (list.get(index) != null)
                node.left = new TreeNode(list.get(index++));
            else {
                node.left = null;
                index++;
            }

            if (index < list.size()) {
                if (list.get(index) != null)
                    node.right = new TreeNode(list.get(index++));
                else {
                    node.right = null;
                    index++;
                }
            }


            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null)
                queue.add(node.right);
        }

        return root;
    }

    public static void inorderTraversal(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.val + " ");
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    public static void printTree(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {

            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                System.out.print(node.val + " ");

                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            System.out.println();


        }
    }

    public static int getDepth(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) {
            return 1;
        }
        return Math.max(getDepth(root.left), getDepth(root.right)) + 1;
    }

    static class ArrayFactory {

        public static List<Integer> apply(String str) {
            String tmp = str.replace(" ", "");
            String[] args = tmp.split(",");
            Object[] objs = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("null")) {
                    objs[i] = null;
                } else {
                    objs[i] = Integer.valueOf(args[i]);
                }
            }
            return apply(objs);
        }

        public static List<Integer> apply(Object... args) {
            ArrayList<Integer> array = new ArrayList<>();
            for (Object o : args) {
                if (o == null)
                    array.add(null);
                else
                    array.add((Integer) o);

            }

            return array;
        }

    }

    public static void main(String[] args) {
        //text01();
        //text02();
        //test03();
        //test04();
        System.out.println(changeInt("1101"));
    }

    /**
     * 给定一个二叉树和一个目标和，找到所有从根节点到叶子节点路径总和等于给定目标和的路径。
     * <p>
     * 说明: 叶子节点是指没有子节点的节点。
     */
//    List<List<Integer>> lists = new ArrayList<>();
//    List<Integer>  l = new ArrayList<>();

//    public static void pathSum(TreeNode root, int sum, int mySum) {
//        l.add(root.val);
//        mySum +=root.val;
//        if(root.left == null && root.right ==null && mySum ==sum){
//             List<Integer>  dest= new ArrayList<>();
//            for(Integer i : l){
//                dest.add(i);
//            }
//            lists.add(dest);
//            return ;
//        }
//
//        if(root.left !=null){
//            pathSum(root.left,sum,mySum);
//            l.remove(l.size()-1);
//        }
//
//        if(root.right !=null){
//            pathSum(root.right,sum,mySum);
//            l.remove(l.size()-1);
//        }
//
//    }
//
//    public static List<List<Integer>> pathSum(TreeNode root, int sum) {
//
//        if(root ==null) return lists;
//        pathSum(root,sum,0);
//        return lists;
//    }

//    public static void text01(){
//        String s = new String("5,4,8,11,null,13,4,7,2,null,null,5,1");
//        List<Integer> array = ArrayFactory.apply(s);
//        TreeNode root = getTree(array);
//        List<List<Integer>> list = pathSum(root ,22);
//        for(List<Integer> l:list){
//            System.out.println(l);
//        }
//    }
    public static void text02() {
        String s1 = new String("1,5,2,47,7,6,3,null,null,59,22,null,16,9,4,null,null,60,26,20,null,10,11,33,8, null, null, 53, 28, 35");
        List<Integer> array1 = ArrayFactory.apply(s1);
        String s2 = new String("33");
        List<Integer> array2 = ArrayFactory.apply(s2);

        TreeNode t1 = getTree(array1);
        TreeNode t2 = getTree(array2);
        Boolean f = checkSubTree(t1, t2);
        //Boolean f = comapre(t1,t2);
        printTree(t1);
        System.out.println(f);
    }


    public static boolean comapre(TreeNode t1, TreeNode t2) {
        if (t1 == null || t2 == null) {
            return t1 == t2;
        }

        return comapre(t1.left, t2.left) && comapre(t1.right, t2.right) && t1.val == t2.val;
    }

    public static boolean checkSubTree(TreeNode t1, TreeNode t2) {


        boolean flag = comapre(t1, t2);
        if (t1 == null) return false;


        return flag || checkSubTree(t1.left, t2) || checkSubTree(t1.right, t2);
    }

    public static void test03() {
        String s = new String("10,5,-3,3,2,null,11,3,-2,null,1");
        List<Integer> array = ArrayFactory.apply(s);
        TreeNode t = getTree(array);
        int c = pathSum(t, 8);
        System.out.println(c);
    }

    static int count = 0;
    // List<TreeNode> path = new ArrayList<>();//记录路径，用于回溯
    static int recordSum; //记录路径的和

    public static void find(TreeNode root, int sum) {


        if (root == null) return;

        recordSum += root.val;
        if (recordSum == sum) count++;


        if (root.left != null) {
            find(root.left, sum);
            recordSum -= root.left.val;
        }

        if (root.right != null) {
            find(root.right, sum);
            recordSum -= root.right.val;
        }


    }

    public static void findAll(TreeNode root, int sum) {


        if (root == null) return;
        recordSum =0;
        find(root,sum);

        findAll(root.left, sum);

        findAll(root.right, sum);


    }

    public static int pathSum(TreeNode root, int sum) {
        if (root == null) return 0;
        findAll(root, sum);
        return count;
    }


    /**************************/
    public static Queue<TreeNode> getLevelNodes(TreeNode root,int level){//拿到指定层的所有节点
        if(root == null) return null;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int depth =1;
        while(!queue.isEmpty() && depth <level){
            int size = queue.size();
            for(int  i = 0 ;i<size; i++ ){//把当前层全部遍历

                TreeNode node = queue.remove();

                if(node.left != null){
                    queue.add(node.left);
                }

                if(node.right != null){
                    queue.add(node.right);
                }
            }

            depth++;

        }

        return queue;

    }
    public static TreeNode addOneRow(TreeNode root, int v, int d) {
        if(d == 1){
            TreeNode  newRoot = new TreeNode(v);
            newRoot.left = root;
            return newRoot;
        }

        Queue<TreeNode> nodes = getLevelNodes(root,d-1); //拿到d-1层全部节点

        for(TreeNode node : nodes){
            System.out.print(node.val+" ");
            TreeNode newLeft = new TreeNode(v);
            TreeNode newRight = new TreeNode(v);
            newLeft.left = node.left;
            newRight.right =node.right;
            node.left =newLeft;
            node.right=newRight;
        }
        return root;
    }

    public static  void test04(){
        String s = new String("4,2,6,3,1,5");
        List<Integer> array = ArrayFactory.apply(s);
        TreeNode root = getTree(array);


    }
    public static int changeInt(String s){
        char[] c = s.toCharArray();
        int res = Character.getNumericValue(c[0]);
        for(int i  =1 ;i< c.length;i++) {
            res = res * 2 + Character.getNumericValue(c[i]);
        }
        return res;
    }

   //以广度优先遍历方法
    public  boolean canMeasureWater(int x, int y, int z) {
        if(z == 0) return true ;// 如果水量z 为 0
        if( x + y < z) return false; // z的水量大于总的水量
        //记录每次的水量
       Queue<Map.Entry<Integer,Integer>> queue = new LinkedList<>();
       AbstractMap.SimpleEntry<Integer,Integer> start = new AbstractMap.SimpleEntry<>(0,0);
       queue.add(start);
       //记录访问过的情况
       Set<Map.Entry<Integer,Integer>> visited = new HashSet<>();

       while(!queue.isEmpty()){
           Map.Entry<Integer,Integer>  entry = queue.poll();

           int curX =entry.getKey(); //拿到当前X水量
           int curY =entry.getValue(); //拿到当前Y水量

           if(curX == z || curY ==z || curX+curY ==z)
               return true;

           if( visited.contains(entry))
                continue;;
            visited.add(entry);
            //灌满x水壶
            queue.add(new AbstractMap.SimpleEntry<>(x,curY));
            //灌满y 水壶
           queue.add(new AbstractMap.SimpleEntry<>(curX,y));

           //把x壶直接倒空
           queue.add(new AbstractMap.SimpleEntry<>(0,curY));

           //把y壶直接倒空
           queue.add(new AbstractMap.SimpleEntry<>(curX,0));


           //把x壶倒入 y壶直至倒空或者倒满
           int x_y =Math.min(curX,y-curY);
          queue.add(new AbstractMap.SimpleEntry<>(curX - x_y , curY + x_y));

          //把y 壶导入x 壶直至 倒空或者倒满
           int y_x  =Math.min(curY,x-curX);
           queue.add(new AbstractMap.SimpleEntry<>(curX + y_x , curY - y_x));

       }

       return false;
    }

    @Test
    public void canMeasureWaterTest(){
          boolean flag = canMeasureWater(3,5,4);
          System.out.println(flag );
    }



    public boolean hasGroupsSizeX(int[] deck) {
        if(deck == null) return false;
        if(deck.length < 2) return false;

        HashMap<Integer,Integer> map = new HashMap<>();

        for(int i = 0 ; i< deck.length ;i++){
            Integer count = map.get(deck[i]);
            int value = count == null ? 1 : count +1;
            map.put(deck[i],value);
        }

        Set<Integer> keys =map.keySet();
       int c = -1;
       for(int k :keys) {

           int v = map.get(k);
           c = c== -1 ? v : gcd(c,v);
            if(c == 1) return  false;
       }
        return true;

    }

    public int gcd(int a,int  b){
        int min = Math.min(a,b);
        int c =1;
        for(int i = 2 ; i <=min ; i++){
            if( a % i == 0 && b %i ==0){
                c = i;
            }
        }
        return  c;
    }


    public List<Integer> rightSideView(TreeNode root) {
        if(root == null) return null;
        List<Integer> res =  new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while(!queue.isEmpty()){

            int size = queue.size();
            int index = size -1;
            for(int i = 0;i< size; i++){
                if(index == i) res.add(queue.peek().val);
                TreeNode node =  queue.poll();
                if(node.left != null){
                    queue.add(node.left);
                }

                if(node.right != null){
                    queue.add(node.right);
                }
            }


        }

        return res;

    }

    @Test
    public void testRightSideView(){
        String s =  "1,2,3,null,5,null,4";
        List<Integer> array = ArrayFactory.apply(s);
        TreeNode root = getTree(array);
        List<Integer> l= rightSideView(root);
        l.forEach(System.out::println);
    }


    @Test
    public void testHasGroupsSizeX(){

            int[] deck = new int[]{1,1,1,1,2,2,2,2,2,2};
           System.out.println(hasGroupsSizeX(deck));
        //System.out.println(gcd(7,6));
    }
}
