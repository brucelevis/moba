package info.chenliang.moba.battle;

import info.chenliang.moba.battle.arena.ArenaManager;
import info.chenliang.moba.battle.handler.MatchServerChannelInitializer;
import info.chenliang.moba.player.handler.PlayerChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by chenliang on 16/5/11.
 */
public class BattleServer {
    private String host;
    private int port;

    public BattleServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private void startBattleServer() throws Exception {
        ArenaManager.getInstance().init();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new PlayerChannelInitializer())
                .option(ChannelOption.SO_BACKLOG, 256)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private void initMatch() throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .handler(new MatchServerChannelInitializer());

        ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
    }

    public void start() throws Exception{
        startBattleServer();
//        initMatch();
    }
}
