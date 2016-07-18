package info.chenliang.moba.nobody.login;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.List;

/**
 * Created by chenliang on 16/5/11.
 */
public class LoginMessageHandler extends ByteToMessageDecoder {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        PlayerProxy playerProxy = new PlayerProxy(ctx.channel());
        PlayerProxyManager.getInstance().addPlayerProxy(playerProxy);
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
        PlayerProxy playerProxy = PlayerProxyManager.getInstance().getPlayerProxy(ctx.channel());
        FakeClientExecutor.getInstance().execute(new PlayerProxyTask(playerProxy, dataInputStream));
    }
}
