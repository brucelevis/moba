package info.chenliang.moba.battle.arena.component;

import info.chenliang.moba.message.EntityState;

/**
 * Created by chenliang on 16/5/18.
 */
public class PlayerComponent extends Component {
    private float speed;
    private int state = EntityState.IDLE;
    private float x, z;
    // angle between player's facing direction and position x axis in degree
    private float direction;

    public PlayerComponent(int entityId) {
        super(entityId, ComponentType.PLAYER);
        speed = 0.1f;
    }

    public float getSpeed() {
        return speed;
    }

    public void setState(int state) {
        this.state = state;
    }
}
