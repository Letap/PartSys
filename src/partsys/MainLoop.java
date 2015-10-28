package partsys;

import org.lwjgl.opengl.Display;

import models.RawModel;
import models.TexturedModel;

public class MainLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		
		ModelLoader loader = new ModelLoader();
        ModelRenderer renderer = new ModelRenderer();
        StaticShader shader = new StaticShader();
        
        float[] vertices = {
        		-0.5f, 0.5f, 0f,
        		-0.5f, -0.5f, 0f,
        		0.5f, -0.5f, 0f,
        		0.5f, 0.5f, 0f,
        };
        
        int[] indices = {
        		0, 1, 3, 3, 1, 2
        };
        
        float[] textureCoods={
        	0,0,
        	0,1,
        	1,1,
        	1,0
        };
        

        ModelTexture texture = new ModelTexture(loader.loadTexture("res/image.png"));
        
        RawModel model = loader.loadToVAO(vertices, textureCoods, indices);
        TexturedModel texturedModel = new TexturedModel(model,texture);
 

		while(!Display.isCloseRequested()){
			renderer.prepare();
        	shader.start();
        	renderer.render(texturedModel);
        	shader.stop();
			DisplayManager.updateDisplay();
		}
    	loader.deleteAll();
    	shader.cleanUp();
		
		DisplayManager.closeDisplay();

	}

}
