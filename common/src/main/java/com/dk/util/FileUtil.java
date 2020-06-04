package com.dk.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by scott on 2017/7/9.
 */
public class FileUtil {

        // 把二进制文件读入字节数组，如果没有内容，字节数组为null
        public static byte[] read(String filePath) {
            byte[] data = null;
            try {
                BufferedInputStream in = new BufferedInputStream(
                        new FileInputStream(filePath));
                try {
                    data = new byte[in.available()];
                    in.read(data);
                } finally {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        // 把字节数组为写入二进制文件，数组为null时直接返回
        /*public static void write(String filePath, byte[] data) {
            if (data == null)
                return;
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(filePath,true));
                try {
                    out.write(data,20,data.length);
                } finally {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/


    // 把字节数组为写入二进制文件，数组为null时直接返回
    public static void write(String filePath, byte[] data) {
        if (data == null)
            return;
        try {
            RandomAccessFile randomFile = new RandomAccessFile(filePath, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            //将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            try {
                randomFile.write(data);
            } finally {
                randomFile.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  static  void main(String [] args) throws Exception {


        byte []  data = new byte[20];
        for (int i=0;i<data.length;i++){
            data[i] = 1;
        }
        write("wuyao.txt",data);
        write("wuyao.txt",data);
        write("wuyao.txt",data);
    }
}
