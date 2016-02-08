package particles;

import java.util.LinkedList;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import partsys.Camera;
import partsys.Renderer;
import poly.PolyRenderer;

public class ParticleManager {
	private final LinkedList<Particle> particles = new LinkedList<Particle>();
	private PolyRenderer polyRenderer;
	private Camera camera;
	public int minx = -20, maxx = 20, miny=0, maxy=20, minz=0, maxz=20;
	
	public ParticleManager(PolyRenderer polyRenderer, Camera camera){
        this.polyRenderer = polyRenderer;
        this.camera = camera;
	}
	
	public void updateParticles(Renderer renderer){
		for (int i = 0; i < particles.size(); i++) {
			Particle pi = (particles.get(i));
			pi.updatePosition();
			if(isOutOfBounds(pi.getPosition())) particles.remove(pi);

			//polyRenderer.render(pi, camera);
			renderer.processEntity(pi);
			pi.increaseRotation((float)Math.random(),(float)Math.random(), (float)Math.random());
			//pi.increasePosition(0, -0.005f, 0);
        }
	}
	
	public void addParticle(Particle p){
		particles.add(p);
	}
	
	private boolean isOutOfBounds(Vector3f position){
		//if(position.x<=minx) return true;
		//if(position.x>=maxx) return true;
		if(position.y<=miny) return true;
		//if(position.y>=maxy) return true;
		//if(position.z<=minz) return true;
		//if(position.z>=maxz) return true;
		
		return false;
	}

}
