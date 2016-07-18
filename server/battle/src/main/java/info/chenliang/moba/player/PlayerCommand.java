package info.chenliang.moba.player;

import java.io.DataInputStream;

/**
 * Created by chenliang on 16/5/17.
 */
public class PlayerCommand {
    private final PlayerProxy playerProxy;
    private final DataInputStream dataInputStream;
    private final int protocolId;

    public PlayerCommand(PlayerProxy playerProxy, DataInputStream dataInputStream, int protocolId) {
        this.playerProxy = playerProxy;
        this.dataInputStream = dataInputStream;
        this.protocolId = protocolId;
    }

    public PlayerProxy getPlayerProxy() {
        return playerProxy;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public int getProtocolId() {
        return protocolId;
    }
}
