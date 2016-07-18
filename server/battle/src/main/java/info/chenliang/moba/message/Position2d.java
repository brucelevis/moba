package info.chenliang.moba.message;
import java.io.*;
import info.chenliang.talky.*;
public class Position2d{
public float x;
public float z;
public void serialize(DataOutputStream dos) throws Exception{
FieldMark fm = new FieldMark(1);
fm.mark(x != 0);
fm.mark(z != 0);
dos.write(fm.getData());
if(fm.isMarked(0)){
dos.writeFloat(x);
}
if(fm.isMarked(1)){
dos.writeFloat(z);
}
}
public void deserialize(DataInputStream dis) throws Exception{
FieldMark fm = new FieldMark(1);
dis.read(fm.getData());
if(fm.readMark()){
x = dis.readFloat();
}
if(fm.readMark()){
z = dis.readFloat();
}
}
}
