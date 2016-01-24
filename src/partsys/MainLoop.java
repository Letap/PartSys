package partsys;

import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import models.Entity;
import models.Floor;
import models.RawModel;
import models.TexturedModel;
import particles.Particle;
import particles.ParticleManager;

public class MainLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
				
		float[] vertices = {			
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,0.5f,-0.5f,		
				
				-0.5f,0.5f,0.5f,	
				-0.5f,-0.5f,0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				0.5f,0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				-0.5f,-0.5f,0.5f,	
				-0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,0.5f,
				-0.5f,0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				0.5f,0.5f,0.5f,
				
				-0.5f,-0.5f,0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f
				
		};
		
		float[] textureCoords = {
				
				0,0,
				0,1,
				1,1,
				1,0,
				
				0,0,
				0,1,
				1,1,
				1,0,
				
				0,0,
				0,1,
				1,1,
				1,0,
				
				0,0,
				0,1,
				1,1,
				1,0,
				
				0,0,
				0,1,
				1,1,
				1,0,
				
				0,0,
				0,1,
				1,1,
				1,0

				
		};
		
		int[] indices = {
				0,1,3,	
				3,1,2,	
				4,5,7,
				7,5,6,
				8,9,11,
				11,9,10,
				12,13,15,
				15,13,14,	
				16,17,19,
				19,17,18,
				20,21,23,
				23,21,22

		};
		
		float[] normals = {
				0,0,1,
				0,0,1,
				0,0,1,
				0,0,1,

				0,0,1,
				0,0,1,
				0,0,1,
				0,0,1,

				0,0,1,
				0,0,1,
				0,0,1,
				0,0,1,

				0,0,1,
				0,0,1,
				0,0,1,
				0,0,1,

				0,0,1,
				0,0,1,
				0,0,1,
				0,0,1,

				0,0,1,
				0,0,1,
				0,0,1,
				0,0,1,
				
		};
		
		
		ModelLoader loader = new ModelLoader();    
        ModelTexture texture = new ModelTexture(loader.loadTexture("res/image.png"));
        
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        
        
        RawModel model = OBJLoader.loadOBJModel("res/dragon.obj", loader);
        TexturedModel texturedModel = new TexturedModel(model,texture);
        
        RawModel cubemodel = loader.loadToVAO(vertices, textureCoords, normals, indices);
        

        TexturedModel textcube = new TexturedModel(cubemodel,texture);
        
        Entity entity = new Entity(texturedModel, new Vector3f(0,0,25),0,0,0,1);
        Entity cube = new Entity(textcube, new Vector3f(0,0,25),0,0,0,1);
        Light light = new Light(new Vector3f(25,0,-25), new Vector3f(1,1,1));
        Camera camera = new Camera();
        

        Floor floor = new Floor(0,0,loader, new ModelTexture(loader.loadTexture("res/grass.png")));
        Floor floor2 = new Floor(-1,0,loader, new ModelTexture(loader.loadTexture("res/grass.png")));
        
        RawModel particle = OBJLoader.loadOBJModel("res/part.obj", loader);
        
        //ParticleManager pmanager = new ParticleManager(new TexturedModel(particle,new ModelTexture(loader.loadTexture("res/image2.png"))));

        Renderer mainRenderer = new Renderer();
		while(!Display.isCloseRequested()){
		//	entity.increasePosition(0, 0, -0.002f);
			entity.increaseRotation(0, 1f, 0);
			camera.move();
			
		    GL11.glColor3f(0.0f, 1.0f, 0.2f);
		    GL11.glBegin(GL11.GL_LINE);

		    GL11.glVertex3f(-5,-5,-5);
		    GL11.glVertex3f(5,5,5);
		    GL11.glEnd();
			mainRenderer.processEntity(entity);
			mainRenderer.processEntity(cube);
			mainRenderer.processFloor(floor);
			mainRenderer.processFloor(floor2);
            //pmanager.updateParticles(mainRenderer);
			mainRenderer.render(light,camera);
        	
			DisplayManager.updateDisplay();
		}
    	loader.deleteAll();
		
		DisplayManager.closeDisplay();

	}

}
