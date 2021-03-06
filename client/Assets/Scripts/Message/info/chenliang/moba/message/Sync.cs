using System;
using System.IO;
using MiscUtil;
using MiscUtil.IO;
using MiscUtil.Conversion;
using Talky;
namespace info.chenliang.moba.message{
public class Sync{
public UInt32 timestamp;
public SyncItem[] items;
public void Serialize(MemoryEndianBinaryWriter writer){
FieldMark fm = new FieldMark(1);
fm.Mark(timestamp != 0);
fm.Mark(items != null && items.Length > 0);
writer.Write(fm.GetData());
if(fm.IsMarked(0)){
writer.WriteUInt32(timestamp);
}
if(fm.IsMarked(1)){
SerializationUtil.WriteVariableLength(writer, items.Length);
for(int i=0; i < items.Length;i++){
items[i].Serialize(writer);
}
}
}
public void Deserialize(MemoryEndianBinaryReader reader){
FieldMark fm = new FieldMark(1);
reader.Read(fm.GetData(), 0, fm.GetData().Length);
if(fm.ReadMark()){
timestamp = reader.ReadUInt32();
}
if(fm.ReadMark()){
int __0__ = SerializationUtil.ReadVariableLength(reader);
items = new SyncItem[__0__];
for(int i=0; i < items.Length;i++){
items[i] = new SyncItem();
items[i].Deserialize(reader);
}
}
}
}
}
