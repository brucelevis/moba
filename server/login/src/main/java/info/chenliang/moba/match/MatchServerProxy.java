package info.chenliang.moba.match;

import info.chenliang.moba.message.MatchToLoginProxy;
import io.netty.channel.Channel;

/**
 * Created by chenliang on 16/5/11.
 */
public class MatchServerProxy extends MatchToLoginProxy {
    private final MatchServerStub matchServerStub;

    public MatchServerProxy(Channel channel) {
        matchServerStub = new MatchServerStub(channel);
    }
}
