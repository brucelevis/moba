package info.chenliang.moba.nobody.battle;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by chenliang on 16/5/24.
 */
public class PlayerToBattleStub extends info.chenliang.moba.message.PlayerToBattleStub {
    ByteBuf byteBuf = Unpooled.buffer();
    private Channel channel;

    public PlayerToBattleStub(Channel channel) {
        this.channel = channel;
    }

    @Override
    protected DataOutputStream begin() {
        byteBuf.retain();
        byteBuf.clear();
        DataOutputStream dataOutputStream = null;
        try {
            dataOutputStream = new DataOutputStream(new ByteBufOutputStream(byteBuf));
            dataOutputStream.writeShort(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataOutputStream;
    }

    @Override
    protected void end() {
        byteBuf.setShort(0, byteBuf.readableBytes() - 2);
        channel.writeAndFlush(byteBuf);
    }

    @Override
    public synchronized void playerPing() throws Exception {
        super.playerPing();
    }

    @Override
    public synchronized void playerPong() throws Exception {
        super.playerPong();
    }

    @Override
    public synchronized void play() throws Exception {
        super.play();
    }

    @Override
    public synchronized void moveToPosition(float x, float z) throws Exception {
        super.moveToPosition(x, z);
    }
}
