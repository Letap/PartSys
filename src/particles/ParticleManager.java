package particles;

import java.util.LinkedList;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import partsys.ModelRenderer;
import partsys.StaticShader;

public class ParticleManager {
	private final LinkedList<Particle> particles = new LinkedList<Particle>();
	
	public ParticleManager(TexturedModel model){
		Random rand = new Random();
        for(int i=0; i<200; i++){
        	float x = (float) (Math.random()*(31) -15);
        	float y =  (float) (Math.random()*(17) -8);
        	float z =  (float) (Math.random()*(17) -25);
        	particles.add( new Particle(model, new Vector3f(x,y,z),0,0,0,0.1f));
        }
	}
	
	public void updateParticles(ModelRenderer renderer, StaticShader shader){
		for (int i = 0; i < particles.size(); i++) {
			renderer.render(particles.get(i),shader);
			(particles.get(i)).increaseRotation((float)Math.random(),(float)Math.random(), (float)Math.random());
			(particles.get(i)).increasePosition(0, -0.002f, 0);
        }
	}

}
