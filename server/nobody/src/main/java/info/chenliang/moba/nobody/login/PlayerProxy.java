package info.chenliang.moba.nobody.login;

import info.chenliang.moba.message.LoginToClientProxy;
import info.chenliang.moba.message.Player;
import io.netty.channel.Channel;

/**
 * Created by chenliang on 16/5/11.
 */
public class PlayerProxy extends LoginToClientProxy {
    Channel channel;

    public PlayerProxy(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void onLogin(Player player) {
        System.out.println("login success id="+player.id+" name="+player.name);
    }


}
