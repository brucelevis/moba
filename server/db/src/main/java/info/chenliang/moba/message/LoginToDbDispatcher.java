package info.chenliang.moba.message;
import java.io.*;
import info.chenliang.talky.*;
public class LoginToDbDispatcher {
public static void dispatch(DataInputStream dis, LoginToDbProxy proxy) throws Exception{
int fid = (int)(dis.read()&0xFFFF);
switch(fid)
{
}
}
}
