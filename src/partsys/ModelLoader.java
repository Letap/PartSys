package partsys;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import models.RawModel;

public class ModelLoader {
	
	private List<Integer> vaoList = new ArrayList<Integer>();
	private List<Integer> vboList = new ArrayList<Integer>();
	private List<Integer> textureList = new ArrayList<Integer>();

	public RawModel loadToVAO(float[] positions, float[] textureCoordinates, float[] normals, int[] indices){
		int vaoID = createVAO();
		bindIndexBuffer(indices);
		storeInAttributeList(0, 3, positions);
		storeInAttributeList(1, 2, textureCoordinates);
		storeInAttributeList(2, 3, normals);
		unbindVAO();
		return new RawModel(vaoID,indices.length);
	}	
	
	public RawModel loadToVAO(float[] positions, int dimensions){
		int vaoID = createVAO();
		this.storeInAttributeList(0, dimensions, positions);
		unbindVAO();
		return new RawModel(vaoID,positions.length/dimensions);
	}	
	
	public int loadTexture(String fileName){
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID();
		return textureID;
	}
	
	public void deleteAll(){
		for(int vao:vaoList){
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo:vboList){
			GL15.glDeleteBuffers(vbo);
		}
		for(int text:textureList){
			GL11.glDeleteTextures(text);
		}
		
	}
	
	private int createVAO(){
		int vaoID = GL30.glGenVertexArrays();
		vaoList.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
		
	}
	
	private void storeInAttributeList(int attributeNumber, int dim, float[] data){
		int vboID = GL15.glGenBuffers();
		vboList.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, dim, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private void unbindVAO(){
		GL30.glBindVertexArray(0);
	}
	
	private void bindIndexBuffer(int[] indices){
		int vboID = GL15.glGenBuffers();
		vboList.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
			
	}
	private FloatBuffer storeDataInFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
}
