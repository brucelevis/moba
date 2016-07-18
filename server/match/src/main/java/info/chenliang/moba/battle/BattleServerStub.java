package info.chenliang.moba.battle;

import info.chenliang.moba.message.MatchToLoginStub;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by chenliang on 16/5/11.
 */
public class BattleServerStub extends MatchToLoginStub {

    ByteBuf byteBuf = Unpooled.buffer();
    Channel channel;

    public BattleServerStub(Channel channel) {
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
}
