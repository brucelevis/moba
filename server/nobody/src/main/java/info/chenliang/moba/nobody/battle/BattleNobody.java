package info.chenliang.moba.nobody.battle;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by chenliang on 16/5/24.
 */
public class BattleNobody {
    private final static Logger logger = LogManager.getLogger(BattleNobody.class);

    public BattleNobody() {

    }

    public void init() throws Exception{
        BattlePlayerProxyManager.getInstance().init();

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new BattleChannelInitializer());
            final ChannelFuture channelFuture = bootstrap.connect("localhost", 6000).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
