package particles;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Emitter {
	public TexturedModel cloud;
	private ParticleManager pman;
	private ParticleTexture texture;
	private TexturedModel model;
	private Timer timer = new Timer();
	
	private Particle randomP;
	private int partGend = 0;
	
	private Random nums = new Random(1432);
	
	public Emitter( TexturedModel model, ParticleTexture texture, int minx, int maxx, int miny, int maxy, int minz, int maxz, ParticleManager pman ){
		this.model = model;
		this.pman = pman;
		this.texture = texture;
	}

	public void start(){
		for(int i=0;i<20;i++){
        	float x = (float) (nums.nextFloat()*(20)-10 );
        	float y = (float) (nums.nextFloat()*20);
        	float z =  (float) (nums.nextFloat()*(20)+20 );
        	randomP = new Particle(model, texture, new Vector3f(x,y,z),0,0,0,1f);
        	pman.addParticle(randomP);
			
		}
		
		/*
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run() {
		        	float x = (float) (Math.random()*(20)-10 );
		        	float y = 20;
		        	float z =  (float) (Math.random()*(20)+20 );
		        	Particle p = new Particle(model, texture, new Vector3f(x,y,z),0,0,0,1f);
		        	pman.addParticle(p);
		        	if(partGend==3) {
		        		randomP = p;
		        	}
		        	partGend++;
		        	if(randomP != null) System.out.println(randomP.getPosition().getY());
			  }
			}, 50, 100);*/
	}
	
	
	public void startCheck(){
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  System.out.println("----Starting linkedlist test----");
				  //LinkedList<Particle> parts = pman.findWithinDistance(randomP, 5f);

				  System.out.println("----Finished linkedlist test----");
				  System.out.println("----Starting 3d array test----");
				  LinkedList<Particle> parts = pman.boxes.nearbyParticles(randomP);
				  System.out.println("Done Check");
				  for (int l = 0; l< parts.size(); l++) {
						Particle pi = parts.get(l);
						if(pi!= randomP){
						Vector3f pos = pi.getPosition();
						System.out.format("%f, %f, %f", pos.getX(), pos.getY(), pos.getZ() );

						System.out.println();
						}
			      }

				  System.out.println(parts.size());
				  System.out.println("Done with test");
			  }
			}, 1000);
	}
	
	public void end(){
		timer.cancel();		
	}
}
