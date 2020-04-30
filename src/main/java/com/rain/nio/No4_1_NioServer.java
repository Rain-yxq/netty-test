package com.rain.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author 左边
 * @date 2020/4/30 4:51 下午
 */
public class No4_1_NioServer {
    public static void main(String[] args) throws Exception {
        // 创建服务端channel
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        // 绑定端口
        ssChannel.socket().bind(new InetSocketAddress(6666));

        // 创建Selector
        Selector selector = Selector.open();
        // 将ssChannel设置为非阻塞
        ssChannel.configureBlocking(false);
        // 将ssChannel注册到selector，并设置监听的事件为OP_ACCEPT
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 开始循环等待客户端连接
        while (true) {
            // 每次循环阻塞1s等待客户端连接
            if (selector.select(1000) == 0) {
                System.out.println("正在等待客户端连接...");
                // select方法返回0说明没有客户端连接就直接进入下次循环
                continue;
            }

            // 如果select方法返回的 > 0， 表示已经获取到关注的事件。通过selectedKeys() 返回关注事件的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    // 如果是 OP_ACCEPT 事件, 有新的客户端连接，为该客户端生成一个 SocketChannel
                    SocketChannel clientChannel = ssChannel.accept();
                    // 将clientChannel设为非阻塞
                    clientChannel.configureBlocking(false);
                    // 将clientChannel也注册到Selector中，并给clientChannel一个缓冲
                    clientChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                if (key.isReadable()) {
                    // 如果是 OP_READ 事件，说明此时client已经有了channel，因此通过key获取客户端的channel
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    // 再通过key获取到客户端的buffer
                    ByteBuffer clientBuffer = (ByteBuffer) key.attachment();
                    // 将channel中的数据读到buffer中
                    clientChannel.read(clientBuffer);

                    String clientMsg = new String(clientBuffer.array());
                    System.out.println("From 客户端: " + clientMsg);
                }
                //手动从集合中移动当前的selectionKey, 防止重复操作
                iterator.remove();
            }
        }
    }
}
