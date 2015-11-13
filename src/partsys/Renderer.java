package partsys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import models.Entity;
import models.TexturedModel;

public class Renderer {
	private StaticShader shader = new StaticShader();
	private ModelRenderer renderer = new ModelRenderer(shader);
	
	private Map<TexturedModel,List<Entity>> entities = new HashMap<TexturedModel,List<Entity>>();
	
	public void render(Light light, Camera camera){
		prepare();
		shader.start();
		shader.loadLight(light);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		entities.clear();
	}
	
	public void processEntity(Entity entity){
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch!=null) batch.add(entity);
		else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	
	}
	
	public void prepare(){
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(1, 0, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void cleanUp(){ shader.cleanUp(); }
}
