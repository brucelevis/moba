package info.chenliang.moba.message;
public class FieldType{
public final static int POSITION_X=0;
public final static int POSITION_Z=1;
public final static int DIRECTION=2;
public final static int STATE=3;
public final static int IDLE_TIME=4;
public final static int MAX_VALUE=5;
public static String toString(byte value){
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
public static int toValue(String name){
if(name.equals("POSITION_X")){
return POSITION_X;
}
if(name.equals("POSITION_Z")){
return POSITION_Z;
}
if(name.equals("DIRECTION")){
return DIRECTION;
}
if(name.equals("STATE")){
return STATE;
}
if(name.equals("IDLE_TIME")){
return IDLE_TIME;
}
return -1;
}
}
