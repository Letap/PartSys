package particles;

import java.util.LinkedList;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import partsys.Renderer;

public class ParticleManager {
	private final LinkedList<Particle> particles = new LinkedList<Particle>();
	
	public ParticleManager(TexturedModel model){
        for(int i=0; i<2000; i++){
        	float x = (float) (Math.random()*(31)-20 );
        	float y =  (float) (Math.random()*(17) );
        	float z =  (float) (Math.random()*(17)+10 );
        	particles.add( new Particle(model, new Vector3f(x,y,z),0,0,0,0.03f));
        }
	}
	
	public void updateParticles(Renderer renderer){
		for (int i = 0; i < particles.size(); i++) {
			Particle pi = (particles.get(i));
			pi.updatePosition();
			if(pi.isOutOfBounds()) particles.remove(pi);
			
			renderer.processEntity(pi);
			pi.increaseRotation((float)Math.random(),(float)Math.random(), (float)Math.random());
			//pi.increasePosition(0, -0.005f, 0);
        }
	}

}
