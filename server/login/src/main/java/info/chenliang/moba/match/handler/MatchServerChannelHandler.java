package info.chenliang.moba.match.handler;

import info.chenliang.moba.executor.TaskExecutor;
import info.chenliang.moba.match.MatchServerProxy;
import info.chenliang.moba.match.MatchServerTask;
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
public class MatchServerChannelHandler extends ByteToMessageDecoder {
    private static final AttributeKey<MatchServerProxy> MATCH_SERVER_PROXY_KEY = new AttributeKey<>("MatchServerProxy");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        // connection is created, allocate resource for this player

        MatchServerProxy matchServerProxy = new MatchServerProxy(ctx.channel());
        ctx.attr(MATCH_SERVER_PROXY_KEY).set(matchServerProxy);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        System.out.println("channelInactive");
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

        TaskExecutor.getInstance().execute(new MatchServerTask(dataInputStream, ctx.attr(MATCH_SERVER_PROXY_KEY).get()));
    }
}
