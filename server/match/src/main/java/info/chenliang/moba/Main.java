package info.chenliang.moba;

import info.chenliang.moba.match.MatchServer;

/**
 * Created by chenliang on 16/5/11.
 */
public class Main {
    public static void main(String[] args) throws Exception{
        MatchServer matchServer = new MatchServer();
        matchServer.start();
    }
}
