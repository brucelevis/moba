namespace info.chenliang.moba.message{
public class FieldType{
public const int POSITION_X=0;
public const int POSITION_Z=1;
public const int DIRECTION=2;
public const int MAX_VALUE=3;
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
return -1;
}
}
}
