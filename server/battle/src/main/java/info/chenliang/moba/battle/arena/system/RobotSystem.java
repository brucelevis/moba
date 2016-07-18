package info.chenliang.moba.battle.arena.system;

import info.chenliang.moba.battle.arena.component.MoveToPositionComponent;
import info.chenliang.moba.battle.arena.component.PositionComponent;
import info.chenliang.moba.battle.arena.component.PropertyComponent;
import info.chenliang.moba.battle.arena.entity.Entity;
import info.chenliang.moba.battle.arena.sync.Synchronizer;
import info.chenliang.moba.message.EntityType;
import info.chenliang.moba.message.Position2d;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by chenliang on 16/5/16.
 */
public class RobotSystem extends System{

    private final static Logger logger = LogManager.getLogger(RobotSystem.class);

    public RobotSystem(Synchronizer synchronizer) {
        super(synchronizer);
    }

    @Override
    public void run(int deltaTime) {
        for(Entity entity : entities){
            if (entity.type == EntityType.ROBOT) {
                PropertyComponent propertyComponent = entity.getComponent(PropertyComponent.class);
                if (propertyComponent == null)
                    continue;

                PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
                if (positionComponent == null)
                    continue;

                MoveToPositionComponent moveToPositionComponent = entity.getComponent(MoveToPositionComponent.class);
                if (moveToPositionComponent != null)
                    continue;

                Position2d newPosition = new Position2d();
                newPosition.x = (float) (positionComponent.getX() + Math.random() * 10 * (Math.random() > 0.5 ? 1 : -1));
                newPosition.z = (float) (positionComponent.getZ() + Math.random() * 10 * (Math.random() > 0.5 ? 1 : -1));


                moveToPositionComponent = new MoveToPositionComponent(entity.id, newPosition);
                entity.addComponent(moveToPositionComponent);
            }
        }

    }
}
