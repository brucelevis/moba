namespace info.chenliang.moba.message{
using System;
using System.IO;
using MiscUtil;
using MiscUtil.IO;
using MiscUtil.Conversion;
using Talky;
public class PlayerToBattleDispatcher {
public static void Dispatch(MemoryEndianBinaryReader reader, PlayerToBattleProxy proxy) {
int fid = reader.ReadByte();
switch(fid)
{
case 0:
{
playerPing(reader, proxy);
}
break;
case 1:
{
playerPong(reader, proxy);
}
break;
case 2:
{
play(reader, proxy);
}
break;
case 3:
{
moveTo(reader, proxy);
}
break;
}
}
protected static void playerPing(MemoryEndianBinaryReader reader, PlayerToBattleProxy proxy){
proxy.playerPing();
}
protected static void playerPong(MemoryEndianBinaryReader reader, PlayerToBattleProxy proxy){
proxy.playerPong();
}
protected static void play(MemoryEndianBinaryReader reader, PlayerToBattleProxy proxy){
proxy.play();
}
protected static void moveTo(MemoryEndianBinaryReader reader, PlayerToBattleProxy proxy){
Single x;
Single z;
x = reader.ReadSingle();
z = reader.ReadSingle();
proxy.moveTo(x,z);
}
}
}
