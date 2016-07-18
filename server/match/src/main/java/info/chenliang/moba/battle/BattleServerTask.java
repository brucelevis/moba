package info.chenliang.moba.battle;

import info.chenliang.moba.message.BattleToMatchDispatcher;
import info.chenliang.moba.message.BattleToMatchProxy;

import java.io.DataInputStream;

/**
 * Created by chenliang on 16/5/11.
 */
public class BattleServerTask implements Runnable {
    private final DataInputStream dataInputStream;
    private final BattleServerProxy battleServerProxy;

    public BattleServerTask(DataInputStream dataInputStream, BattleServerProxy battleServerProxy) {
        this.dataInputStream = dataInputStream;
        this.battleServerProxy = battleServerProxy;
    }

    @Override
    public void run() {
        try {
            BattleToMatchDispatcher.dispatch(dataInputStream, battleServerProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
