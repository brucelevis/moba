package info.chenliang.moba.login;

import info.chenliang.moba.message.LoginToMatchProxy;
import io.netty.channel.Channel;

/**
 * Created by chenliang on 16/5/11.
 */
public class LoginServerProxy extends LoginToMatchProxy {
    private LoginServerStub loginServerStub;

    public LoginServerProxy(Channel channel) {
        this.loginServerStub = new LoginServerStub(channel);
    }
}
