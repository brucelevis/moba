package info.chenliang.moba.message;
public class FieldType{
public final static int POSITION_X=0;
public final static int POSITION_Z=1;
public final static int DIRECTION=2;
public final static int MAX_VALUE=3;
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
return -1;
}
}
