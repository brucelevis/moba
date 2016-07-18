package info.chenliang.moba.battle.arena;

import info.chenliang.moba.player.PlayerProxy;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by chenliang on 16/5/17.
 */
public final class ArenaManager {
    private static final ArenaManager instance = new ArenaManager();
    private ArenaManager(){}

    private ConcurrentHashMap<Integer, Arena> arenaConcurrentHashMap = new ConcurrentHashMap<>();
    public static ArenaManager getInstance(){
        return instance;
    }
    private List<Arena> freeArenas = new LinkedList<>();

    private final static Logger logger = LogManager.getLogger(ArenaManager.class);

    private final ScheduledExecutorService[] workers = new ScheduledExecutorService[Runtime.getRuntime().availableProcessors()];

    private final static int ARENA_COUNT = 10;

    private final static int ARENA_TICK_INTERVAL = 50;

    private final Map<Integer, ArenaAllocationRecord> arenaAllocationRecordMap = new ConcurrentHashMap<>();


    public synchronized void init() {
        initWorkers();
        initArenas();
    }

    private void initWorkers() {
        for (int i = 0; i < workers.length; i++) {
            ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            workers[i] = scheduledExecutorService;
        }
    }

    private void initArenas(){
        for (int i=0; i < ARENA_COUNT; i++){
            Arena arena = new Arena(i);
            arena.init();

            freeArenas.add(arena);
        }
    }

    private static class ArenaAllocationRecord {
        private final Arena arena;
        private final ScheduledFuture scheduledFuture;

        public ArenaAllocationRecord(Arena arena, ScheduledFuture scheduledFuture) {
            this.arena = arena;
            this.scheduledFuture = scheduledFuture;
        }

        public Arena getArena() {
            return arena;
        }

        public ScheduledFuture getScheduledFuture() {
            return scheduledFuture;
        }
    }

    public Arena getArena(int arenaId){
        return arenaConcurrentHashMap.get(arenaId);
    }

    private int getWorkerIndex(final int arenaId) {
        return arenaId % workers.length;
    }

    private ScheduledExecutorService getWorker(final Arena arena) {
        int workerId = getWorkerIndex(arena.getId());
        return workers[workerId];
    }

    synchronized Arena allocateArena() {
        Arena arena = freeArenas.remove(0);
        arena.afterAllocated();

        ScheduledExecutorService scheduledExecutorService = getWorker(arena);
        ScheduledFuture scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(arena, 0, ARENA_TICK_INTERVAL, TimeUnit.MILLISECONDS);

        ArenaAllocationRecord arenaAllocationRecord = new ArenaAllocationRecord(arena, scheduledFuture);
        arenaAllocationRecordMap.put(arenaAllocationRecord.getArena().getId(), arenaAllocationRecord);

        logger.info(String.format("arena allocated id=%s", arena.getId()));
        return arena;
    }



    synchronized void deallocateArena(final Arena arena) {
        ArenaAllocationRecord arenaAllocationRecord = arenaAllocationRecordMap.get(arena.getId());
        if(arenaAllocationRecord == null) {
            logger.fatal(String.format("deallocateArena no allocation record found for arena id=%d", arena.getId()));
            return;
        }

        if (freeArenas.contains(arena)) {
            logger.warn(String.format("closeArena arena already in free arenas id=%d", arena.getId()));
        } else {
            freeArenas.add(arena);
        }

        // FIXME: 16/5/22 this kind of resource management is too casual
        boolean cancelled = arenaAllocationRecord.getScheduledFuture().cancel(false);
        if(!cancelled) {
            logger.fatal(String.format("unschedule arena failed id=%d", arena.getId()));
        }
    }

    public synchronized Arena onPlayerPlay(PlayerProxy playerProxy) {
        Arena arena = null;

        for(ArenaAllocationRecord record : arenaAllocationRecordMap.values()) {
            if(!record.arena.isFull()) {
                arena = record.arena;
                break;
            }
        }

        if(arena == null) {
            arena = allocateArena();
        }

        return arena;
    }
}
