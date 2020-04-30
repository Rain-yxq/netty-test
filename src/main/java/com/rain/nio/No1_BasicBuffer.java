package com.rain.nio;

import java.nio.IntBuffer;

/**
 * 演示nio buffer的使用
 *
 * @author 左边
 * @date 2020/4/29 4:40 下午
 */
public class No1_BasicBuffer {
    public static void main(String[] args) {

        // 创建一个Buffer, 大小为5, 即可以存放5个int(类似大小为5的int数组)
        IntBuffer intBuffer = IntBuffer.allocate(5);
        // 向intBuffer中存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i);
        }

        // buffer不只能写数据，也能读数据。上边是将数据写入到buffer，如果想要读取buffer中的数据，要从当前写的状态翻转成读，使用flip
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            int i = intBuffer.get();
            System.out.println(i);
        }
    }

}
