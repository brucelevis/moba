package info.chenliang.moba.match;

import info.chenliang.moba.message.MatchToBattleDispatcher;

import java.io.DataInputStream;

/**
 * Created by chenliang on 16/5/11.
 */
public class MatchServerTask implements Runnable{
    private final DataInputStream dataInputStream;
    private final MatchServerProxy matchServerProxy;

    public MatchServerTask(DataInputStream dataInputStream, MatchServerProxy matchServerProxy) {
        this.dataInputStream = dataInputStream;
        this.matchServerProxy = matchServerProxy;
    }

    @Override
    public void run() {
        try {
            MatchToBattleDispatcher.dispatch(dataInputStream, matchServerProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
