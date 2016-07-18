package info.chenliang.moba.login;

import info.chenliang.moba.login.player.Player;
import info.chenliang.moba.message.ClientToLoginDispatcher;

import java.io.DataInputStream;

/**
 * Created by chenliang on 16/5/11.
 */
public class PlayerTask implements Runnable{
    private final DataInputStream dataInputStream;
    private final Player player;

    public PlayerTask(DataInputStream dataInputStream, Player player) {
        this.dataInputStream = dataInputStream;
        this.player = player;
    }

    @Override
    public void run() {
        try {
            ClientToLoginDispatcher.dispatch(dataInputStream, player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
