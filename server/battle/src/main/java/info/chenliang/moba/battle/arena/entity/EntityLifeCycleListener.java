package info.chenliang.moba.battle.arena.entity;

/**
 * Created by chenliang on 16/5/16.
 */
public interface EntityLifeCycleListener {
    void onEntityCreated(Entity entity);
    void onEntityDestroyed(Entity entity);
}
