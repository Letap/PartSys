package particles;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import partsys.Camera;
import partsys.Renderer;
import poly.PolyRenderer;

public class ParticleManager {
	private final Map<ParticleTexture, LinkedList<Particle>> particles = new HashMap<ParticleTexture, LinkedList<Particle>>();
	private PolyRenderer polyRenderer;
	private Camera camera;
	public int minx = -20, maxx = 20, miny=0, maxy=20, minz=0, maxz=20;
	
	public ParticleManager(PolyRenderer polyRenderer, Camera camera){
        this.polyRenderer = polyRenderer;
        this.camera = camera;
	}
	
	public void updateParticles(Renderer renderer){
		Iterator<Entry<ParticleTexture, LinkedList<Particle>>> mapIterator = particles.entrySet().iterator();
		while(mapIterator.hasNext()){
			
			LinkedList<Particle> list = mapIterator.next().getValue();
			for (int i = 0; i < list.size(); i++) {
				Particle pi = list.get(i);
				pi.updatePosition();
				if(isOutOfBounds(pi.getPosition())) list.remove(pi);
				
				if(list.isEmpty()) mapIterator.remove();
				polyRenderer.render(pi, camera);
				//renderer.processEntity(pi);
				pi.increaseRotation((float)Math.random(),(float)Math.random(), (float)Math.random());
				//pi.increasePosition(0, -0.005f, 0);
	        }
		}
	}
	
	public void addParticle(Particle p){
		LinkedList<Particle> list = particles.get(p.getTexture());
		if(list == null) {
			list = new LinkedList<Particle>();
			particles.put(p.getTexture(), list);
		}
		list.add(p);
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
