package info.chenliang.moba.login.player;

import info.chenliang.moba.message.LoginToClientStub;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by chenliang on 16/5/11.
 */
public class PlayerToClientStub extends LoginToClientStub {
    Player player;
    ByteBuf byteBuf = Unpooled.buffer();

    public PlayerToClientStub(Player player) {
        this.player = player;
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
        player.channel.writeAndFlush(byteBuf);
    }
}
