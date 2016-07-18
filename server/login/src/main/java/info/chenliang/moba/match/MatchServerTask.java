package info.chenliang.moba.match;

import info.chenliang.moba.message.MatchToLoginDispatcher;
import info.chenliang.moba.message.MatchToLoginProxy;

import java.io.DataInputStream;

/**
 * Created by chenliang on 16/5/11.
 */
public class MatchServerTask implements Runnable{
    private final DataInputStream dataInputStream;
    private final MatchToLoginProxy matchToLoginProxy;

    public MatchServerTask(DataInputStream dataInputStream, MatchToLoginProxy matchToLoginProxy) {
        this.dataInputStream = dataInputStream;
        this.matchToLoginProxy = matchToLoginProxy;
    }

    @Override
    public void run() {
        try {
            MatchToLoginDispatcher.dispatch(dataInputStream, matchToLoginProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
