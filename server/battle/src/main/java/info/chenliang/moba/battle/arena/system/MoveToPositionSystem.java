package info.chenliang.moba.battle.arena.system;

import info.chenliang.moba.battle.arena.component.MoveToPositionComponent;
import info.chenliang.moba.battle.arena.component.PositionComponent;
import info.chenliang.moba.battle.arena.component.PropertyComponent;
import info.chenliang.moba.battle.arena.entity.Entity;
import info.chenliang.moba.battle.arena.sync.Synchronizer;
import info.chenliang.moba.message.FieldType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by chenliang on 16/5/16.
 */
public class MoveToPositionSystem extends System{

    private final static Logger logger = LogManager.getLogger(MoveToPositionSystem.class);

    public MoveToPositionSystem(Synchronizer synchronizer) {
        super(synchronizer);
    }

    @Override
    public void run(int deltaTime) {
        for(Entity entity : entities){
            MoveToPositionComponent moveToPositionComponent = entity.getComponent(MoveToPositionComponent.class);
            if (moveToPositionComponent == null)
                continue;

            PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
            if (positionComponent == null)
                continue;

            PropertyComponent propertyComponent = entity.getComponent(PropertyComponent.class);
            if (propertyComponent == null)
                continue;

            float dx = moveToPositionComponent.getTarget().x - positionComponent.getX();
            float dz = moveToPositionComponent.getTarget().z - positionComponent.getZ();

            float lengthLeft = (float)Math.sqrt(dx*dx + dz*dz);

            float moveLength = propertyComponent.getSpeed() * deltaTime;
            boolean done = false;

            dx /= lengthLeft;
            dz /= lengthLeft;

            // position
            float x = positionComponent.getX();
            float z = positionComponent.getZ();

            if (moveLength >= lengthLeft) {
                x = moveToPositionComponent.getTarget().x;
                z = moveToPositionComponent.getTarget().z;
                entity.setIdleTime((short) (deltaTime * (1 - lengthLeft/moveLength)));
                done = true;
            }else{
                x += dx * moveLength;
                z += dz * moveLength;
            }

            logger.info(String.format("player %d move to x=%.3f z=%.3f", entity.id, x, z));

            positionComponent.setPosition(x, z);

            synchronizer.sync(entity, FieldType.POSITION_X, x);
            synchronizer.sync(entity, FieldType.POSITION_Z, z);

            if (done){
                logger.info("player moved to target");

                // notify the entity move is done
                entity.onMoveDone(synchronizer);

                // remove the component used to move to position
                entity.removeComponent(moveToPositionComponent);
            }
        }

    }
}
