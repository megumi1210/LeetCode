package com.megumi.util.map;

import com.megumi.util.TimeTool;
import com.mj.file.FileInfo;
import com.mj.file.Files;
import org.junit.Test;


public class TreeMapTest {

    @Test
    public void test() {
        Map<String, Integer> map = new TreeMap<>();
        map.put("c", 2);
        map.put("a", 5);
        map.put("b", 6);
        map.put("a", 8);

        map.traversal(new Map.Visitor<String, Integer>() {
            @Override
            protected boolean visit(String key, Integer value) {
                System.out.println(key + ":" + value);
                return false;
            }
        });

    }

    @Test
    public void testPerformance() {
        Map<String, Integer> map = new TreeMap<>();


        FileInfo info = Files.read("D:\\usr\\local\\Java\\JDK\\src\\java\\util",
                new String[]{"java"});

        System.out.println("文件数量：" + info.getFiles());
        System.out.println("单词数量：" + info.words().length);

        TimeTool.test("TreeMap", () -> {
            for (String w : info.words()) {
                Integer count = map.get(w);
                count = count == null ? 0 : count;
                map.put(w, count + 1);
            }

        });


        System.out.println("去重后单词数量：" + map.size());
    }
}
