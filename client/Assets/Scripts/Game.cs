using UnityEngine;
using System.Collections;
using System.Collections.Generic;

using System;
using info.chenliang.moba.message;

using MiscUtil;
using MiscUtil.IO;
using MiscUtil.Conversion;
using Talky;

public class Game : IMessageListener {
	NetworkManager networkManager;
	List<Message> messages;
	List<Sync> syncs;
	Dictionary<UInt32, Character> characters;

	UInt32 timestamp;

	UInt32 targetTimestamp;
	BattleProxy battleProxy;
	bool inSyncWithServer;

	private void Init() {
		messages = new List<Message> ();
		characters = new Dictionary<UInt32, Character> ();

		networkManager = new NetworkManager (this);
		networkManager.Start ();

		battleProxy = new BattleProxy (this);
	}

	public UInt32 Timestamp{
		get{ return timestamp;}
	}

	// Use this for initialization
	public void Start () {
		Init ();


	}

	public void OnMessage(Message message) {
		messages.Add (message);
	}


	private Character GetCharacter(UInt32 id) {
		Character c;
		if (characters.TryGetValue (id, out c)) {
			return c;
		} else {
			return null;
		}

	}

	private void DispatchMessages() {
		if (messages.Count > 0) {
			for (int i = 0; i < messages.Count; i++) {
				Message message = messages[i];
				BattleToPlayerDispatcher.Dispatch (new MemoryEndianBinaryReader (EndianBitConverter.Big, message.MemoryStream), battleProxy);
				;
			}

			messages.Clear ();
		}
	}


	// Update is called once per frame
	public void Update () {
		networkManager.Update ();

		DispatchMessages ();

		UInt16 deltaTime = (UInt16)(Time.deltaTime * 1000);
		Tick (deltaTime);
	}

	private void Tick(UInt16 deltaTime) {

		if (!inSyncWithServer) {
			return;
		}

		foreach (KeyValuePair<UInt32, Character> pair in characters)
		{
//			pair.Key,
			pair.Value.Tick(deltaTime);
		}

		timestamp += deltaTime;
		if (timestamp >= targetTimestamp) {
			if (syncs.Count > 0) {
				Sync sync = syncs[0];

				targetTimestamp = sync.timestamp;

				for (int i = 0; i < sync.items.Length; i++) {
					SyncItem syncItem = sync.items[i];

					Character character = GetCharacter (syncItem.entityId);
					character.Sync (syncItem, targetTimestamp);
				}

				syncs.RemoveAt (0);
			}
		}


	}

	class BattleProxy : BattleToPlayerProxy {
		private Game game;

		public BattleProxy(Game game){
			this.game = game;
		}

		public override void battlePing() {
			DEBUG.Info ("battle ping");
		}
		public override void battlePong() {
			DEBUG.Info ("battle pong");
		}
		public override void onJoinArena(Int32 arenaId, Sync sync, Int32 entityId) {
			GameObject.FindGameObjectWithTag ("GameManager");

			game.targetTimestamp = sync.timestamp;
			game.timestamp = game.targetTimestamp;
			game.inSyncWithServer = true;

			GameObject player = GameObject.Instantiate (Resources.Load("Player")) as GameObject;
		}
		public override void sync(Sync sync) {
			DEBUG.Info ("sync");
			game.syncs.Add (sync);
		}

		public override void playerSpawned(Int32 entityId, SyncItem item) {
		}
	}
}
