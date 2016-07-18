package info.chenliang.moba.nobody.login;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

/**
 * Created by chenliang on 16/5/10.
 */
public class FakeClient {
    public static void main(String[] args) throws Exception{
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new FakeClientChannelInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 1777).sync();

            final ClientToLoginStubImpl clientToLoginStub = new ClientToLoginStubImpl(channelFuture.channel());


            FakeClientExecutor.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        clientToLoginStub.login("angelleecash", "password");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 2, TimeUnit.SECONDS);

            channelFuture.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
