using System;
using System.IO;
using MiscUtil;
using MiscUtil.IO;
using MiscUtil.Conversion;
using Talky;
namespace info.chenliang.moba.message{
public abstract class PlayerToBattleProxy {
public abstract void playerPing();
public abstract void playerPong();
public abstract void play();
public abstract void moveTo(Single x,Single z);
}
}
