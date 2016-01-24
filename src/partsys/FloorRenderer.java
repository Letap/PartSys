package partsys;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import models.Entity;
import models.Floor;
import models.RawModel;
import models.TexturedModel;
import util.Maths;

public class FloorRenderer {

	private FloorShader shader;
	
	public FloorRenderer(FloorShader shader, Matrix4f projectionMatrix){
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(List<Floor> floors){
		for (Floor floor:floors){
			prepareFloor(floor);
			loadModelMatrix(floor);
			GL11.glDrawElements(GL11.GL_TRIANGLES, floor.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			
			unbindTexturedModel();
			
		}
		
	}
	
	private void prepareFloor(Floor floor){

		RawModel model = floor.getModel();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		ModelTexture texture= floor.getTexture();
		shader.loadSpecularVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
	}
	
	private void unbindTexturedModel(){
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void loadModelMatrix(Floor floor){
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(floor.getX(),0,floor.getZ()), 
				0, 0, 0, 1);
		shader.loadTransformationMatrix(transformationMatrix);
	}
}
