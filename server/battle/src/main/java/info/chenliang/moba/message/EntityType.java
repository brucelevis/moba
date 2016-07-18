package info.chenliang.moba.message;
public class EntityType{
public final static int PLAYER=0;
public final static int MINION=1;
public final static int ROBOT=2;
public final static int MAX_VALUE=3;
public static String toString(byte value){
if(value == PLAYER){
return "PLAYER";
}
if(value == MINION){
return "MINION";
}
if(value == ROBOT){
return "ROBOT";
}
return null;
}
public static int toValue(String name){
if(name.equals("PLAYER")){
return PLAYER;
}
if(name.equals("MINION")){
return MINION;
}
if(name.equals("ROBOT")){
return ROBOT;
}
return -1;
}
}
