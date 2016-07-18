package info.chenliang.moba.battle.arena.component;

/**
 * Created by chenliang on 16/5/16.
 */
public abstract class Component {
    private final ComponentType componentType;

    protected final int entityId;

    public Component(int entityId, ComponentType componentType) {
        this.entityId = entityId;
        this.componentType = componentType;
    }

    public ComponentType getComponentType() {
        return componentType;
    }


}
