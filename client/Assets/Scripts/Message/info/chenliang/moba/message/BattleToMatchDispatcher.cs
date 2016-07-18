namespace info.chenliang.moba.message{
using System;
using System.IO;
using MiscUtil;
using MiscUtil.IO;
using MiscUtil.Conversion;
using Talky;
public class BattleToMatchDispatcher {
public static void Dispatch(MemoryEndianBinaryReader reader, BattleToMatchProxy proxy) {
int fid = reader.ReadByte();
switch(fid)
{
}
}
}
}
