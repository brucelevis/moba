package info.chenliang.moba.message;
public class EntityState{
public final static int IDLE=0;
public final static int MOVE=1;
public final static int DEAD=2;
public final static int ATTACK=3;
public final static int SKILL=4;
public final static int MAX_VALUE=5;
public static String toString(byte value){
if(value == IDLE){
return "IDLE";
}
if(value == MOVE){
return "MOVE";
}
if(value == DEAD){
return "DEAD";
}
if(value == ATTACK){
return "ATTACK";
}
if(value == SKILL){
return "SKILL";
}
return null;
}
public static int toValue(String name){
if(name.equals("IDLE")){
return IDLE;
}
if(name.equals("MOVE")){
return MOVE;
}
if(name.equals("DEAD")){
return DEAD;
}
if(name.equals("ATTACK")){
return ATTACK;
}
if(name.equals("SKILL")){
return SKILL;
}
return -1;
}
}
