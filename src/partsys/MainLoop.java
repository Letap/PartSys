package partsys;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import models.Entity;
import models.RawModel;
import models.TexturedModel;

public class MainLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		
		ModelLoader loader = new ModelLoader();
        StaticShader shader = new StaticShader();
        ModelRenderer renderer = new ModelRenderer(shader);
        
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

        ModelTexture texture = new ModelTexture(loader.loadTexture("res/image.png"));
        
        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        TexturedModel texturedModel = new TexturedModel(model,texture);
        Entity entity = new Entity(texturedModel, new Vector3f(0,0,-5),0,0,0,1);

        Camera camera = new Camera();
		while(!Display.isCloseRequested()){
		//	entity.increasePosition(0, 0, -0.002f);
			entity.increaseRotation(1, 1, 0);
			camera.move();
			renderer.prepare();
        	shader.start();
        	shader.loadViewMatrix(camera);
        	renderer.render(entity,shader);
        	shader.stop();
			DisplayManager.updateDisplay();
		}
    	loader.deleteAll();
    	shader.cleanUp();
		
		DisplayManager.closeDisplay();

	}

}
