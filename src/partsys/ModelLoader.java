package partsys;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;


import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import models.RawModel;

public class ModelLoader {
	
	private List<Integer> vaoList = new ArrayList<Integer>();
	private List<Integer> vboList = new ArrayList<Integer>();
	private List<Integer> textureList = new ArrayList<Integer>();

	public RawModel loadToVAO(float[] positions, int[] indices){
		int vaoID = createVAO();
		bindIndexBuffer(indices);
		storeInAttributeList(0, 3, positions);

		unbindVAO();
		return new RawModel(vaoID,indices.length);
	}
	
	public int loadTexture(String fileName){
		ByteBuffer buf = null;
		try {
		    // Open the PNG file as an InputStream
		    InputStream in = new FileInputStream(fileName);
		    // Link the PNG decoder to this stream
		    PNGDecoder decoder = new PNGDecoder(in);
		     
		    // Decode the PNG file in a ByteBuffer
		    buf = ByteBuffer.allocateDirect(
		            4 * decoder.getWidth() * decoder.getHeight());
		    decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
		    buf.flip();
		     
		    in.close();
		} catch (IOException e) {
		    e.printStackTrace();
		    System.exit(-1);
		}
		
		// Create a new texture object in memory and bind it
        int texId = GL11.glGenTextures();
       
        return texId;
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
