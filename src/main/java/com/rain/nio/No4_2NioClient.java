package com.rain.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author 左边
 * @date 2020/4/30 5:27 下午
 */
public class No4_2NioClient {
    public static void main(String[] args) throws IOException {
        SocketChannel clientChannel = SocketChannel.open();
        clientChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 6666);

        // connect方法连接服务器，成功返回true
        if (!clientChannel.connect(address)) {
            //因为是非阻塞的，所以如果没连接成功，可以做其他事情，然后不断轮询连接状态即可
            while (!clientChannel.finishConnect()) {
                System.out.println("做其他事情");
            }
        }

        // 如果连接成功，向服务端发送数据
        String str = "hello~";
        //Wraps a byte array into a buffer
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        //发送数据，将 buffer 数据写入 channel
        clientChannel.write(buffer);
        System.in.read();

    }
}
