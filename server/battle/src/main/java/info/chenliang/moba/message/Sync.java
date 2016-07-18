package info.chenliang.moba.message;
import java.io.*;
import info.chenliang.talky.*;
public class Sync{
public int timestamp;
public SyncItem[] items;
public void serialize(DataOutputStream dos) throws Exception{
FieldMark fm = new FieldMark(1);
fm.mark(timestamp != 0);
fm.mark(items != null && items.length > 0);
dos.write(fm.getData());
if(fm.isMarked(0)){
dos.writeInt(timestamp);
}
if(fm.isMarked(1)){
SerializationUtil.writeVariableLength(dos, items.length);
for(int i=0; i < items.length;i++){
items[i].serialize(dos);
}
}
}
public void deserialize(DataInputStream dis) throws Exception{
FieldMark fm = new FieldMark(1);
dis.read(fm.getData());
if(fm.readMark()){
timestamp = dis.readInt();
}
if(fm.readMark()){
int __0__ = SerializationUtil.readVariableLength(dis);
items = new SyncItem[__0__];
for(int i=0; i < items.length;i++){
items[i] = new SyncItem();
items[i].deserialize(dis);
}
}
}
}
