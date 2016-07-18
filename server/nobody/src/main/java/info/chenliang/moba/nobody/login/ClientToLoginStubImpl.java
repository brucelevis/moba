package info.chenliang.moba.nobody.login;

import info.chenliang.moba.message.ClientToLoginStub;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by chenliang on 16/5/10.
 */
public class ClientToLoginStubImpl extends ClientToLoginStub {
    ByteBuf byteBuf = Unpooled.buffer();
    private Channel channel;

    public ClientToLoginStubImpl(Channel channel) {
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