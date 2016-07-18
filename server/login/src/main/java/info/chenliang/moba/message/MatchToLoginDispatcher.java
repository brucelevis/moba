package info.chenliang.moba.message;
import java.io.*;
import info.chenliang.talky.*;
public class MatchToLoginDispatcher {
public static void dispatch(DataInputStream dis, MatchToLoginProxy proxy) throws Exception{
int fid = (int)(dis.read()&0xFFFF);
switch(fid)
{
}
}
}
