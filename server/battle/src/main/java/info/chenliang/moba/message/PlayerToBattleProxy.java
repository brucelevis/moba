package info.chenliang.moba.message;
import java.io.*;
import info.chenliang.talky.*;
public abstract class PlayerToBattleProxy {
public abstract void playerPing();
public abstract void playerPong();
public abstract void play();
public abstract void moveTo(float x,float z);
}
