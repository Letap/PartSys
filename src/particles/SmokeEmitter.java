package particles;

import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class SmokeEmitter {

	private ParticleManager pman;
	private ParticleTexture texture;
	private TexturedModel model;
	private Timer timer = new Timer();
	
	public SmokeEmitter( TexturedModel model, ParticleTexture texture, int minx, int maxx, int miny, int maxy, int minz, int maxz, ParticleManager pman ){
		this.model = model;
		this.pman = pman;
		this.texture = texture;
	}

	
	public void start(){
		
		
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run() {
		        	float x = 0 ;
		        	float y = 0;
		        	float z = 30 ;
		        	Particle p = new SmokeParticle(model, texture, new Vector3f(x,y,z),0,0,0,1f);
		        	pman.addParticle(p);
			  }
			}, 50, 2);
	}
}
