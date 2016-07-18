package info.chenliang.moba.battle.arena;

import info.chenliang.moba.battle.arena.component.PlayerComponent;
import info.chenliang.moba.battle.arena.component.PositionComponent;
import info.chenliang.moba.battle.arena.component.PropertyComponent;
import info.chenliang.moba.battle.arena.entity.Entity;
import info.chenliang.moba.battle.arena.sync.Synchronizer;
import info.chenliang.moba.battle.arena.system.MoveToPositionSystem;
import info.chenliang.moba.battle.arena.system.System;
import info.chenliang.moba.battle.arena.system.TurnSystem;
import info.chenliang.moba.message.*;
import info.chenliang.moba.player.PlayerCommand;
import info.chenliang.moba.player.PlayerProxy;
import info.chenliang.moba.player.RobotPlayerProxy;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chenliang on 16/5/16.
 */
public final class Arena implements Runnable {
    List<Entity> entities = new LinkedList<>();
    MoveToPositionSystem moveToPositionSystem;
    TurnSystem turnSystem;
    List<System> systems = new LinkedList<>();

    List<PlayerCommand> playerCommands = new LinkedList<>();
    List<PlayerProxy> humanPlayerProxies = new ArrayList<>();
    List<PlayerProxy> robotPlayerProxies = new ArrayList<>();
    List<PlayerProxy> allPlayerProxies = new ArrayList<>();


    Synchronizer synchronizer = new Synchronizer();

    private final static Logger logger = LogManager.getLogger(Arena.class);
    private long lastTimestamp;
    private long gameStartTimestamp;
    private int frame;
    private final int id;

    private AtomicInteger atomicInteger = new AtomicInteger();

    public Arena(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        if (lastTimestamp == 0){
            lastTimestamp = java.lang.System.currentTimeMillis();
        }

        int deltaTime = (int)(java.lang.System.currentTimeMillis() - lastTimestamp);
        processPlayerCommands();
        for(System system : systems){
            system.run(deltaTime);
        }

        sync();

        lastTimestamp = java.lang.System.currentTimeMillis();
        frame ++;
    }

    private void sync(){
        Sync sync = synchronizer.generateSync(frame);

        for(PlayerProxy playerProxy : humanPlayerProxies){
            try {
                playerProxy.getPlayerStub().sync(sync);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void afterAllocated() {
        // create robot players when allocated
        RobotPlayerProxy robotPlayerProxy = new RobotPlayerProxy();

        robotPlayerProxies.add(robotPlayerProxy);
        allPlayerProxies.add(robotPlayerProxy);

        Entity entity = createEntity(nextEntityId(), EntityType.ROBOT);

        entity.addComponent(new PlayerComponent(entity.id));
        entity.addComponent(new PositionComponent(entity.id));
        entity.addComponent(new PropertyComponent(entity.id));

        robotPlayerProxy.bindEntity(entity);
    }

    private void processPlayerCommands(){
        synchronized (playerCommands){
            if(playerCommands.size() > 0) {
                logger.info("player commands " + playerCommands.size());
                for(PlayerCommand playerCommand : playerCommands){
                    try {
                        logger.info("process player command id=" + playerCommand.getProtocolId());
                        PlayerToBattleDispatcher.dispatch(playerCommand.getDataInputStream(), playerCommand.getPlayerProxy());
                    } catch (Exception e) {
                        logger.error("processPlayerCommands exception:", e);
                    }
                }
                playerCommands.clear();
            }
        }
    }

    synchronized void init(){
        moveToPositionSystem = new MoveToPositionSystem(synchronizer);
        turnSystem = new TurnSystem(synchronizer);

        systems.add(moveToPositionSystem);
        systems.add(turnSystem);
    }

    private void addEntity(Entity entity){
        for(System system : systems){
            system.onEntityCreated(entity);
        }
    }

    private Entity createEntity(int id, int type){
        Entity entity = new Entity(id, type);
        return entity;
    }

    public void addPlayerCommand(PlayerCommand playerCommand){
        synchronized (playerCommands){
            playerCommands.add(playerCommand);
        }
    }

    private void notifyPlayerSpawned(Entity entity) {
        SyncItem syncItem = entity.generateSyncItem();

        for (PlayerProxy proxy : humanPlayerProxies) {
            try {
                proxy.getPlayerStub().playerSpawned(entity.id, syncItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public synchronized Entity onPlayerJoin(PlayerProxy playerProxy){
        humanPlayerProxies.add(playerProxy);
        Entity entity = createEntity(nextEntityId(), EntityType.PLAYER);

        entity.addComponent(new PlayerComponent(entity.id));
        entity.addComponent(new PositionComponent(entity.id));
        entity.addComponent(new PropertyComponent(entity.id));

        notifyPlayerSpawned(entity);

        return entity;
    }

    private int nextEntityId(){
        return atomicInteger.getAndIncrement();
    }

    public synchronized boolean isFull() {
        return humanPlayerProxies.size() >= 20;
    }

    private int timestamp() {
        return (int)(java.lang.System.currentTimeMillis() - gameStartTimestamp);
    }

    public synchronized Sync takeSnapshot() {
        int timestamp = timestamp();

        Sync snapshot = new Sync();
        snapshot.timestamp = timestamp;

        snapshot.items = new SyncItem[entities.size()];
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            snapshot.items[i] = entity.generateSyncItem();
        }

        return snapshot;
    }
}
