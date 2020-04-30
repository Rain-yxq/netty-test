package com.rain.nio;

import java.nio.ByteBuffer;

/**
 * 演示将普通buffer转成只读buffer
 *
 * @author 左边
 * @date 2020/4/30 2:48 下午
 */
public class No3_ReadOnlyBuffer {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for (byte i = 0; i < buffer.capacity(); i++) {
            buffer.put(i);
        }

        // 翻转读取
        buffer.flip();

        // 将buffer转成只读
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        //读取
        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }

        // 只读buffer如果写的话会抛出ReadOnlyBufferException
        readOnlyBuffer.put((byte) 100);

    }
}
