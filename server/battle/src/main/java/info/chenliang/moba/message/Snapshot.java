package info.chenliang.moba.message;
import info.chenliang.talky.FieldMark;
import info.chenliang.talky.SerializationUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
public class Snapshot{
public SyncItem[] items;
public void serialize(DataOutputStream dos) throws Exception{
FieldMark fm = new FieldMark(1);
fm.mark(items != null && items.length > 0);
dos.write(fm.getData());
if(fm.isMarked(0)){
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
int __0__ = SerializationUtil.readVariableLength(dis);
items = new SyncItem[__0__];
for(int i=0; i < items.length;i++){
items[i] = new SyncItem();
items[i].deserialize(dis);
}
}
}
}
