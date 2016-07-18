package info.chenliang.moba.battle;

import info.chenliang.moba.battle.handler.BattleServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by chenliang on 16/5/11.
 */
public class BattleServer {
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private int id;
    private int port;

    public BattleServer(int id, int port) {
        this.id = id;
        this.port = port;
    }

    public void init() throws Exception{
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new BattleServerChannelInitializer())
            .option(ChannelOption.SO_BACKLOG, 256)
            .childOption(ChannelOption.SO_KEEPALIVE, true);

        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
//            channelFuture.channel().closeFuture().addListener(new );
    }

    public int getId() {
        return id;
    }

    public int getPort() {
        return port;
    }
}
