package info.chenliang.moba.login.handler;

import info.chenliang.moba.executor.ServerHandlerExecutor;
import info.chenliang.moba.login.LoginServerProxy;
import info.chenliang.moba.login.LoginServerTask;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.AttributeKey;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.List;

/**
 * Created by chenliang on 16/5/11.
 */
public class LoginServerHandler extends ByteToMessageDecoder {
    private static final AttributeKey<LoginServerProxy> LOGIN_SERVER_PROXY_KEY = new AttributeKey<>("LoginServerProxy");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        System.out.println("LoginServerHandler created");
        LoginServerProxy loginServerProxy = new LoginServerProxy(ctx.channel());
        ctx.attr(LOGIN_SERVER_PROXY_KEY).set(loginServerProxy);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        System.out.println("LoginServerHandler disconnected");
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 2){
            return;
        }

        in.markReaderIndex();
        int length = in.readShort() & 0xffff;
        if (in.readableBytes() < length){
            in.resetReaderIndex();
            return;
        }

        ByteBuf byteBuf = ctx.alloc().heapBuffer(length, length);
        in.readBytes(byteBuf, length);
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(byteBuf.array()));

        ServerHandlerExecutor.getInstance().execute(new LoginServerTask(dataInputStream, ctx.attr(LOGIN_SERVER_PROXY_KEY).get()));
    }
}
