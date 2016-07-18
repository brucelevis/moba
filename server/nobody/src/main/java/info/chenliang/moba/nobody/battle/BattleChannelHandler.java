package info.chenliang.moba.nobody.battle;

import info.chenliang.moba.message.BattleToPlayerDispatcher;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.List;

/**
 * Created by chenliang on 16/5/11.
 */
public class BattleChannelHandler extends ByteToMessageDecoder {
    private final static Logger logger = LogManager.getLogger(BattleChannelHandler.class);


    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        final BattlePlayerProxy battlePlayerProxy = new BattlePlayerProxy(ctx.channel());
        BattlePlayerProxyManager.getInstance().addBattlePlayerProxy(battlePlayerProxy);

        final Channel channel = ctx.channel();

        // join game
        logger.info("about to join game");
        try {
            battlePlayerProxy.getPlayerToBattleStub().play();
        } catch (Exception e) {
            logger.error("play exception:", e);
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 2){
            logger.info("no enough input " + in.readableBytes());
            return;
        }

        int length = in.getShort(0);
        if (in.readableBytes() < length + 2){
            logger.info("no enough data expect "+ (length + 2) +" current " + in.readableBytes());
            return;
        }

        in.skipBytes(2);

        ByteBuf byteBuf = ctx.alloc().heapBuffer(length, length);
        in.readBytes(byteBuf, length);

        int protocolId = byteBuf.getByte(0);

//        logger.info("processing protocol " + protocolId);


        final DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(byteBuf.array()));
        final BattlePlayerProxy battlePlayerProxy = BattlePlayerProxyManager.getInstance().getBattlePlayerProxy(ctx.channel());

        BattleToPlayerDispatcher.dispatch(dataInputStream, battlePlayerProxy);
    }
}
