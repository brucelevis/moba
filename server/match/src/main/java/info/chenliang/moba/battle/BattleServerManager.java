package info.chenliang.moba.battle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenliang on 16/5/11.
 */
public class BattleServerManager {
    private BattleServerManager(){}

    private static BattleServerManager instance = new BattleServerManager();

    public static BattleServerManager getInstance(){
        return instance;
    }

    private Map<Integer, BattleServer> battleServerMap = new HashMap<>();

    public void addBattleServer(BattleServer battleServer){
        battleServerMap.put(battleServer.getId(), battleServer);
    }

    public BattleServer getBattleServerById(int id){
        return battleServerMap.get(id);
    }
}
