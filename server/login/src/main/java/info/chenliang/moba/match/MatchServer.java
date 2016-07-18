package info.chenliang.moba.match;

import info.chenliang.moba.match.handler.MatchServerChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by chenliang on 16/5/11.
 */
public class MatchServer {
    public void start() throws Exception{
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .handler(new MatchServerChannelInitializer());

        ChannelFuture channelFuture = bootstrap.connect("localhost", 1777).sync();
    }
}
