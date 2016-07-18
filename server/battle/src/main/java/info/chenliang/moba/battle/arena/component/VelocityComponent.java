package info.chenliang.moba.battle.arena.component;

/**
 * Created by chenliang on 16/5/16.
 */
public class VelocityComponent extends Component {
    private final float speedX, speedZ;

    public VelocityComponent(int entityId, float speedX, float speedZ) {
        super(entityId, ComponentType.VELOCITY);
        this.speedX = speedX;
        this.speedZ = speedZ;
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedZ() {
        return speedZ;
    }
}
