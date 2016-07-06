package study.netty;

import io.netty.buffer.ByteBuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
//
//        /**
//         * 这个低效的循环事实上可以简化为:System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII))
//         或者，你可以在这里调用in.release()。
//         */
//        ByteBuf in = (ByteBuf) msg;
//        try {
//            while (in.isReadable()) { // (1)
//                System.out.print((char) in.readByte());
//                System.out.flush();
//            }
//        } finally {
//            ReferenceCountUtil.release(msg); // (2)
//        }
//    }

//
//     ECHO服务（响应式协议）

    /**
     * 1. ChannelHandlerContext对象提供了许多操作，使你能够触发各种各样的I/O事件和操作。这里我们调用了write(Object)方法来逐字地把接受到的消息写入。请注意不同于DISCARD的例子我们并没有释放接受到的消息，这是因为当写入的时候Netty已经帮我们释放了。
     2. ctx.write(Object)方法不会使消息写入到通道上，他被缓冲在了内部，你需要调用ctx.flush()方法来把缓冲区中数据强行输出。或者你可以用更简洁的cxt.writeAndFlush(msg)以达到同样的目的。

     如果你再一次运行telnet命令，你会看到服务端会发回一个你已经发送的消息。
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.write(msg); // (1)
        ctx.flush(); // (2)
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}