package info.chenliang.moba.battle.arena.entity;

import info.chenliang.moba.battle.arena.component.Component;
import info.chenliang.moba.battle.arena.component.ComponentType;
import info.chenliang.moba.battle.arena.component.PlayerComponent;
import info.chenliang.moba.battle.arena.component.PositionComponent;
import info.chenliang.moba.battle.arena.sync.Synchronizer;
import info.chenliang.moba.battle.arena.system.MoveToPositionSystemListener;
import info.chenliang.moba.message.EntityState;
import info.chenliang.moba.message.EntityType;
import info.chenliang.moba.message.FieldType;
import info.chenliang.moba.message.SyncItem;

import java.util.*;

/**
 * Created by chenliang on 16/5/16.
 */
public class Entity implements MoveToPositionSystemListener{
    public final int id;
    public final int type;

    private short idleTime;

    public Entity(int id, int type) {
        this.id = id;
        this.type = type;
    }

    Map<ComponentType, Component> componentMap = new HashMap<>();
    Map<Class, Component> componentMap2 = new HashMap<>();

    public void addComponent(Component component){
        componentMap.put(component.getComponentType(), component);

        componentMap2.put(component.getClass(), component);
    }

    public Component getComponent(ComponentType componentType){
        return componentMap.get(componentType);
    }

    public void removeComponent(Component component){
        componentMap.remove(component.getComponentType());
    }

    @Override
    public void onMoveDone(Synchronizer synchronizer) {
        if (type == EntityType.PLAYER){
            PlayerComponent playerComponent = (PlayerComponent) getComponent(ComponentType.PLAYER);
            playerComponent.setState(EntityState.IDLE);
        }
    }

    public PositionComponent getPositionComponent() {
        return (PositionComponent) getComponent(ComponentType.POSITION);
    }

    public <T> T getComponent(Class<T> clazz) {
        return (T)
            componentMap2.get(clazz);
    }

    public SyncItem generateSyncItem() {
        int[] fields = new int[] {FieldType.POSITION_X, FieldType.POSITION_Z, FieldType.DIRECTION};
        float[] values = new float[] {getPositionComponent().getX(), getPositionComponent().getZ(), getPositionComponent().getDirection()};

        SyncItem syncItem = new SyncItem();

        syncItem.entityId = id;
        syncItem.fields = fields;
        syncItem.values = values;
        syncItem.idleTime = idleTime;

        return syncItem;
    }

    public void afterSync(){
        idleTime = 0;
    }

    public void setIdleTime(short idleTime) {
        this.idleTime = idleTime;
    }
}
