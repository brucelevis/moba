package info.chenliang.moba.battle.arena.component;

import info.chenliang.moba.message.Position2d;

/**
 * Move to the specified position in specified time period
 *
 * Created by chenliang on 16/5/16.
 */
public class MoveToPositionComponent extends Component {
    private final Position2d target;

    public MoveToPositionComponent(int entityId, Position2d target) {
        super(entityId, ComponentType.MOVE_TO_POSITION);
        this.target = target;
    }


    public Position2d getTarget() {
        return target;
    }
}
