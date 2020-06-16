package com.megumi.util.set;

import com.megumi.util.TimeTool;
import com.mj.file.FileInfo;
import com.mj.file.Files;
import org.junit.Test;



public class TreeSetTest {
    Set<Integer> set = new TreeSet2<>();

    Integer[] data = new Integer[]{
      3,6,8,7,10,11,10,12,13,3,8,2,5
    };
    @Test
    public void testAdd(){
        for (int i : data) {
            set.add(i);
        }

        set.traversal(new Set.Visitor<Integer>() {
            @Override
            boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });
    }

    @Test
    public void testPerformance(){
        FileInfo info = Files.read("D:\\usr\\local\\Java\\JDK\\src\\java\\util",
                new String[]{"java"});

        TreeSet<String> s = new TreeSet<>();

        TimeTool.test("TreeSet" ,()->{
            for(String w : info.words()){
                s.add(w);
            }

        });

        System.out.println(s.size());
    }
}
