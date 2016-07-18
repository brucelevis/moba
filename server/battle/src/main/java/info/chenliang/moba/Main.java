package info.chenliang.moba;

import info.chenliang.moba.battle.BattleServer;

/**
 * Created by chenliang on 16/5/11.
 */
public class Main {
    public static void main(String[] args) throws Exception{
        BattleServer battleServer = new BattleServer("localhost", 6000);
        battleServer.start();
    }
}
