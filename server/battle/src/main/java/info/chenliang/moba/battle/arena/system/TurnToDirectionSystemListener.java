package info.chenliang.moba.battle.arena.system;

import info.chenliang.moba.battle.arena.sync.Synchronizer;

/**
 * Created by chenliang on 16/5/19.
 */
public interface TurnToDirectionSystemListener {
    void onTurnDone(Synchronizer synchronizer);
}
