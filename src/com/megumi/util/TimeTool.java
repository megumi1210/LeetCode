package com.megumi.util;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试算法的时间消耗
 *
 * @author megumi
 * @version 1.0
 */
public class TimeTool {
    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:SS:sss");



    public static void test(String title, Task task) {
        if (task == null) return;
        if (title == null) title = "";
        System.out.println("【" + title + "】");


        System.out.println("开始:" + sdf.format(new Date()));
        long start = System.currentTimeMillis();
        task.execute();
        long end = System.currentTimeMillis();
        System.out.println("结束:" + sdf.format(new Date()));


        double delta = (end - start) / 1000.0;
        System.out.println("耗时：" + delta + "秒");
        System.out.println("------------------------------------------");

    }

    public interface Task {

        void execute();
    }



}
