package info.chenliang.moba.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by chenliang on 16/5/10.
 */
public class TaskExecutor {
    private TaskExecutor(){

    }

    private static TaskExecutor instance = new TaskExecutor();
    public static TaskExecutor getInstance(){
        return instance;
    }

    private Executor executor = Executors.newSingleThreadExecutor();

    private Executor getExecutor() {
        return executor;
    }

    public void init(){

    }

    public void execute(Runnable runnable){
        executor.execute(runnable);
    }
}
