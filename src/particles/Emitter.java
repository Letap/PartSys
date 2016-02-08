package particles;

import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Emitter {
	public TexturedModel cloud;
	private ParticleManager pman;
	private TexturedModel model;
	private Timer timer = new Timer();
	
	public Emitter( TexturedModel model, int minx, int maxx, int miny, int maxy, int minz, int maxz, ParticleManager pman ){
		this.model = model;
		this.pman = pman;
	}

	public void start(){
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run() {
		        	float x = (float) (Math.random()*(100)-20 );
		        	float y = 20;
		        	float z =  (float) (Math.random()*(100)+20 );
		        	pman.addParticle( new Particle(model, new Vector3f(x,y,z),0,0,0,0.3f));
			  }
			}, 50, 5);
		
		
	}
	
	public void end(){
		timer.cancel();		
	}
}
