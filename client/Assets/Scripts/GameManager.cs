using UnityEngine;
using System.Collections;

public class GameManager : MonoBehaviour {

	private Game game;

	// Use this for initialization
	void Start () {
		game = new Game ();
		game.Start ();
	}
	
	// Update is called once per frame
	void Update () {
		game.Update ();
	}
}
