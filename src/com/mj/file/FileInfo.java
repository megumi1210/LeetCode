package com.mj.file;


/**
 * 文件信息类
 *
 * @author megumi
 * @version 1.0
 */
public class FileInfo {
    //文件的行数
    private int lines;
    //文件的数量
    private int files;
    //文件的内容
    private String content = "";

    //返回单词的数量
    public String[] words() {
        return content.split("[^a-zA-Z]");//按照非字母的字符分割
    }


    public FileInfo append(FileInfo info) {
        if (info != null && info.lines > 0) {
            this.files += info.files;
            this.lines += info.lines;
            this.content = new StringBuilder(this.content)
                    .append("\n")
                    .append(info.content)
                    .toString();
        }
        return this;
    }
   /*------------------------------------------------------------------------------------*/
   //                 getter ans setter
    /*------------------------------------------------------------------------------------*/
    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public int getFiles() {
        return files;
    }

    public void setFiles(int files) {
        this.files = files;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
