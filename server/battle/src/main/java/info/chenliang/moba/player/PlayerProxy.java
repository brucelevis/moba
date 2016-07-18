package info.chenliang.moba.player;

import info.chenliang.moba.battle.arena.Arena;
import info.chenliang.moba.battle.arena.component.MoveToPositionComponent;
import info.chenliang.moba.battle.arena.component.PlayerComponent;
import info.chenliang.moba.battle.arena.component.PositionComponent;
import info.chenliang.moba.battle.arena.entity.Entity;
import info.chenliang.moba.message.PlayerToBattleProxy;
import info.chenliang.moba.message.Position2d;
import info.chenliang.moba.message.Sync;
import io.netty.channel.Channel;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chenliang on 16/5/17.
 */
public class PlayerProxy extends PlayerToBattleProxy {
    final PlayerStub playerStub;

    public PlayerStub getPlayerStub() {
        return playerStub;
    }

    private Arena arena;

    private final static Logger logger = LogManager.getLogger(PlayerProxy.class);

    private final static class RttStats {
        List<Integer> rtts = new LinkedList<>();

        void onRtt(int rtt) {
            rtts.add(rtt);
        }

        public int getRtt() {
            return 0;
        }
    }

    private RttStats rttStats = new RttStats();

    private long lastPing;
    @Override
    public void playerPing() {
        try {
            playerStub.battlePong();
        } catch (Exception e) {
            logger.error("PlayerProxy.ping", e);
        }
    }

    @Override
    public void playerPong() {
        // record rtt
        int rtt = (int)(System.currentTimeMillis() - lastPing);
        rttStats.onRtt(rtt);
    }

    @Override
    public void play() {
        logger.info("sending snapshot to player");
        Sync snapshot = arena.takeSnapshot();
        try {
            playerStub.onJoinArena(arena.getId(), snapshot, entity.id);
            logger.info("onJoinArea sent");
        } catch (Exception e) {
            logger.error("PlayerProxy.play", e);
        }
    }

    @Override
    public void moveTo(float x, float z) {
        PositionComponent positionComponent = entity.getPositionComponent();
        PlayerComponent playerComponent = entity.getComponent(PlayerComponent.class);

        float dx = x - positionComponent.getX();
        float dz = z - positionComponent.getZ();

        float distance = (float)Math.sqrt(dx*dx + dz*dz);

        Position2d direction = new Position2d();
        direction.x = dx/distance * playerComponent.getSpeed();
        direction.z = dz/distance * playerComponent.getSpeed();

        MoveToPositionComponent component = new MoveToPositionComponent(entity.id,  direction, distance / playerComponent.getSpeed());
        entity.addComponent(component);
    }

    public int getId() {
        return id;
    }

    public int getRtt() {
        return rttStats.getRtt();
    }

    private Entity entity;

    private final static AtomicInteger playerProxyIdGenerator = new AtomicInteger();
    private final int id;
    public PlayerProxy(Channel channel) {
        this.playerStub = new PlayerStub(channel);
        this.id = playerProxyIdGenerator.getAndIncrement();
    }

    public boolean isInArena(){
        return arena != null;
    }

    public synchronized void joinArena(Arena arena, Entity entity){
        this.arena = arena;
        this.entity = entity;
    }

    public synchronized Arena getArena() {
        return arena;
    }

    public synchronized void bindEntity(Entity entity){
        this.entity = entity;
    }
}
