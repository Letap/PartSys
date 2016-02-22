package partsys;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.sun.prism.ps.Shader;

import gui.GuiRenderer;
import gui.GuiTexture;
import models.Entity;
import models.Floor;
import models.Line;
import models.RawModel;
import models.TexturedModel;
import particles.Emitter;
import particles.FireworkEmitter;
import particles.Particle;
import particles.ParticleManager;
import particles.ParticleTexture;
import particles.SmokeEmitter;
import poly.PolyRenderer;

public class MainLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();		
		//Create lighting
		Light light = new Light(new Vector3f(25,0,-25), new Vector3f(1,1,1));
        
		//Create camera
		Camera camera = new Camera();   
		
		//Create model loader
		ModelLoader loader = new ModelLoader(); 
		
		//Create renderers
		Renderer mainRenderer = new Renderer();
		PolyRenderer polyRenderer = new PolyRenderer(loader,mainRenderer.getProjectionMatrix());
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
        //Models
        ModelTexture treetexture = new ModelTexture(loader.loadTexture("res/green.png"));
        RawModel tree = OBJLoader.loadOBJModel("res/tree.obj", loader);
        TexturedModel texttree = new TexturedModel(tree,treetexture);
        Entity entity = new Entity(texttree, new Vector3f(0,0,25),0,0,0,5);
        Entity[] trees = new Entity[50];
        for(int i=0;i<trees.length;i++){
        	trees[i] = new Entity(texttree, new Vector3f((int)(200*Math.random()-100),0,(int)(100*Math.random())),0,0,0,(float)(4+2*Math.random()));
        }
        ModelTexture ftexture = new ModelTexture(loader.loadTexture("res/yellow.png"));
        ParticleTexture fireworktext = new ParticleTexture(ftexture.getID(),2);
        
        
        //Particles
        RawModel particle = OBJLoader.loadOBJModel("res/part.obj", loader);
        ModelTexture particletexture = new ModelTexture(loader.loadTexture("res/snowflake.png"));
        TexturedModel texparticle = new TexturedModel(particle,particletexture);
        List<Entity> polys = new ArrayList<Entity>(); 
        
        //GUI
        List<GuiTexture> guis = new ArrayList<GuiTexture>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("res/grass.png"),new Vector2f(0.5f,0.5f),new Vector2f(0.25f,0.25f));
        guis.add(gui);
        
        
        
        
        //Load floor
        Floor floor = new Floor(0,0,loader, new ModelTexture(loader.loadTexture("res/grass.png")));
        Floor floor2 = new Floor(-1,0,loader, new ModelTexture(loader.loadTexture("res/grass.png")));
        
        //Create particle manager        
        ParticleManager pmanager = new ParticleManager(polyRenderer,camera);
        
        //Create and start particle emitter
        ModelTexture parttexture = new ModelTexture(loader.loadTexture("res/smoke3.png"));
        ParticleTexture smoketext = new ParticleTexture(parttexture.getID(),8);
        //SmokeEmitter emitter = new SmokeEmitter( texparticle, smoketext, 0,0,30, pmanager);
        Emitter emitter = new Emitter( texparticle, new ParticleTexture(particletexture.getID(),2), 0,0,0,0,0,0, pmanager);
        emitter.start();
        
        /*//Performs tests
        emitter.startCheck();
        emitter.start();
        emitter.startCheck();
        for(int i = 0; i<6; i++){
	        emitter.total = emitter.total*2;
	        emitter.start();
	        emitter.startCheck();
        }*/
        
        
        
        boolean mouseDown = false;
        boolean fDown = false;
        
		while(!Display.isCloseRequested()){
			camera.move();
			
			for(int i=0;i<trees.length;i++){
				mainRenderer.processEntity(trees[i]);
	        }
			
			mainRenderer.processFloor(floor);
			mainRenderer.processFloor(floor2);
			mainRenderer.render(light,camera);

            pmanager.updateParticles(mainRenderer);
			
			polyRenderer.render(polys, camera);
			
			if(Mouse.isButtonDown(0)){
				mouseDown = true;
			}
			else{
				if(mouseDown){
					Vector3f pos = camera.getCentre();
					SmokeEmitter emitter2 = new SmokeEmitter( texparticle, smoketext,pos.getX(),pos.getY(),pos.getZ(), pmanager);
			        emitter2.start();
				}
				mouseDown = false;
				
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_F)){
				fDown = true;
			}
			else{
				if(fDown){
					fDown = false;
					Vector3f pos = camera.getCentre();
					new FireworkEmitter(texparticle, fireworktext,pos.getX(),pos.getY(),pos.getZ(), pmanager);
					
				}
			}
			
			/*
			if(Keyboard.isKeyDown(Keyboard.KEY_B)){
				for(int i=-20; i<=80;i+=20){
					for(int j=20; j<=120;j+=20){
						polyRenderer.renderVertices3d(camera,new Vector3f(i,0,j),new Vector3f(i,20,j));
					}
				}
				for(int i=0; i<=20;i+=10){
					for(int j=20; j<=120;j+=20){	
						polyRenderer.renderVertices3d(camera,new Vector3f(-20,i,j),new Vector3f(80,i,j));
					}
				}
				
				for(int i=0; i<=20;i+=10){
					for(int j=-20; j<=80;j+=20){	
						polyRenderer.renderVertices3d(camera,new Vector3f(j,i,20),new Vector3f(j,i,120));
					}
				}
			}*/
			//guiRenderer.render(guis);bbb
			DisplayManager.updateDisplay();
		}
        polyRenderer.cleanUp();
    	loader.deleteAll();
		
		DisplayManager.closeDisplay();

	}

}
