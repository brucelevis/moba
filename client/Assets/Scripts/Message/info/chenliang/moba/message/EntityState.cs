namespace info.chenliang.moba.message{
public class EntityState{
public const int IDLE=0;
public const int MOVE=1;
public const int DEAD=2;
public const int ATTACK=3;
public const int SKILL=4;
public const int MAX_VALUE=5;
public static string ToString(byte value){
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
public static int ToValue(string name){
if(name == "IDLE"){
return IDLE;
}
if(name == "MOVE"){
return MOVE;
}
if(name == "DEAD"){
return DEAD;
}
if(name == "ATTACK"){
return ATTACK;
}
if(name == "SKILL"){
return SKILL;
}
return -1;
}
}
}
