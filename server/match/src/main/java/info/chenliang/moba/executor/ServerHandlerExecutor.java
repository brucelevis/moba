package info.chenliang.moba.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by chenliang on 16/5/11.
 */
public class ServerHandlerExecutor {
    private ServerHandlerExecutor(){

    }
    private static ServerHandlerExecutor instance = new ServerHandlerExecutor();
    public static ServerHandlerExecutor getInstance(){
        return instance;
    }

    private Executor executor = Executors.newSingleThreadExecutor();

    public void init(){

    }

    public void execute(Runnable runnable){
        executor.execute(runnable);
    }
}
