package info.chenliang.moba.message;
import java.io.*;
import info.chenliang.talky.*;
public class Position3d{
public void serialize(DataOutputStream dos) throws Exception{
FieldMark fm = new FieldMark(0);
dos.write(fm.getData());
}
public void deserialize(DataInputStream dis) throws Exception{
FieldMark fm = new FieldMark(0);
dis.read(fm.getData());
}
}
