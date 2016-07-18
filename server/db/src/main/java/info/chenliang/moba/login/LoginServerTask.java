package info.chenliang.moba.login;

import info.chenliang.moba.message.LoginToDbDispatcher;
import info.chenliang.moba.message.LoginToDbProxy;

import java.io.DataInputStream;

/**
 * Created by chenliang on 16/5/11.
 */
public class LoginServerTask implements Runnable{
    private final DataInputStream dataInputStream;
    private final LoginToDbProxy loginToDbProxy;

    public LoginServerTask(DataInputStream dataInputStream, LoginToDbProxy loginToDbProxy) {
        this.dataInputStream = dataInputStream;
        this.loginToDbProxy = loginToDbProxy;
    }

    @Override
    public void run() {
        try {
            LoginToDbDispatcher.dispatch(dataInputStream, loginToDbProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
