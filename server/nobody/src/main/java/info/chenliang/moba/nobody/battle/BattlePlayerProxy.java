package info.chenliang.moba.nobody.battle;

import info.chenliang.moba.executor.TaskExecutor;
import info.chenliang.moba.message.BattleToPlayerProxy;
import info.chenliang.moba.message.Snapshot;
import info.chenliang.moba.message.Sync;
import io.netty.channel.Channel;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by chenliang on 16/5/11.
 */
public class BattlePlayerProxy extends BattleToPlayerProxy {
    private final Channel channel;
    private final PlayerToBattleStub playerToBattleStub;
    private final static Logger logger = LogManager.getLogger(BattlePlayerProxy.class);

    public BattlePlayerProxy(Channel channel) {
        this.channel = channel;
        this.playerToBattleStub = new PlayerToBattleStub(channel);
    }

    public synchronized PlayerToBattleStub getPlayerToBattleStub() {
        return playerToBattleStub;
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    public void battlePing() {

    }

    @Override
    public void battlePong() {
        logger.info("battle pong");
    }

    @Override
    public void onJoinArena(int arenaId, Snapshot snapshot) {
        logger.info("onJoinArena " + arenaId);

        TaskExecutor.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                // ping
                try {
                    logger.info("ping battle ");
                    playerToBattleStub.playerPing();
                } catch (Exception e) {
                    logger.error("playerPing exception:", e);
                }
            }
        }, 3, TimeUnit.SECONDS);

        TaskExecutor.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    float x = (float)(Math.random() * 10000);
                    float z = (float)(Math.random() * 10000);

                    logger.info(String.format("moving to x=%.3f z=%.3f", x, z));
                    playerToBattleStub.moveToPosition(x,z);
                } catch (Exception e) {
                    logger.error("moveToPosition exception:", e);
                }
            }
        }, 3, TimeUnit.SECONDS);
    }

    @Override
    public void sync(Sync sync) {
        logger.info("receiving sync ");
    }
}
