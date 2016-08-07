/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package study.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * TCP服务端的NIO写法
 * <p/>
 * 到目前为止，所举的案例中都没有涉及Selector。不要急，好东西要慢慢来。
 * Selector类可以用于避免使用阻塞式客户端中很浪费资源的“忙等”方法
 * 。例如，考虑一个IM服务器。像QQ或者旺旺这样的，可能有几万甚至几千万个客户端同时连接到了服务器
 * 但在任何时刻都只是非常少量的消息。
 * <p/>
 * 需要读取和分发。这就需要一种方法阻塞等待，直到至少有一个信道可以进行I/O操作，
 * 并指出是哪个信道。NIO的选择器就实现了这样的功能。一个Selector实例可以同时检查一组信道的I/O状态。
 * 用专业术语来说，选择器就是一个多路开关选择器，因为一个选择器能够管理多个信道上的I/O操作。
 * 然而如果用传统的方式来处理这么多客户端，使用的方法是循环地一个一个地去检查所有的客户端是否有I/O操作，
 * 如果当前客户端有I/O操作，则可能把当前客户端扔给一个线程池去处理，如果没有I/O操作则进行下一个轮询，
 * 当所有的客户端都轮询过了又接着从头开始轮询；这种方法是非常笨而且也非常浪费资源，
 * 因为大部分客户端是没有I/O操作，我们也要去检查；而Selector就不一样了，它在内部可以同时管理多个I/O，
 * 当一个信道有I/O操作的时候，他会通知Selector，Selector就是记住这个信道有I/O操作，并且知道是何种I/O操作，是读呢？是写呢？
 * 还是接受新的连接；所以如果使用Selector，它返回的结果只有两种结果，一种是0，即在你调用的时刻没有任何客户端需要I/O操作，
 * 另一种结果是一组需要I/O操作的客户端，这是你就根本不需要再检查了，因为它返回给你的肯定是你想要的。
 * 这样一种通知的方式比那种主动轮询的方式要高效得多！
 * <p/>
 * 要使用选择器（Selector），需要创建一个Selector实例（使用静态工厂方法open()）并将其注册（register）
 * 到想要监控的信道上（注意，这要通过channel的方法实现，而不是使用selector的方法）。
 * 最后，调用选择器的select()方法。该方法会阻塞等待，直到有一个或更多的信道准备好了I/O操作或等待超时。
 * select()方法将返回可进行I/O操作的信道数量。现在，在一个单独的线程中，通过调用select()方法就能检查多个信道是否准备好进行I/O操作。
 * 如果经过一段时间后仍然没有信道准备好，select()方法就会返回0，并允许程序继续执行其他任务。
 */
public class SelectorServerConnect {
    private static final int BUF_SIZE = 1024;
    private static final int PORT = 8080;
    private static final int TIMEOUT = 3000;

    public static void main(String[] args) {
        selector();
    }

    public static void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
        SocketChannel sc = ssChannel.accept();
        sc.configureBlocking(false);
        sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE));
    }

    public static void handleRead(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer buf = (ByteBuffer) key.attachment();
        long bytesRead = sc.read(buf);
        while (bytesRead > 0) {
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }
            System.out.println();
            buf.clear();
            bytesRead = sc.read(buf);
        }
        if (bytesRead == -1) {
            sc.close();
        }
    }

    public static void handleWrite(SelectionKey key) throws IOException {
        ByteBuffer buf = (ByteBuffer) key.attachment();
        buf.flip();
        SocketChannel sc = (SocketChannel) key.channel();
        while (buf.hasRemaining()) {
            sc.write(buf);
        }
        buf.compact();
    }

    public static void selector() {
        Selector selector = null;
        ServerSocketChannel ssc = null;
        try {
            selector = Selector.open();
            ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(PORT));

            /**
             * 与Selector一起使用时，Channel必须处于非阻塞模式下。这意味着不能将FileChannel与Selector一起使用，
             * 因为FileChannel不能切换到非阻塞模式。而套接字通道都可以
             */
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                if (selector.select(TIMEOUT) == 0) {
                    System.out.println("==");
                    continue;
                }
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isAcceptable()) {
                        handleAccept(key);
                    }
                    if (key.isReadable()) {
                        handleRead(key);
                    }
                    if (key.isWritable() && key.isValid()) {
                        handleWrite(key);
                    }
                    if (key.isConnectable()) {
                        System.out.println("isConnectable = true");
                    }
                    iter.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (selector != null) {
                    selector.close();
                }
                if (ssc != null) {
                    ssc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

