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
	public int total = 100;
	
	private Random nums = new Random(843732381);
	
	public Emitter( TexturedModel model, ParticleTexture texture, int minx, int maxx, int miny, int maxy, int minz, int maxz, ParticleManager pman ){
		this.model = model;
		this.pman = pman;
		this.texture = texture;
	}

	public void start(){/*
		for(int i=0;i<total;i++){
        	float x = (float) (nums.nextFloat()*(20)-10 );
        	float y = 20;//(float) (nums.nextFloat()*20);
        	float z =  (float) (nums.nextFloat()*(20)+20 );
        	randomP = new Particle(model, texture, new Vector3f(x,y,z),0,0,0,1f);
        	pman.addParticle(randomP);
			
		}*/
		
		
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run() {
		        	float x = (float) (Math.random()*(200)-100 );
		        	float y = 20;
		        	float z =  (float) (Math.random()*(200)+0 );
		        	Particle p = new MultiTextureParticle(model, texture, new Vector3f(x,y,z),0,0,0,0.15f);
		        	p.setPolyRotation((float)(360*Math.random()));
		        	pman.addParticle(p);
		        	if(partGend==3) {
		        		randomP = p;
		        	}
		        	partGend++;
		        	//if(randomP != null) System.out.println(randomP.getPosition().getY());
			  }
			}, 50, 1);
	}
	
	
	public void startCheck(){
		//timer.schedule(new TimerTask() {
			//  @Override
			//  public void run() {
				  //System.out.println("----Starting linkedlist test----");
				  //LinkedList<Particle> parts = pman.findWithinDistance(randomP, 5f);

				  //System.out.println("----Finished linkedlist test----");
				  //System.out.println("----Starting 3d array test----");
				  LinkedList<Particle> parts = pman.boxes.nearbyParticles(randomP);
				  //System.out.println("----Finished 3d array test, printing results----");
				  int size = 0;
				  for (int l = 0; l< parts.size(); l++) {
						Particle pi = parts.get(l);
						if(pi!= randomP){
						Vector3f pos = pi.getPosition();
						//System.out.format("Particle at (%f, %f, %f)", pos.getX(), pos.getY(), pos.getZ() ); System.out.println();
						size++;
						}
			      }

				  System.out.println(size);
				  //System.out.println("----Done----");
			//  }
			//}, 1000);
	}
	
	public void end(){
		timer.cancel();		
	}
}
