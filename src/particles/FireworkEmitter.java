package particles;

import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class FireworkEmitter {

	private ParticleManager pman;
	private ParticleTexture texture;
	private TexturedModel model;
	private float x,y,z;
	
	public FireworkEmitter( TexturedModel model, ParticleTexture texture, float x, float y, float z, ParticleManager pman ){
		this.model = model;
		this.pman = pman;
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.z = z;
		this.start();
	}

	
	public void start(){
		for(int i = 0; i<360;i=i+10){
			for(int j = 0; j<360;j=j+10){
				float i1 = (float)(i+10*Math.random());
				float j1 = (float)(j+10*Math.random());
	        	Particle p = new FireworkParticle(model, texture, new Vector3f(x,y,z),0,0,0,0.05f);
	        	float xvel = (float)(Math.cos(Math.toRadians(i1))*0.005f*Math.cos(Math.toRadians(j1)));
	        	float yvel = (float)(Math.sin(Math.toRadians(j1))*0.005f);
	        	float zvel = (float)(Math.sin(Math.toRadians(i1))*0.005f*Math.cos(Math.toRadians(j1)));
	        	p.setVelocity(new Vector3f(xvel,yvel,zvel));
	        	p.setAcceleration(new Vector3f(0,-0.0002f,0));
	        	pman.addParticle(p);
			}
		}
	}

}
