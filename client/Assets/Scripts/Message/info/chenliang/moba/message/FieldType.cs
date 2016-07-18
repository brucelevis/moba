namespace info.chenliang.moba.message{
public class FieldType{
public const int POSITION_X=0;
public const int POSITION_Z=1;
public const int DIRECTION=2;
public const int STATE=3;
public const int IDLE_TIME=4;
public const int MAX_VALUE=5;
public static string ToString(byte value){
if(value == POSITION_X){
return "POSITION_X";
}
if(value == POSITION_Z){
return "POSITION_Z";
}
if(value == DIRECTION){
return "DIRECTION";
}
if(value == STATE){
return "STATE";
}
if(value == IDLE_TIME){
return "IDLE_TIME";
}
return null;
}
public static int ToValue(string name){
if(name == "POSITION_X"){
return POSITION_X;
}
if(name == "POSITION_Z"){
return POSITION_Z;
}
if(name == "DIRECTION"){
return DIRECTION;
}
if(name == "STATE"){
return STATE;
}
if(name == "IDLE_TIME"){
return IDLE_TIME;
}
return -1;
}
}
}
