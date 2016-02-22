package particles;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class FireworkParticle extends Particle {

	public FireworkParticle(TexturedModel model, ParticleTexture texture, Vector3f position, float rotX, float rotY,
			float rotZ, float scale) {
		super(model, texture, position, rotX, rotY, rotZ, scale);
		// TODO Auto-generated constructor stub
		this.life = 2000;
	}
	
	public boolean isOutOfBounds(){
		//if(this.getPosition().y <-0.01) return true;
		if(this.life < this.timePassed) return true;
		return false;
	}
	
	
}
