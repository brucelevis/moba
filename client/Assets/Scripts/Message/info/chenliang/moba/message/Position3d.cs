using System;
using System.IO;
using MiscUtil;
using MiscUtil.IO;
using MiscUtil.Conversion;
using Talky;
namespace info.chenliang.moba.message{
public class Position3d{
public void Serialize(MemoryEndianBinaryWriter writer){
FieldMark fm = new FieldMark(0);
writer.Write(fm.GetData());
}
public void Deserialize(MemoryEndianBinaryReader reader){
FieldMark fm = new FieldMark(0);
reader.Read(fm.GetData(), 0, fm.GetData().Length);
}
}
}
