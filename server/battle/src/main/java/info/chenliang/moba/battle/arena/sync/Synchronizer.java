package info.chenliang.moba.battle.arena.sync;

import info.chenliang.moba.battle.arena.entity.Entity;
import info.chenliang.moba.message.Sync;
import info.chenliang.moba.message.SyncItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenliang on 16/5/18.
 */
public class Synchronizer {
    private static class SynchronizeItem{
//        private final int entityId;
        private final Entity entity;
        Map<Integer, Float> fields = new HashMap<>();

//        public SynchronizeItem(int entityId) {
//            this.entityId = entityId;
//        }


        public SynchronizeItem(Entity entity) {
            this.entity = entity;
        }

        public void set(int field, float value){
            fields.put(field, value);
        }
    }

    Map<Integer, SynchronizeItem> synchronizeItemMap = new HashMap<>();

//    public void sync(int entityId, int field, float value){
//        SynchronizeItem synchronizeItem = synchronizeItemMap.get(entityId);
//        if (synchronizeItem == null) {
//            synchronizeItem  = new SynchronizeItem(entityId);
//            synchronizeItemMap.put(entityId, synchronizeItem);
//        }
//
//        synchronizeItem.set(field, value);
//    }

    public void sync(Entity entity, int field, float value){
        SynchronizeItem synchronizeItem = synchronizeItemMap.get(entity.id);
        if (synchronizeItem == null) {
            synchronizeItem  = new SynchronizeItem(entity);
            synchronizeItemMap.put(entity.id, synchronizeItem);
        }

        synchronizeItem.set(field, value);
    }

    public Sync generateSync(int timestamp){
        Sync sync = new Sync();
        sync.timestamp = timestamp;
        sync.items = new SyncItem[synchronizeItemMap.size()];

        SynchronizeItem[] synchronizeItems = synchronizeItemMap.values().toArray(new SynchronizeItem[0]);
        for (int i = 0; i < sync.items.length; i++) {
            // for each entity
            SyncItem syncItem = new SyncItem();
            SynchronizeItem synchronizeItem = synchronizeItems[i];
            syncItem.entityId = synchronizeItem.entity.id;

            Integer[] fieldIds = synchronizeItem.fields.keySet().toArray(new Integer[0]);
            syncItem.fields = new int[fieldIds.length];
            syncItem.values = new float[fieldIds.length];
            for (int j = 0; j < synchronizeItem.fields.size(); j++) {
                // for each field
                syncItem.fields[j] = fieldIds[j];
                syncItem.values[j] = synchronizeItem.fields.get(fieldIds[j]);
            }

            synchronizeItem.entity.afterSync();
        }
        return sync;
    }

    public void reset(){
        synchronizeItemMap.clear();
    }
}
