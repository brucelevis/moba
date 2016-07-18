package info.chenliang.moba.battle.arena.system;

import info.chenliang.moba.battle.arena.entity.Entity;
import info.chenliang.moba.battle.arena.entity.EntityLifeCycleListener;
import info.chenliang.moba.battle.arena.sync.Synchronizer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by chenliang on 16/5/16.
 */
public abstract class System implements EntityLifeCycleListener {
    protected List<Entity> entities = new LinkedList<>();

    protected final Synchronizer synchronizer;

    public System(Synchronizer synchronizer) {
        this.synchronizer = synchronizer;
    }

    public void run(int deltaTime) {

    }

    @Override
    public void onEntityDestroyed(Entity entity) {

    }

    @Override
    public void onEntityCreated(Entity entity) {

    }
}
