package info.chenliang.moba.message;
import java.io.*;
import info.chenliang.talky.*;
public class BattleToMatchDispatcher {
public static void dispatch(DataInputStream dis, BattleToMatchProxy proxy) throws Exception{
int fid = (int)(dis.read()&0xFFFF);
switch(fid)
{
}
}
}
