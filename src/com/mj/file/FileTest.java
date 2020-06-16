package com.mj.file;


import org.junit.Test;


public class FileTest {

    @Test
    public  void testReadDir(){
        FileInfo info1 = Files.read("D:\\usr\\local\\Java\\JDK\\src\\java\\util",
                             new String[]{"java"});
        FileInfo info2 =Files.read("D:\\tmp",new String[]{"java"});
        System.out.println(info2.getFiles());
        System.out.println(info2.getLines());
    }

   @Test
    public void testReadFile(){
        FileInfo info = Files.read("D:\\tmp\\AbstractCollection.java");
       // System.out.println(info.getContent());
        System.out.println(info.getLines());
        System.out.println(info.words().length);
    }
}
