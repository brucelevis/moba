using System;
using System.IO;
using MiscUtil;
using MiscUtil.IO;
using MiscUtil.Conversion;
using Talky;
namespace info.chenliang.moba.message{
public class SyncItem{
public UInt32 entityId;
public UInt32 timestamp;
public UInt16 idleTime;
public UInt32[] fields;
public Single[] values;
public void Serialize(MemoryEndianBinaryWriter writer){
FieldMark fm = new FieldMark(1);
fm.Mark(entityId != 0);
fm.Mark(timestamp != 0);
fm.Mark(idleTime != 0);
fm.Mark(fields != null && fields.Length > 0);
fm.Mark(values != null && values.Length > 0);
writer.Write(fm.GetData());
if(fm.IsMarked(0)){
writer.WriteUInt32(entityId);
}
if(fm.IsMarked(1)){
writer.WriteUInt32(timestamp);
}
if(fm.IsMarked(2)){
writer.WriteUInt16(idleTime);
}
if(fm.IsMarked(3)){
SerializationUtil.WriteVariableLength(writer, fields.Length);
for(int i=0; i < fields.Length;i++){
writer.WriteUInt32(fields[i]);
}
}
if(fm.IsMarked(4)){
SerializationUtil.WriteVariableLength(writer, values.Length);
for(int i=0; i < values.Length;i++){
writer.WriteSingle(values[i]);
}
}
}
public void Deserialize(MemoryEndianBinaryReader reader){
FieldMark fm = new FieldMark(1);
reader.Read(fm.GetData(), 0, fm.GetData().Length);
if(fm.ReadMark()){
entityId = reader.ReadUInt32();
}
if(fm.ReadMark()){
timestamp = reader.ReadUInt32();
}
if(fm.ReadMark()){
idleTime = reader.ReadUInt16();
}
if(fm.ReadMark()){
int __1__ = SerializationUtil.ReadVariableLength(reader);
fields = new UInt32[__1__];
for(int i=0; i < __1__;i++){
fields[i] = reader.ReadUInt32();
}
}
if(fm.ReadMark()){
int __2__ = SerializationUtil.ReadVariableLength(reader);
values = new Single[__2__];
for(int i=0; i < __2__;i++){
values[i] = reader.ReadSingle();
}
}
}
}
}
