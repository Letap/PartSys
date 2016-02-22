package particles;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import partsys.Camera;
import partsys.DisplayManager;
import partsys.Renderer;
import poly.PolyRenderer;

public class ParticleManager {
	private final Map<ParticleTexture, LinkedList<Particle>> particles = new HashMap<ParticleTexture, LinkedList<Particle>>();
	private PolyRenderer polyRenderer;
	private Camera camera;
	private Iterator<Entry<ParticleTexture, LinkedList<Particle>>> mapIterator;
	LinkedList<Particle> list;
	public int minx = -20, maxx = 20, miny=0, maxy=20, minz=0, maxz=20;
	
	public BoxContainer boxes = new BoxContainer(new Vector3f(-10,0,20), new Vector3f(10,20,40),5);
	
	public ParticleManager(PolyRenderer polyRenderer, Camera camera){
        this.polyRenderer = polyRenderer;
        this.camera = camera;
	}
	
	public void updateParticles(Renderer renderer){
		float delta = DisplayManager.getDelta();
		mapIterator = particles.entrySet().iterator();
		polyRenderer.prepare();
		while(mapIterator.hasNext()){			
			list = mapIterator.next().getValue();
			for (int i = 0; i < list.size(); i++) {
				Particle pi = list.get(i);
				pi.updatePosition(delta);
				/*
				//Handle boxes update
				Vector3f index = boxes.getIndex(pi.getPosition());
				pi.setI((int)index.getX());
				pi.setJ((int)index.getY());
				pi.setK((int)index.getZ());
				*/
				if(isOutOfBounds(pi.getPosition())||pi.isOutOfBounds()) list.remove(pi);
				
				if(list.isEmpty()) mapIterator.remove();
				polyRenderer.render(pi, camera);
				//renderer.processEntity(pi);
				
				//Add universal forces
	        }
		}
		polyRenderer.finishRendering();
		//boxes.updateParticles();
	}
	
	public void addParticle(Particle p){
		LinkedList<Particle> list = particles.get(p.getTexture());
		if(list == null) {
			list = new LinkedList<Particle>();
			particles.put(p.getTexture(), list);
		}
		list.add(p);
		//boxes.addPoint(p);
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
	
	//Returns linked list of particles closer than f
	public LinkedList<Particle> findWithinDistance(Particle p, float f){
		int comparisons = 0;
		LinkedList<Particle> ret = new LinkedList<Particle>();
		Vector3f pos1 = p.getPosition();
		Iterator<Entry<ParticleTexture, LinkedList<Particle>>> mapIterator = particles.entrySet().iterator();
		while(mapIterator.hasNext()){
			
			LinkedList<Particle> list = mapIterator.next().getValue();
			for (int i = 0; i < list.size(); i++) {
				Particle pi = list.get(i);
				Vector3f pos2 = pi.getPosition();
				Vector3f diff = new Vector3f(pos1.getX()-pos2.getX(),pos1.getY()-pos2.getY(),pos1.getZ()-pos2.getZ());
				float d = (float) Math.sqrt(diff.getX()*diff.getX() + diff.getY()*diff.getY() + diff.getZ()*diff.getZ());
				System.out.println(d);
				if(d<=f) ret.add(pi); 
				comparisons++;
	        }
		}
		System.out.println(ret.size());
		System.out.println(comparisons + " comparisions for linkedlist test");
		return ret;
	}

}
