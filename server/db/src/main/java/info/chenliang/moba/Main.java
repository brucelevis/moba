package info.chenliang.moba;

import info.chenliang.moba.db.DbServer;

/**
 * Created by chenliang on 16/5/11.
 */
public class Main {
    public static void main(String[] args) throws Exception{
        DbServer dbServer = new DbServer(3000);
        dbServer.start();
    }
}
