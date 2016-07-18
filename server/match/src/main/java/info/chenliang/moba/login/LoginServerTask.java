package info.chenliang.moba.login;

import info.chenliang.moba.message.LoginToMatchDispatcher;
import info.chenliang.moba.message.LoginToMatchProxy;

import java.io.DataInputStream;

/**
 * Created by chenliang on 16/5/11.
 */
public class LoginServerTask implements Runnable {
    private final DataInputStream dataInputStream;
    private final LoginToMatchProxy loginToMatchProxy;

    public LoginServerTask(DataInputStream dataInputStream, LoginToMatchProxy loginToMatchProxy) {
        this.dataInputStream = dataInputStream;
        this.loginToMatchProxy = loginToMatchProxy;
    }

    @Override
    public void run() {
        try {
            LoginToMatchDispatcher.dispatch(dataInputStream, loginToMatchProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
