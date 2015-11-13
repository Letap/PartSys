package partsys;

import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import models.Entity;
import models.RawModel;
import models.TexturedModel;
import particles.Particle;
import particles.ParticleManager;

public class MainLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
				
		ModelLoader loader = new ModelLoader();    
        ModelTexture texture = new ModelTexture(loader.loadTexture("res/image.png"));
        
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        
        RawModel model = OBJLoader.loadOBJModel("res/dragon.obj", loader);
        TexturedModel texturedModel = new TexturedModel(model,texture);
        Entity entity = new Entity(texturedModel, new Vector3f(0,-8,-25),0,0,0,1);
        Light light = new Light(new Vector3f(25,25,0), new Vector3f(1,1,1));
        Camera camera = new Camera();
        
        RawModel particle = OBJLoader.loadOBJModel("res/part.obj", loader);
        
        ParticleManager pmanager = new ParticleManager(new TexturedModel(particle,texture));

        Renderer mainRenderer = new Renderer();
		while(!Display.isCloseRequested()){
		//	entity.increasePosition(0, 0, -0.002f);
			entity.increaseRotation(0, 0.1f, 0);
			camera.move();

			mainRenderer.processEntity(entity);
            pmanager.updateParticles(mainRenderer);
			mainRenderer.render(light,camera);
        	
			DisplayManager.updateDisplay();
		}
    	loader.deleteAll();
		
		DisplayManager.closeDisplay();

	}

}
