using UnityEngine;
using System.Collections;
using System.Collections.Generic;

using System;
using info.chenliang.moba.message;

public class Game {
	NetworkManager networkManager;
	List<Message> messages;
	List<Sync> syncs;
	Dictionary<Int32, Character> characters;

	UInt32 timestamp;

	UInt32 targetTimestamp;
	BattleProxy battleProxy;

	private void Init() {
		messages = new List<Message> ();
		characters = new Dictionary<int, Character> ();

		networkManager = new NetworkManager ();
		networkManager.Start ();

		battleProxy = new BattleProxy ();
	}

	// Use this for initialization
	public void Start () {
		Init ();


	}

	private Character GetCharacter(Int32 id){
		return characters[id];
	}

	private void DispatchMessages() {
		if (messages.Count > 0) {
			for (int i = 0; i < messages.Count; i++) {
				Message message = messages[i];
				BattleToPlayerDispatcher.Dispatch (message.MemoryStream, battleProxy);	
			}

			messages.Clear ();
		}
	}

	private void Sync() {

		if (timestamp == 0 || timestamp >= targetTimestamp) {
			if(syncs.Count > 0){
				Sync sync = syncs[i];

				targetTimestamp = sync.timestamp;

				for (int i = 0; i < sync.items.GetLength; i++) {
					SyncItem syncItem = sync.items[i];

					Character character = GetCharacter (syncItem.entityId);
					character.Sync (syncItem, targetTimestamp);
				}

				syncs.RemoveAt (0);
			}	
		}
	}


	// Update is called once per frame
	public void Update () {
		networkManager.Update ();

		DispatchMessages ();

		Sync ();

		Tick (Time.deltaTime*1000);
	}

	private void Tick(UInt16 deltaTime) {
		
		foreach (KeyValuePair<Int32, Character> pair in characters)
		{
//			pair.Key, 
			pair.Value.Tick(deltaTime);
		}
	}

	class BattleProxy : BattleToPlayerProxy {
		public override void battlePing(){
			DEBUG.Info ("battle ping");
		}
		public override void battlePong(){
			DEBUG.Info ("battle pong");
		}
		public override void onJoinArena(Int32 arenaId,Sync sync,Int32 entityId){
			GameObject.FindGameObjectWithTag ("GameManager");

			GameObject player = GameObject.Instantiate (Resources.Load("Player")) as GameObject;
		}
		public override void sync(Sync sync){
			DEBUG.Info ("sync");
			syncs.Add (sync);
		}

		public override void playerSpawned(Int32 entityId,SyncItem item) {
		}
	}
}
