package partsys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Entity;
import models.TexturedModel;

public class Renderer {
	private StaticShader shader = new StaticShader();
	private ModelRenderer renderer = new ModelRenderer(shader);
	
	private Map<TexturedModel,List<Entity>> entities = new HashMap<TexturedModel,List<Entity>>();
	
	public void render(Light light, Camera camera){
		renderer.prepare();
		shader.start();
		shader.loadLight(light);
		shader.loadViewMatrix(camera);
		
		shader.stop();
		entities.clear();
	}

	public void cleanUp(){ shader.cleanUp(); }
}
