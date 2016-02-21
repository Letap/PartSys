package particles;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.Entity;
import models.RawModel;
import models.TexturedModel;
import partsys.DisplayManager;

public class Particle extends Entity {
	private RawModel model;
	public Vector3f force, mass, acceleration, velocity;
	private float maxSpeed = 0.01f, minSpeed = -maxSpeed;
	public float life = 3000;
	public float timePassed = 0;
	
	public ParticleTexture texture;
	private Vector2f texOffset1 = new Vector2f();
	private Vector2f texOffset2 = new Vector2f();
	private float blend;
	

	public Particle(TexturedModel model, ParticleTexture texture, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model,position,rotX,rotY,rotZ,scale);
		this.velocity = new Vector3f(0, 0, 0);
		this.acceleration = new Vector3f(0,0,0);
		this.texture = texture;
	}
	
	public void updatePosition(float d){
		
		Vector3f.add(velocity, acceleration, velocity);
		clampVelocity();
		updateTextureInfo();
		timePassed += DisplayManager.getDelta();
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

	public ParticleTexture getTexture() {
		return texture;
	}

	public Vector2f getTexOffset1() {
		return texOffset1;
	}

	public Vector2f getTexOffset2() {
		return texOffset2;
	}

	public float getBlend() {
		return blend;
	}
	
	
	private void updateTextureInfo(){
		float lifeFactor = timePassed/life;
		int stageCount = texture.getNumberOfRows()*texture.getNumberOfRows();
		float atlasProg = lifeFactor * stageCount;
		int index1 =(int) Math.floor(atlasProg);
		int index2 = index1 < stageCount - 1 ? index1+1:index1;
		this.blend = atlasProg % 1;
		
		setTextureOffset(texOffset1, index1);
		setTextureOffset(texOffset2, index2);
	}
	
	private void setTextureOffset(Vector2f offset,int index){
		int column = index % texture.getNumberOfRows();
		int row = index/texture.getNumberOfRows();
		offset.x = (float) column/texture.getNumberOfRows();
		offset.y = (float) row/texture.getNumberOfRows();
	}

	public Vector3f getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector3f acceleration) {
		this.acceleration = acceleration;
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}
	
	
}
