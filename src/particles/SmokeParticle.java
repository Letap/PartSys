package particles;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class SmokeParticle extends Particle {

	public SmokeParticle(TexturedModel model, ParticleTexture texture, Vector3f position, float rotX, float rotY,
			float rotZ, float scale) {
		super(model, texture, position, rotX, rotY, rotZ, scale);

		this.velocity = new Vector3f((float) (Math.random()*(0.006f)-0.003f ),(float) (Math.random()*100f),(float) (Math.random()*(0.006f)-0.003f ));
		this.acceleration = new Vector3f(0,0,0);
		this.texture = texture;
		int l = (400 + (int ) (300*Math.random()));
		int rem = l % 16;
		this.life = l-rem;
	}
	public boolean isOutOfBounds(){
		if(this.getPosition().y <-0.01) return true;
		if(this.life < this.timePassed) return true;
		return false;
	}
	
	

}
