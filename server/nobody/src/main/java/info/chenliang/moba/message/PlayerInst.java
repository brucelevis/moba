package info.chenliang.moba.message;
import java.io.*;
import info.chenliang.talky.*;
public class PlayerInst{
public int id;
public float speed;
public void serialize(DataOutputStream dos) throws Exception{
FieldMark fm = new FieldMark(1);
fm.mark(id != 0);
fm.mark(speed != 0);
dos.write(fm.getData());
if(fm.isMarked(0)){
dos.writeInt(id);
}
if(fm.isMarked(1)){
dos.writeFloat(speed);
}
}
public void deserialize(DataInputStream dis) throws Exception{
FieldMark fm = new FieldMark(1);
dis.read(fm.getData());
if(fm.readMark()){
id = dis.readInt();
}
if(fm.readMark()){
speed = dis.readFloat();
}
}
}
