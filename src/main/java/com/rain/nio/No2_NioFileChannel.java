package com.rain.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 演示通过Channel和buffer将字符串写入文件
 *
 * @author 左边
 * @date 2020/4/29 10:16 下午
 */
public class No2_NioFileChannel {

    private static final int SIZE_1K = 1024;
    private static final String FILE_PATH = "/Users/rain/Desktop/nio.txt";


    public static void main(String[] args) throws IOException {

        // 通过nio写出文件
//        writeFile();
        // 通过nio读取文件
//        readFile();
        // 使用同一个buffer进行文件的读和写
//        readAndWriteFile();
        // 使用channel的transform拷贝文件
        copyFile();
    }

    private static void copyFile() throws IOException {
        FileInputStream fis = new FileInputStream("test1.jpg");
        FileOutputStream fos = new FileOutputStream("copy.jpg");

        FileChannel readChannel = fis.getChannel();
        FileChannel writeChannel = fos.getChannel();

        writeChannel.transferFrom(readChannel, 0, readChannel.size());

        fis.close();
        fos.close();
    }

    private static void readAndWriteFile() throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(SIZE_1K);
        FileInputStream fis = new FileInputStream("1.txt");
        FileOutputStream fos = new FileOutputStream("2.txt");
        FileChannel readChannel = fis.getChannel();
        FileChannel writeChannel = fos.getChannel();
        readChannel.read(buffer);
        buffer.flip();
        writeChannel.write(buffer);

        fis.close();
        fos.close();

    }

    private static void readFile() throws IOException  {
        // 创建输入流
        FileInputStream fis = new FileInputStream(FILE_PATH);
        // 通过输入流获取channel
        FileChannel channel = fis.getChannel();
        // 创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(SIZE_1K);
        // 将channel中输入流的内容读取到缓冲区
        channel.read(buffer);
        // 翻转buffer
        buffer.flip();
        String s = new String(buffer.array(), buffer.position(), buffer.limit());
        System.out.println(s);


    }

    private static void writeFile() throws IOException {
        String target = "hello world";

        // 创建输出流
        FileOutputStream fos = new FileOutputStream(FILE_PATH);
        // 通过输出流获取channel
        FileChannel channel = fos.getChannel();
        // 创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(SIZE_1K);
        // 将待输出的字符串放入buffer
        buffer.put(target.getBytes());
        // 翻转buffer准备将刚才存放的数据写入到channel
        buffer.flip();
        // 将刚才存放的数据写入到channel
        channel.write(buffer);
        fos.close();
    }


}
