using System;
using System.IO;
using MiscUtil;
using MiscUtil.IO;
using MiscUtil.Conversion;
using Talky;
namespace info.chenliang.moba.message{
public class Position2d{
public Single x;
public Single z;
public void Serialize(MemoryEndianBinaryWriter writer){
FieldMark fm = new FieldMark(1);
fm.Mark(x != 0);
fm.Mark(z != 0);
writer.Write(fm.GetData());
if(fm.IsMarked(0)){
writer.WriteSingle(x);
}
if(fm.IsMarked(1)){
writer.WriteSingle(z);
}
}
public void Deserialize(MemoryEndianBinaryReader reader){
FieldMark fm = new FieldMark(1);
reader.Read(fm.GetData(), 0, fm.GetData().Length);
if(fm.ReadMark()){
x = reader.ReadSingle();
}
if(fm.ReadMark()){
z = reader.ReadSingle();
}
}
}
}
