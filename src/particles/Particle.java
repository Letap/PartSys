package particles;

import org.lwjgl.util.vector.Vector3f;

import models.Entity;
import models.RawModel;
import models.TexturedModel;
import partsys.DisplayManager;

public class Particle extends Entity {
	private RawModel model;
	private Vector3f force, mass, acceleration, velocity;
	private float maxSpeed = 0.01f, minSpeed = -maxSpeed;
	private float life = 100;
	private float timePassed = 0;
	
	private ParticleTexture texture;
	

	public Particle(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model,position,rotX,rotY,rotZ,scale);
		this.velocity = new Vector3f(0, -20f, 0);
		this.acceleration = new Vector3f(0,-0.0005f,0);
	}
	
	public void updatePosition(){
		
		Vector3f.add(velocity, acceleration, velocity);
		clampVelocity();
		float d = DisplayManager.getDelta();
		this.increasePosition(velocity.getX()*d, velocity.getY()*d, velocity.getZ()*d);
	}
	
	public boolean isOutOfBounds(){
		if(this.getPosition().y <-0.01) return true;
		
		return false;
	}
	
	private void clampVelocity(){
		velocity = new Vector3f(Math.min(maxSpeed, velocity.getX()),Math.min(maxSpeed, velocity.getY()),Math.min(maxSpeed, velocity.getZ()));
		velocity = new Vector3f(Math.max(minSpeed, velocity.getX()),Math.max(minSpeed, velocity.getY()),Math.max(minSpeed, velocity.getZ()));
	}
	
	
}
