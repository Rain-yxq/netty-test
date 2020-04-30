package com.rain.bio;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 左边
 * @date 2020/4/29 3:28 下午
 */
public class BioServer {
    public static void main(String[] args) throws IOException {

        // 创建服务端Socket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("server is start...");

        // 创建线程池
        ExecutorService pool = Executors.newCachedThreadPool();

        // 服务端循环监听6666端口
        while (true) {
            System.out.println("线程信息 id =" + Thread.currentThread().getId() + " 名字=" + Thread.currentThread().getName());

            // （阻塞）监听客户端socket连接
            final Socket customerSocket = serverSocket.accept();
            System.out.println("发现客户端连接...");
            // 当监听到客户端连接，就创建一个线程与之通信
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    handlerRequest(customerSocket);
                }
            });
        }
    }

    private static void handlerRequest(Socket customerSocket) {

        try {
            System.out.println("线程信息 id =" + Thread.currentThread().getId() + " 名字=" + Thread.currentThread().getName());
            byte[] bArray = new byte[1024];
            InputStream inputStream = customerSocket.getInputStream();
            while (true) {
                System.out.println("线程信息 id =" + Thread.currentThread().getId() + " 名字=" + Thread.currentThread().getName());
                // （阻塞）读取客户端socket的输入到byte数组
                int read = inputStream.read(bArray);
                if (read != -1) {
                    // 将byte数组转成String进行输出
                    System.out.println(new String(bArray, 0, read));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭了客户端连接");
            try {
                customerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
