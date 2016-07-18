namespace info.chenliang.moba.message{
using System;
using System.IO;
using MiscUtil;
using MiscUtil.IO;
using MiscUtil.Conversion;
using Talky;
public abstract class MatchToBattleStub {
protected abstract MemoryEndianBinaryWriter Begin();
protected abstract void End();
}
}
