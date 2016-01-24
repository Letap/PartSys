package partsys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import models.Entity;
import models.Floor;
import models.TexturedModel;

public class Renderer {
	public Matrix4f projectionMatrix;
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	
	private StaticShader shader = new StaticShader();
	private ModelRenderer renderer;
	private FloorRenderer floorRenderer;
	
	private FloorShader floorShader = new FloorShader();
	
	private Map<TexturedModel,List<Entity>> entities = new HashMap<TexturedModel,List<Entity>>();
	private List<Floor> floors = new ArrayList<Floor>();
	
	public Renderer(){
		//GL11.glEnable(GL11.GL_CULL_FACE);
		//GL11.glCullFace(GL11.GL_BACK);
		createProjectionMatrix();
		renderer = new ModelRenderer(shader, projectionMatrix);
		floorRenderer = new FloorRenderer(floorShader, projectionMatrix);
	}
	
	public void render(Light light, Camera camera){
		prepare();
		shader.start();
		shader.loadLight(light);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		

		floorShader.start();
		floorShader.loadLight(light);
		floorShader.loadViewMatrix(camera);
		floorRenderer.render(floors);
		floorShader.stop();
		floors.clear();
		
		entities.clear();
	}
	
	public void processFloor(Floor floor){
		floors.add(floor);
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
		GL11.glClearColor(0.69f, 1, 1, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void cleanUp(){ 
		shader.cleanUp(); 
		floorShader.cleanUp();
	}
	
	private void createProjectionMatrix(){
		float aspectRatio = (float) Display.getWidth()/(float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f)))* aspectRatio);
		float x_scale = y_scale/aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;
		
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE+NEAR_PLANE)/frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2*NEAR_PLANE*FAR_PLANE)/frustum_length);
		projectionMatrix.m33 = 0;
		
	}
}
