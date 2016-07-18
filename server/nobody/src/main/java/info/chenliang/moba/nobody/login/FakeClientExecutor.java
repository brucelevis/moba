package info.chenliang.moba.nobody.login;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenliang on 16/5/11.
 */
public class FakeClientExecutor {
    private Executor executor = Executors.newSingleThreadExecutor();
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public static FakeClientExecutor instance = new FakeClientExecutor();
    public synchronized static FakeClientExecutor getInstance(){
        return instance;
    }

    public void execute(Runnable runnable){
        executor.execute(runnable);
    }

    public void schedule(Runnable runnable, long delay, TimeUnit timeUnit){
        scheduledExecutorService.scheduleAtFixedRate(runnable,0, delay, timeUnit);
    }
}
