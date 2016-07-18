package info.chenliang.moba.login.handler;

import info.chenliang.moba.login.PlayerTask;
import info.chenliang.moba.executor.TaskExecutor;
import info.chenliang.moba.login.player.Player;
import info.chenliang.moba.login.player.PlayerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.List;

/**
 * Created by chenliang on 16/5/10.
 */
public class PlayerConnectionHandler extends ByteToMessageDecoder {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        // connection is created, allocate resource for this player
        System.out.println("channelActive");

        Player player = Player.alloc(ctx.channel());
        player.init();
        PlayerManager.getInstance().addPlayer(player);
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
        Player player = PlayerManager.getInstance().getPlayer(ctx.channel());
        TaskExecutor.getInstance().execute(new PlayerTask(dataInputStream, player));
    }
}
