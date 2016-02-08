package partsys;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
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
import particles.Particle;
import particles.ParticleManager;
import poly.PolyRenderer;

public class MainLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Vector3f s = new Vector3f(-10,0,0);
		Vector3f f = new Vector3f(-10,20,0);
		
		ModelLoader loader = new ModelLoader();    
        ModelTexture texture = new ModelTexture(loader.loadTexture("res/image3.png"));
        
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        
        
        RawModel model = OBJLoader.loadOBJModel("res/dragon.obj", loader);
        TexturedModel texturedModel = new TexturedModel(model,texture);
        

        TexturedModel textcube =(new Line(s,f,texture,loader)).model;
        
        Entity entity = new Entity(texturedModel, new Vector3f(0,0,25),0,0,0,1);
        Entity line1 = new Entity(textcube, new Vector3f(0,0,25),0,0,0,1);
        
        Light light = new Light(new Vector3f(25,0,-25), new Vector3f(1,1,1));
        Camera camera = new Camera();
        

        Floor floor = new Floor(0,0,loader, new ModelTexture(loader.loadTexture("res/grass.png")));
        Floor floor2 = new Floor(-1,0,loader, new ModelTexture(loader.loadTexture("res/grass.png")));
        
        RawModel particle = OBJLoader.loadOBJModel("res/part.obj", loader);
        
        ModelTexture particletexture = new ModelTexture(loader.loadTexture("res/image2.png"));
        TexturedModel texparticle = new TexturedModel(particle,particletexture);
        
        
        Renderer mainRenderer = new Renderer();
        
        PolyRenderer polyRenderer = new PolyRenderer(loader,mainRenderer.getProjectionMatrix());
        List<Entity> polys = new ArrayList<Entity>(); 
        polys.add(line1);
        
        List<GuiTexture> guis = new ArrayList<GuiTexture>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("res/grass.png"),new Vector2f(0.5f,0.5f),new Vector2f(0.25f,0.25f));
        guis.add(gui);
        GuiRenderer guiRenderer = new GuiRenderer(loader);
        

        ParticleManager pmanager = new ParticleManager(polyRenderer,camera);
        Emitter emitter = new Emitter( texparticle, 0, 0, 0, 0, 0, 0, pmanager);
        emitter.start();
        
		while(!Display.isCloseRequested()){
		//	entity.increasePosition(0, 0, -0.002f);
			entity.increaseRotation(0, 0.01f, 0);
			camera.move();
			
			if(Keyboard.isKeyDown(Keyboard.KEY_B)){
				polys.add(new Entity(textcube,new Vector3f( camera.getCentre()),0,0,0,1));
			}

		//	mainRenderer.processEntity(entity);
		//	mainRenderer.processEntity(line1);
			mainRenderer.processFloor(floor);
			mainRenderer.processFloor(floor2);
            pmanager.updateParticles(mainRenderer);
			mainRenderer.render(light,camera);

           // pmanager.updateParticles(mainRenderer);
			polyRenderer.render(polys, camera);
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
			
			//guiRenderer.render(guis);
			DisplayManager.updateDisplay();
		}
        polyRenderer.cleanUp();
    	loader.deleteAll();
		
		DisplayManager.closeDisplay();

	}

}
