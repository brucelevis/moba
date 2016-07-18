package info.chenliang.moba.login;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenliang on 16/5/11.
 */
public class LoginServerManager {
    private LoginServerManager(){}
    private static LoginServerManager instance = new LoginServerManager();

    public static LoginServerManager getInstance(){
        return instance;
    }

    private Map<Integer, LoginServer> loginServerMap = new HashMap<>();

    public void addLoginServer(LoginServer loginServer){
        loginServerMap.put(loginServer.getId(), loginServer);
    }

    public LoginServer getLoginServerById(int id){
        return loginServerMap.get(id);
    }
}
