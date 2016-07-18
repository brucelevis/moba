
using System.IO;
using System.Collections;

public class Message {
	private MemoryStream memoryStream;
	public Message(MemoryStream memoryStream)
	{
		this.memoryStream = memoryStream;
	}

	public MemoryStream MemoryStream{
		get {return memoryStream;}
	}
}
