package info.chenliang.moba.battle.arena.component;

/**
 * Move to the specified position in specified time period
 *
 * Created by chenliang on 16/5/16.
 */
public class TurnToDirectionComponent extends Component {
    private float direction;
    private boolean done;

    /**
     *
     * @param entityId
     * @param direction angle between forward direction and world positive x
     */
    public TurnToDirectionComponent(int entityId, float direction) {
        super(entityId, ComponentType.TURN_TO_DIRECTION);
        this.direction = direction;
    }

    public float getDirection() {
        return direction;
    }

    public boolean isDone() {
        return done;
    }
}
