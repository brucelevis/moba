namespace info.chenliang.moba.message{
using System;
using System.IO;
using MiscUtil;
using MiscUtil.IO;
using MiscUtil.Conversion;
using Talky;
public abstract class PlayerToBattleStub {
protected abstract MemoryEndianBinaryWriter Begin();
protected abstract void End();
public void playerPing(){
MemoryEndianBinaryWriter writer = Begin();
writer.WriteByte(0);
End();
}
public void playerPong(){
MemoryEndianBinaryWriter writer = Begin();
writer.WriteByte(1);
End();
}
public void play(){
MemoryEndianBinaryWriter writer = Begin();
writer.WriteByte(2);
End();
}
public void moveTo(Single x,Single z){
MemoryEndianBinaryWriter writer = Begin();
writer.WriteByte(3);
writer.WriteSingle(x);
writer.WriteSingle(z);
End();
}
}
}
