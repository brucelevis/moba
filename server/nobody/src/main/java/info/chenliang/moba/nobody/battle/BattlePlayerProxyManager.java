package info.chenliang.moba.nobody.battle;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chenliang on 16/5/11.
 */
public class BattlePlayerProxyManager {
    private Map<Channel, BattlePlayerProxy> playerMap;

    private static BattlePlayerProxyManager instance = new BattlePlayerProxyManager();
    public synchronized static BattlePlayerProxyManager getInstance(){
        return instance;
    }

    public void init(){
        playerMap = new ConcurrentHashMap<>();
    }

    public synchronized void addBattlePlayerProxy(BattlePlayerProxy playerProxy){
        playerMap.put(playerProxy.getChannel(), playerProxy);
    }

    public synchronized BattlePlayerProxy removeBattlePlayerProxy(Channel channel){
        return playerMap.remove(channel);
    }

    public synchronized BattlePlayerProxy getBattlePlayerProxy(Channel channel){
        return playerMap.get(channel);
    }

}
