package info.chenliang.moba.nobody.login;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chenliang on 16/5/11.
 */
public class PlayerProxyManager {
    private Map<Channel, PlayerProxy> playerMap;

    private static PlayerProxyManager instance = new PlayerProxyManager();
    public synchronized static PlayerProxyManager getInstance(){
        return instance;
    }

    public void init(){
        playerMap = new ConcurrentHashMap<>();
    }

    public void addPlayerProxy(PlayerProxy playerProxy){
        playerMap.put(playerProxy.channel, playerProxy);
    }

    public PlayerProxy removePlayer(Channel channel){
        return playerMap.remove(channel);
    }

    public PlayerProxy getPlayerProxy(Channel channel){
        return playerMap.get(channel);
    }

}
