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
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * 说完了FileChannel和Buffer, 大家应该对Buffer的用法比较了解了，这里使用SocketChannel来继续探讨NIO。
 * NIO的强大功能部分来自于Channel的非阻塞特性，套接字的某些操作可能会无限期地阻塞。
 * 例如，对accept()方法的调用可能会因为等待一个客户端连接而阻塞；对read()方法的调用可能会因为没有数据可读而阻塞，
 * 直到连接的另一端传来新的数据。总的来说，创建/接收连接或读写数据等I/O调用，都可能无限期地阻塞等待，
 * 直到底层的网络实现发生了什么。慢速的，有损耗的网络，或仅仅是简单的网络故障都可能导致任意时间的延迟。
 * 然而不幸的是，在调用一个方法之前无法知道其是否阻塞。NIO的channel抽象的一个重要特征就是可以通过配置它的阻塞行为，
 * 以实现非阻塞式的信道。
 * <p/>
 * <p/>
 * 1
 * <p/>
 * channel.configureBlocking(false)
 * 在非阻塞式信道上调用一个方法总是会立即返回。这种调用的返回值指示了所请求的操作完成的程度。
 * 例如，在一个非阻塞式ServerSocketChannel上调用accept()方法，
 * 如果有连接请求来了，则返回客户端SocketChannel，否则返回null。
 * <p/>
 * 这里先举一个TCP应用案例，客户端采用NIO实现，而服务端依旧使用IO实现。
 */
public class SocketChannelSimple {

    public static void client(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketChannel socketChannel = null;
        try
        {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1",8080));

            if(socketChannel.finishConnect())
            {
                int i=0;
                while(true)
                {
                    TimeUnit.SECONDS.sleep(1);
                    String info = "I'm "+i+++"-th information from client";
                    buffer.clear();
                    buffer.put(info.getBytes());
                    buffer.flip();
                    while(buffer.hasRemaining()){
                        System.out.println(buffer);
                        socketChannel.write(buffer);
                    }
                }
            }
        }
        catch(IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
        finally{
            try{
                if(socketChannel!=null){
                    socketChannel.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }


    public static void server(){
        ServerSocket serverSocket = null;
        InputStream in = null;
        try
        {
            serverSocket = new ServerSocket(8080);
            int recvMsgSize = 0;
            byte[] recvBuf = new byte[1024];
            while(true){
                Socket clntSocket = serverSocket.accept();
                SocketAddress clientAddress = clntSocket.getRemoteSocketAddress();
                System.out.println("Handling client at "+clientAddress);
                in = clntSocket.getInputStream();
                while((recvMsgSize=in.read(recvBuf))!=-1){
                    byte[] temp = new byte[recvMsgSize];
                    System.arraycopy(recvBuf,0, temp, 0, recvMsgSize);
                    System.out.println(new String(temp));
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally{
            try{
                if(serverSocket!=null){
                    serverSocket.close();
                }
                if(in!=null){
                    in.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
       client();
    }
}
