package info.chenliang.moba.player;

import info.chenliang.moba.message.BattleToPlayerStub;
import info.chenliang.moba.message.Sync;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by chenliang on 16/5/17.
 */
public class PlayerStub extends BattleToPlayerStub {
    ByteBuf byteBuf = Unpooled.buffer();
    Channel channel;

    private final static Logger logger = LogManager.getLogger(PlayerStub.class);

    public PlayerStub(Channel channel) {
        this.channel = channel;
    }

    @Override
    protected DataOutputStream begin() {
//        logger.info("begin write message");

        byteBuf.retain();
        byteBuf.clear();
        DataOutputStream dataOutputStream = null;
        try {
            dataOutputStream = new DataOutputStream(new ByteBufOutputStream(byteBuf));
            dataOutputStream.writeShort(0);
        } catch (IOException e) {
            logger.error(e);
        }
        return dataOutputStream;
    }

    @Override
    protected void end() {
        byteBuf.setShort(0, byteBuf.readableBytes() - 2);
        channel.writeAndFlush(byteBuf);
//        logger.info("end write message");
    }


    @Override
    public synchronized void battlePing() throws Exception {
        super.battlePing();
    }

    @Override
    public synchronized void battlePong() throws Exception {
        super.battlePong();
    }

    @Override
    public synchronized void onJoinArena(int arenaId, Sync snapshot, int entityId) throws Exception {
        super.onJoinArena(arenaId, snapshot, entityId);
    }

    @Override
    public synchronized void sync(Sync sync) throws Exception {
        super.sync(sync);
    }
}
