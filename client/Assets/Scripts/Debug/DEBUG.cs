#define DEBUG_INFO

using System;

public class DEBUG
{
	public DEBUG ()
	{
	}



	public static void Info(string message) {
	#if DEBUG_INFO
		UnityEngine.Debug.Log(message);
	#endif
	}

}
