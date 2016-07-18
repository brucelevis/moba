package info.chenliang.moba.login.player;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chenliang on 16/5/10.
 */
public class PlayerManager {
    private PlayerManager(){}

    private Map<Channel, Player> playerMap;

    private static PlayerManager instance = new PlayerManager();
    public synchronized static PlayerManager getInstance(){
        return instance;
    }

    public void init(){
        playerMap = new ConcurrentHashMap<>();
    }

    public void addPlayer(Player player){
        playerMap.put(player.getChannel(), player);
    }

    public Player removePlayer(Channel channel){
        return playerMap.remove(channel);
    }

    public Player getPlayer(Channel channel){
        return playerMap.get(channel);
    }

}
