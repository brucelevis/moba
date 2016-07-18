package info.chenliang.moba.nobody.login;

import info.chenliang.moba.message.LoginToClientDispatcher;

import java.io.DataInputStream;

/**
 * Created by chenliang on 16/5/11.
 */
public class PlayerProxyTask implements Runnable {
    PlayerProxy playerProxy;
    DataInputStream dataInputStream;

    public PlayerProxyTask(PlayerProxy playerProxy, DataInputStream dataInputStream) {
        this.playerProxy = playerProxy;
        this.dataInputStream = dataInputStream;
    }

    @Override
    public void run() {
        try {
            LoginToClientDispatcher.dispatch(dataInputStream, playerProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
