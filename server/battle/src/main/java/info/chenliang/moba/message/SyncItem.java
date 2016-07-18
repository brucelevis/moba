package info.chenliang.moba.message;
import java.io.*;
import info.chenliang.talky.*;
public class SyncItem{
public int entityId;
public int timestamp;
public short idleTime;
public int[] fields;
public float[] values;
public void serialize(DataOutputStream dos) throws Exception{
FieldMark fm = new FieldMark(1);
fm.mark(entityId != 0);
fm.mark(timestamp != 0);
fm.mark(idleTime != 0);
fm.mark(fields != null && fields.length > 0);
fm.mark(values != null && values.length > 0);
dos.write(fm.getData());
if(fm.isMarked(0)){
dos.writeInt(entityId);
}
if(fm.isMarked(1)){
dos.writeInt(timestamp);
}
if(fm.isMarked(2)){
dos.writeShort(idleTime);
}
if(fm.isMarked(3)){
SerializationUtil.writeVariableLength(dos, fields.length);
for(int i=0; i < fields.length;i++){
dos.writeInt(fields[i]);
}
}
if(fm.isMarked(4)){
SerializationUtil.writeVariableLength(dos, values.length);
for(int i=0; i < values.length;i++){
dos.writeFloat(values[i]);
}
}
}
public void deserialize(DataInputStream dis) throws Exception{
FieldMark fm = new FieldMark(1);
dis.read(fm.getData());
if(fm.readMark()){
entityId = dis.readInt();
}
if(fm.readMark()){
timestamp = dis.readInt();
}
if(fm.readMark()){
idleTime = dis.readShort();
}
if(fm.readMark()){
int __1__ = SerializationUtil.readVariableLength(dis);
fields = new int[__1__];
for(int i=0; i < __1__;i++){
fields[i] = dis.readInt();
}
}
if(fm.readMark()){
int __2__ = SerializationUtil.readVariableLength(dis);
values = new float[__2__];
for(int i=0; i < __2__;i++){
values[i] = dis.readFloat();
}
}
}
}
