package info.chenliang.moba.player.handler;

import info.chenliang.moba.battle.arena.Arena;
import info.chenliang.moba.battle.arena.ArenaManager;
import info.chenliang.moba.battle.arena.entity.Entity;
import info.chenliang.moba.player.PlayerCommand;
import info.chenliang.moba.player.PlayerProxy;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.AttributeKey;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.List;

/**
 * Created by chenliang on 16/5/10.
 */
public class PlayerConnectionHandler extends ByteToMessageDecoder {
    private AttributeKey<PlayerProxy> PLAYER_PROXY_KEY;

    private final static Logger logger = LogManager.getLogger(PlayerConnectionHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channel active");
        super.channelActive(ctx);

        PlayerProxy playerProxy = new PlayerProxy(ctx.channel());
        PLAYER_PROXY_KEY = new AttributeKey<>(playerProxy.getId() + "");
        ctx.attr(PLAYER_PROXY_KEY).set(playerProxy);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        logger.info("channelInactive");
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 2){
            logger.info("less 2");
            return;
        }

        int length = in.getShort(0);
        if (in.readableBytes() < length + 2){
            logger.info("not enough " + in.readableBytes() + " " + (length + 2));
            return;
        }

        logger.info("bytes=" + in.readableBytes() + " to read=" + length);
        in.skipBytes(2);

        ByteBuf byteBuf = ctx.alloc().heapBuffer(length, length);
        in.readBytes(byteBuf, length);

        int protocolId = byteBuf.getByte(0);
        PlayerProxy playerProxy = ctx.attr(PLAYER_PROXY_KEY).get();
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(byteBuf.array()));

        logger.info(String.format("length=%d protocol=%d", length, protocolId));

        //TODO use generated constant ids
        if (protocolId == 2) {

            // allocate arena for player
            Arena arena = ArenaManager.getInstance().onPlayerPlay(playerProxy);
            Entity entity = arena.onPlayerJoin(playerProxy);
            playerProxy.joinArena(arena, entity);
        }

        playerProxy.getArena().addPlayerCommand(new PlayerCommand(playerProxy, dataInputStream, protocolId));
    }
}
