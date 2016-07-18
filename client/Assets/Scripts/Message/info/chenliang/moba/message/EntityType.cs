namespace info.chenliang.moba.message{
public class EntityType{
public const int PLAYER=0;
public const int MINION=1;
public const int ROBOT=2;
public const int MAX_VALUE=3;
public static string ToString(byte value){
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
public static int ToValue(string name){
if(name == "PLAYER"){
return PLAYER;
}
if(name == "MINION"){
return MINION;
}
if(name == "ROBOT"){
return ROBOT;
}
return -1;
}
}
}
