package info.chenliang.moba.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by chenliang on 16/5/11.
 */
public class MatchServerTaskExecutor {
    private MatchServerTaskExecutor(){

    }
    private static MatchServerTaskExecutor instance = new MatchServerTaskExecutor();
    public static MatchServerTaskExecutor getInstance(){
        return instance;
    }

    private Executor executor = Executors.newSingleThreadExecutor();

    public void init(){

    }

    public void execute(Runnable runnable){
        executor.execute(runnable);
    }
}
