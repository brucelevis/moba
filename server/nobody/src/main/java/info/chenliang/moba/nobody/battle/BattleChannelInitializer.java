package info.chenliang.moba.nobody.battle;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by chenliang on 16/5/10.
 */
public class BattleChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new BattleChannelHandler());
    }
}
