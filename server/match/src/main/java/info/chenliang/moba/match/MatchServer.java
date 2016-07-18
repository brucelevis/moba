package info.chenliang.moba.match;

import info.chenliang.moba.battle.BattleServer;
import info.chenliang.moba.battle.BattleServerManager;
import info.chenliang.moba.login.LoginServer;
import info.chenliang.moba.login.LoginServerManager;

/**
 * Created by chenliang on 16/5/11.
 */
public class MatchServer {


    private int battleServerCount = 1;
    private int battleServerPortBase = 5000; // 5499 max

    private int loginServerCount = 1;
    private int loginServerPortBase = 5500; // 5999 max

    private void initBattleServers() throws Exception{
        for (int i=0; i < battleServerCount; i++){
            BattleServer battleServer = new BattleServer(i, battleServerPortBase + i);
            battleServer.init();
            BattleServerManager.getInstance().addBattleServer(battleServer);
        }

    }

    private void initLoginServers() throws Exception{
        for (int i = 0; i < loginServerCount; i++) {
            LoginServer loginServer = new LoginServer(i, loginServerPortBase + i);
            loginServer.init();
            LoginServerManager.getInstance().addLoginServer(loginServer);
        }
    }

    public void start() throws Exception{
        initBattleServers();
        initLoginServers();
    }


    private void shutdown(){

    }
}
