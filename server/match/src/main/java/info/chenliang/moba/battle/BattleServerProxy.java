package info.chenliang.moba.battle;

import info.chenliang.moba.message.BattleToMatchProxy;
import io.netty.channel.Channel;

/**
 * Created by chenliang on 16/5/11.
 */
public class BattleServerProxy extends BattleToMatchProxy {
    private final BattleServerStub battleServerStub;

    public BattleServerProxy(Channel channel) {
        battleServerStub = new BattleServerStub(channel);
    }
}
