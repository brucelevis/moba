package info.chenliang.moba.login.player;

import info.chenliang.moba.message.ClientToLoginProxy;
import io.netty.channel.Channel;

/**
 * Created by chenliang on 16/5/10.
 */
public class Player extends ClientToLoginProxy{
    Channel channel;
    private PlayerToClientStub playerToClientStub;
    public static Player alloc(Channel channel){
        return new Player(channel);
    }

    public Player(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }
    public void init(){
        playerToClientStub = new PlayerToClientStub(this);
    }

    @Override
    public void login(String username, String password) {
        System.out.println("login " + username + " " + password);
        try {
            info.chenliang.moba.message.Player player = new info.chenliang.moba.message.Player();
            player.id = 111;
            player.name = "whatthefuck";
            playerToClientStub.onLogin(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
