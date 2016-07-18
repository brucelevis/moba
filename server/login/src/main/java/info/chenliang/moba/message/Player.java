package info.chenliang.moba.message;
import java.io.*;
import info.chenliang.talky.*;
public class Player{
public String name;
public byte id;
public void serialize(DataOutputStream dos) throws Exception{
FieldMark fm = new FieldMark(1);
fm.mark(name != null && name.length() > 0);
fm.mark(id != 0);
dos.write(fm.getData());
if(fm.isMarked(0)){
dos.writeUTF(name);
}
if(fm.isMarked(1)){
dos.writeByte(id);
}
}
public void deserialize(DataInputStream dis) throws Exception{
FieldMark fm = new FieldMark(1);
dis.read(fm.getData());
if(fm.readMark()){
name = dis.readUTF();
}
if(fm.readMark()){
id = dis.readByte();
}
}
}
