package poly;

import java.util.LinkedList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import partsys.Camera;
import models.Entity;
import models.RawModel;
import particles.Particle;
import partsys.ModelLoader;
import util.Maths;

public class PolyRenderer {
	
	private static final float[] VERTICES = {-0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f};
	
	private RawModel quad;
	private PolyShader shader;
	private ModelLoader loader;
	
	public PolyRenderer(ModelLoader loader, Matrix4f projectionMatrix){
		this.loader = loader;
		quad = loader.loadToVAO(VERTICES, 2);
		shader = new PolyShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(List<Entity> polys, Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		prepare();
		for (Entity poly:polys){
			updateModelViewMatrix(poly.getPosition(), poly.getPolyRotation(), poly.getScale(), viewMatrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
			
		}
		finishRendering();
	}	
	/*
	public void render(Entity poly, Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		prepare();
		updateModelViewMatrix(poly.getPosition(), poly.getPolyRotation(), poly.getScale(), viewMatrix);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		finishRendering();
	}*/
	
	public void render(Particle poly, Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		prepare();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, poly.getTexture().getTextureID());
		updateModelViewMatrix(poly.getPosition(), poly.getPolyRotation(), poly.getScale(), viewMatrix);
		shader.loadTextureCoordInfo(poly.getTexOffset1(), poly.getTexOffset2(), poly.getTexture().getNumberOfRows(), poly.getBlend());
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		finishRendering();
	}
	
	/*
	public void renderVertices3d(Camera camera, Vector3f s, Vector3f f){
		float[] verts = {			
				s.getX(), s.getY(), s.getZ(),	
				s.getX()+0.1f, s.getY()+0.1f, s.getZ()+0.1f,	
				f.getX(), f.getY(), f.getZ(),	
				f.getX()+0.1f, f.getY()+0.1f, f.getZ()+0.1f,	
				
		};
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		quad = loader.loadToVAO(verts, 3);
		prepare();
		shader.loadModelViewMatrix(viewMatrix);
		shader.load_is3d(true);
		//updateModelViewMatrix(s, 0, 1, viewMatrix);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		finishRendering();
	}*/

	//The code below is for the updateModelViewMatrix() method
	//modelMatrix.m00 = viewMatrix.m00;
	//modelMatrix.m01 = viewMatrix.m10;
	//modelMatrix.m02 = viewMatrix.m20;
	//modelMatrix.m10 = viewMatrix.m01;
	//modelMatrix.m11 = viewMatrix.m11;
	//modelMatrix.m12 = viewMatrix.m21;
	//modelMatrix.m20 = viewMatrix.m02;
	//modelMatrix.m21 = viewMatrix.m12;
	//modelMatrix.m22 = viewMatrix.m22;

	public void cleanUp(){
		shader.cleanUp();
	}
	
	private void updateModelViewMatrix(Vector3f position, float rotation, float scale, Matrix4f viewMatrix){
		Matrix4f modelMatrix = new Matrix4f();
		Matrix4f.translate(position, modelMatrix, modelMatrix);
		modelMatrix.m00 = viewMatrix.m00;
		modelMatrix.m01 = viewMatrix.m10;
		modelMatrix.m02 = viewMatrix.m20;
		modelMatrix.m10 = viewMatrix.m01;
		modelMatrix.m11 = viewMatrix.m11;
		modelMatrix.m12 = viewMatrix.m21;
		modelMatrix.m20 = viewMatrix.m02;
		modelMatrix.m21 = viewMatrix.m12;
		modelMatrix.m22 = viewMatrix.m22;
		Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0,0,1), modelMatrix, modelMatrix);
		Matrix4f.scale(new Vector3f(scale,scale,scale), modelMatrix, modelMatrix);
		Matrix4f x = Matrix4f.mul(viewMatrix, modelMatrix, null);
		shader.loadModelViewMatrix(x);
	}
	
	private void prepare(){
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glDepthMask(false);

	}
	
	private void finishRendering(){
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
		quad = loader.loadToVAO(VERTICES, 2);
	}
/*
	public void render(LinkedList<Particle> particles, Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		prepare();
		for (Particle poly:particles){
			updateModelViewMatrix(poly.getPosition(), poly.getPolyRotation(), poly.getScale(), viewMatrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
			
		}
		finishRendering();
		
	}*/

}
