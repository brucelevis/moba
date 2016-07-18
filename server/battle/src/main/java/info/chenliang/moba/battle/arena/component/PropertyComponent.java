package info.chenliang.moba.battle.arena.component;

/**
 * Created by chenliang on 16/7/14.
 */
public class PropertyComponent extends Component {

    // angle (in degree) per millisecond
    private float turnSpeed;

    private float speed;

    public PropertyComponent(int entityId) {
        super(entityId, ComponentType.PROPERTY);
        turnSpeed = 0.36f; // 1 round in one second

        speed = 1;
    }

    public float getTurnSpeed() {
        return turnSpeed;
    }

    public float getSpeed() {
        return speed;
    }
}
