package partsys;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	private Vector3f position = new Vector3f(0,10,0);
	private float pitch;
	private float yaw=180;
	private float roll;
	
	public Camera(){}

	public void move(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			position.y += 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			position.y -= 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			position.x += 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			position.x -= 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Z)){
			position.z -= 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_X)){
			position.z += 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_C)){
			pitch -= 1;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_F)){
			pitch += 1;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_V)){
			yaw -= 1;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_G)){
			yaw += 1;
		}
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	
}
