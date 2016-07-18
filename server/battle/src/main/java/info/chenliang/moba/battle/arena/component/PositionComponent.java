package info.chenliang.moba.battle.arena.component;

/**
 * Created by chenliang on 16/5/16.
 */
public class PositionComponent extends Component {
    private float x,z;
    private float direction;

    public PositionComponent(int entityId) {
        super(entityId, ComponentType.POSITION);
    }

    public void setPosition(float x, float z){
        this.x = x;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }
}
