package info.chenliang.moba.battle.arena.system;

import info.chenliang.moba.battle.arena.component.*;
import info.chenliang.moba.battle.arena.entity.Entity;
import info.chenliang.moba.battle.arena.sync.Synchronizer;
import info.chenliang.moba.message.FieldType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by chenliang on 16/5/16.
 */
public class TurnSystem extends System{

    private final static Logger logger = LogManager.getLogger(TurnSystem.class);

    public TurnSystem(Synchronizer synchronizer) {
        super(synchronizer);
    }

    @Override
    public void run(int deltaTime) {
        for(Entity entity : entities){
            TurnToDirectionComponent turnToDirectionComponent = entity.getComponent(TurnToDirectionComponent.class);

            if (turnToDirectionComponent == null)
                continue;

            PropertyComponent propertyComponent = entity.getComponent(PropertyComponent.class);
            if (propertyComponent == null)
                continue;

            PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
            if (positionComponent == null)
                continue;

            float targetDirection = turnToDirectionComponent.getDirection();
            float currentDirection = positionComponent.getDirection();

            float diff = targetDirection - currentDirection;

            if (diff > 180){
                diff = 360 - diff;
            }else if(diff < -180) {
                diff = 360 + diff;
            }

            boolean done = false;
            float deltaAngle = deltaTime * propertyComponent.getTurnSpeed();
            if (deltaAngle >= diff){
                positionComponent.setDirection(turnToDirectionComponent.getDirection());

                done = true;
            }else{
                float o = targetDirection - currentDirection;
                if (o == diff){
                    if (o < 0) {
                        deltaAngle *= -1;
                    }else if(o > 0) {
                        deltaAngle *= +1;
                    }
                }else{
                    if (o < 0) {
                        deltaAngle *= +1;
                    }else if(o > 0) {
                        deltaAngle *= -1;
                    }
                }

                float updatedAngle = currentDirection + deltaAngle;
                if (updatedAngle > 360) updatedAngle -= 360;
                if (updatedAngle < -360) updatedAngle += 360;
                positionComponent.setDirection(updatedAngle);
            }

            synchronizer.sync(entity, FieldType.DIRECTION, positionComponent.getDirection());

            if (done){
                logger.info("player turned to target");

                entity.removeComponent(turnToDirectionComponent);
            }
        }

    }
}
