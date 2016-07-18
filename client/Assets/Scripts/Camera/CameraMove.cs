using System;
using UnityEngine;

public class CameraMove : MonoBehaviour
{
	public GameObject target;
	private Vector3 offset = new Vector3(0, 30, -35.77f);

	public CameraMove ()
	{
	
	}

	void Start(){
	}

	void Update(){
		if (target) {
			transform.position = offset + target.transform.position;
		}
	}
}
