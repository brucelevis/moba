using UnityEngine;
using System.Collections;
using System.IO;
using System.Net.Sockets;
using System.Collections.Generic;
using System;

using UnityEditor;

using MiscUtil;
using MiscUtil.IO;
using MiscUtil.Conversion;
using Talky;
using info.chenliang.moba.message;

public class NetworkManager {
	
	private NetworkStream stream;

	private MessageReader messageReader;
	private MessageWriter messageWriter;
	private BattleProxy proxy;

	public GameObject playerPrefab;

	IMessageListener listener;

	public NetworkManager(IMessageListener listener) {
		this.listener = listener;
	}

	class MessageReader {
		MemoryStream buf;

		MemoryEndianBinaryReader binaryReader;
		//	BattleProxy proxy;

		public MessageReader() {
			buf = new MemoryStream (64*1024);
			//		this.proxy = proxy;
			binaryReader = new MemoryEndianBinaryReader (EndianBitConverter.Big, buf);
		}

		public void read(Stream stream) {
			// read as mch as possible
			int bytesRead = stream.Read(buf.GetBuffer(), (int)buf.Length, (int)(buf.Capacity - buf.Length));

			//		buf.Write (buf.GetBuffer(), (int)buf.Length, (int)(bytesRead));
			//		DEBUG.Info("old length="+buf.Length+" bytesRead="+bytesRead+" new length=" + (buf.Length + bytesRead));


			buf.SetLength(buf.Length + bytesRead);

			//		if (bytesRead > 0) {
			//			DEBUG.Info ("read = " + bytesRead + " pos = " + buf.Position + " len=" + buf.Length + " remaining=" + (buf.Capacity - buf.Length));
			//		}

			buf.Position = 0;

			//		DEBUG.Info (BitConverter.ToString(buf.GetBuffer(), (int)buf.Position, (int)buf.Length));
//			bool messageRead = false;
			while(true) {
				if (buf.Length - buf.Position < 2) {
					//no message avilable now
					break;
				}

				UInt16 length = binaryReader.GetUInt16 ((int)buf.Position);
				if (buf.Length - buf.Position < length + 2) {
					break;	
				}

				// skip the length field
				UInt16 lengthRead = binaryReader.ReadUInt16 ();

				int pid = binaryReader.GetByte ((int)buf.Position);
				//			DEBUG.Info ("pid=" + pid + " length=" + lengthRead + " expected=" + length);
				MemoryStream tmpStream = new MemoryStream (length);
				buf.Read (tmpStream.GetBuffer(), 0, (int)(length));
				tmpStream.SetLength (length);

				//			MemoryEndianBinaryReader tmpReader = new MemoryEndianBinaryReader (EndianBitConverter.Big, tmpStream);

				if (listener != null) {
					Message message = new Message (tmpStream);
					listener.OnMessage (message);
				}

				//			BattleToPlayerDispatcher.Dispatch(tmpReader, proxy);

			}

			MemoryStream newBuf = new MemoryStream (64*1024);

			if (buf.Length - buf.Position > 0) {
				newBuf.Write (buf.GetBuffer(), (int)buf.Position, (int)(buf.Length - buf.Position));
			}

			buf = newBuf;
			binaryReader = new MemoryEndianBinaryReader (EndianBitConverter.Big, buf);	

		}
	}

	public class MessageWriter : PlayerToBattleStub{
		MemoryStream buf;
		MemoryEndianBinaryWriter binaryWriter;

		public EndianBinaryWriter BinaryWriter {
			get {return binaryWriter;}
		}

		public MessageWriter() {
			buf = new MemoryStream (64*1024);
			binaryWriter = new MemoryEndianBinaryWriter (EndianBitConverter.Big, buf);
		}

		public void write(Stream stream) {
			DEBUG.Info ("writing " + buf.Position + " " + buf.Length);
			stream.Write (buf.GetBuffer(), (int)buf.Position, (int)(buf.Length - buf.Position));
			stream.Flush ();

			buf.SetLength (0);
			buf.Position = 0;
		}

		public bool HasData() {
			return buf.Length > 0;
		}

		protected override MemoryEndianBinaryWriter Begin(){
			buf.SetLength (0);
			buf.Position = 0;
			binaryWriter.Write ((UInt16)0);
			return binaryWriter;
		}

		protected override void End() {
			binaryWriter.Seek (0, SeekOrigin.Begin);
			buf.Position = 0;

			UInt16 len = (UInt16)(buf.Length - buf.Position - 2);
			binaryWriter.Write (len);

			buf.Flush ();

			DEBUG.Info (BitConverter.ToString(buf.GetBuffer(), 0, (int)buf.Length));
			//		DEBUG.Info ("after end len=" + buf.Length + " p=" + buf.Position + " size=" + (len));

			buf.Position = 0;
		}
	}

	private void Init() {
		
		proxy = new BattleProxy ();

		TcpClient tcpClient = new TcpClient ("localhost", 6000);
		stream = tcpClient.GetStream ();

		DEBUG.Info ("connected:" + tcpClient.Connected);

		messageReader = new MessageReader (listener);
		messageWriter = new MessageWriter ();

//		PingMessage message = new PingMessage ();
//		message.write (messageWriter);

//		JoinMessage message = new JoinMessage ();
//		message.write (messageWriter);

		messageWriter.play ();
	}

	// Use this for initialization
	public void Start () {
//		DEBUG.Info ("before init");
		Init ();
//		DEBUG.Info ("after init");
	}

	// Update is called once per frame
	public void Update () {
		if (stream != null && stream.CanRead && stream.DataAvailable) {
//			DEBUG.Info ("before read");
			messageReader.read (stream);
//			DEBUG.Info ("after read");
		}

		if(stream != null && stream.CanWrite && messageWriter.HasData()) {
//			DEBUG.Info ("before write");
			messageWriter.write (stream);
//			DEBUG.Info ("after write");
		}
	}
}



