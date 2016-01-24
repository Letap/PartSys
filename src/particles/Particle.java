package particles;

import org.lwjgl.util.vector.Vector3f;

import models.Entity;
import models.RawModel;
import models.TexturedModel;

public class Particle extends Entity {
	private RawModel model;
	private Vector3f force, mass, acceleration, velocity;
	private float maxSpeed = 0.01f, minSpeed = -maxSpeed;
	

	public Particle(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model,position,rotX,rotY,rotZ,scale);
		this.velocity = new Vector3f(0, -0.005f, 0);
		this.acceleration = new Vector3f(0,-0.0005f,0);
	}
	
	public void updatePosition(){
		Vector3f.add(velocity, acceleration, velocity);
		clampVelocity();
		this.increasePosition(velocity.getX(), velocity.getY(), velocity.getZ());
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
