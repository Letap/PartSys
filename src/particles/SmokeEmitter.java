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
	private float x,y,z;
	
	public SmokeEmitter( TexturedModel model, ParticleTexture texture, float x, float y, float z, ParticleManager pman ){
		this.model = model;
		this.pman = pman;
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	
	public void start(){
		
		
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run() {
		        	Particle p = new SmokeParticle(model, texture, new Vector3f(x,y,z),0,0,0,1f);
		        	pman.addParticle(p);
			  }
			}, 50, 2);
	}
}
