package partsys;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	private Vector3f position = new Vector3f(0,10,0);
	private Vector3f centre = new Vector3f(0,10,25);
	private float distance = 25;
	private float pitch;
	private float yaw=180;
	private float roll;
	private float theta = 0;
	
	public Camera(){}

	public void move(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			centre.z += 0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			centre.z -= 0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			centre.x += 0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			centre.x -= 0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Z)){
			centre.y -= 0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_X)){
			centre.y += 0.5f;
		}
		computeTheta();
		computePitch();
		computeZoom();
		float ydiff = computeYdiff();
		float hordiff = computeHorizontaldiff();
		computePosition(ydiff, hordiff);
		yaw =180- theta;
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
	
	private void computeZoom(){
		float zoom = Mouse.getDWheel()*0.1f;
		distance -= zoom;
	}
	
	private void computePitch(){
		if(Mouse.isButtonDown(1)){
			float dp = Mouse.getDY()* 0.3f;
			pitch -= dp;
		}
	}
	
	private void computeTheta(){
		if(Mouse.isButtonDown(1)){
			float dt = Mouse.getDX() * 0.3f;
			theta -= dt;
		}
	}
	
	private float computeYdiff(){
		return (float) (distance*Math.sin(Math.toRadians(pitch)));
	}
	private float computeHorizontaldiff(){
		return (float) (distance*Math.cos(Math.toRadians(pitch)));
	}
	private void computePosition(float ydiff, float hdiff){
		position.y = centre.y + ydiff;
		position.x = centre.x - (float) (hdiff * Math.sin(Math.toRadians(theta)));
		position.z = centre.z - (float) (hdiff * Math.cos(Math.toRadians(theta)));
		
	}

	public Vector3f getCentre() {
		return centre;
	}
	
	
}
