using System;
using info.chenliang.moba.message;

using UnityEngine;

public class Character
{
	private Int32 id;
	private UInt32 timestamp;
	private UInt32 targetTimestamp;
	private int state;
	private int targetState;

	private float x, z;
	private float targetX, targetZ;

	private float direction;
	private float targetDirection;

	private GameObject gameObject;

	private Single idleTime;
	private Game game;

	public Int32 Id {
		get {return id;}
	}

	public Character (Game game, GameObject gameObject)
	{
		this.game = game;
		this.gameObject = gameObject;
		//this.gameObject.Rotate(0, 90, 0);
	}

	public void Sync(SyncItem syncItem, UInt32 timestamp) {

		UpdateToTargetState();

		UpdateView();

		targetX = syncItem.values[FieldType.POSITION_X];
		targetZ = syncItem.values[FieldType.POSITION_Z];
		targetDirection = syncItem.values[FieldType.DIRECTION];
		targetState = (int)syncItem.values[FieldType.STATE];



		idleTime = syncItem.values[FieldType.IDLE_TIME];
	}

	private void UpdateView() {
		// update unity visuals to reflect the state change
		//transform.RotateAround(transform.position, transform.up, Time.deltaTime * 90f);
		gameObject.transform.Rotate(0, -gameObject.transform.rotation.y + direction, 0, Space.Self);
		gameObject.transform.Translate(-gameObject.transform.position.x + x, 0, -gameObject.transform.position.z + z, Space.World);


	}

	private void UpdateToTargetState() {
		x = targetX;
		z = targetZ;
		direction = targetDirection;
		state = targetState;
	}

	private void UpdatePosition(UInt16 deltaTime) {
		Vector2 direction = new Vector2(targetX - x, targetZ - z);
		float length = direction.magnitude;
		direction.Normalize();

		float timeLeft = targetTimestamp - idleTime - game.Timestamp - deltaTime;
		Vector2 delta = length * deltaTime / timeLeft * direction;

		Vector2 position = new Vector2(x, z) + delta;

		x = position.x;
		z = position.y;
	}

	private void UpdateDirection(UInt16 deltaTime) {
		float diff = targetDirection - direction;

		if (diff > 180) {
			diff = 360 - diff;
		} else if (diff < -180) {
			diff = 360 + diff;
		}

		float timeLeft = targetTimestamp - idleTime - game.Timestamp - deltaTime;
		float deltaAngle = deltaTime * diff / timeLeft;
		if (deltaAngle >= diff) {
			direction = targetDirection;
		} else {
			float o = targetDirection - direction;
			if (o == diff) {
				if (o < 0) {
					deltaAngle *= -1;
				} else if (o > 0) {
					deltaAngle *= +1;
				}
			} else {
				if (o < 0) {
					deltaAngle *= +1;
				} else if (o > 0) {
					deltaAngle *= -1;
				}
			}

			float updatedAngle = direction + deltaAngle;
			if (updatedAngle > 360) updatedAngle -= 360;
			if (updatedAngle < -360) updatedAngle += 360;

			direction = updatedAngle;
		}
	}

	public void Tick(UInt16 deltaTime) {

		if (game.Timestamp + deltaTime + idleTime >= targetTimestamp) {
			UpdateToTargetState();
			UpdateView();
		} else {
			// position
			UpdatePosition(deltaTime);

			// direction
			UpdateDirection(deltaTime);


			UpdateView();
		}
	}
}

