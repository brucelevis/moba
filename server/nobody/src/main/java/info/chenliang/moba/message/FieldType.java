package info.chenliang.moba.message;
public class FieldType{
public final static int ID=0;
public final static int POSITION_X=1;
public final static int POSITION_Z=2;
public final static int MAX_VALUE=3;
public static String toString(byte value){
if(value == ID){
return "ID";
}
if(value == POSITION_X){
return "POSITION_X";
}
if(value == POSITION_Z){
return "POSITION_Z";
}
return null;
}
public static int toValue(String name){
if(name.equals("ID")){
return ID;
}
if(name.equals("POSITION_X")){
return POSITION_X;
}
if(name.equals("POSITION_Z")){
return POSITION_Z;
}
return -1;
}
}
