package gui;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import models.RawModel;
import partsys.ModelLoader;

public class GuiRenderer {

	private final RawModel quad;
	public GuiRenderer(ModelLoader loader){
		float[] positions = {-1f, 1f, -1f, -1f, 1f, 1f, 1f, -1f};
		quad = loader.loadToVAO(positions, 2);
		
	}
	
	public void render(List<GuiTexture> guis){
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		//render
		for(GuiTexture gui:guis){
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		}
		
		
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
}
