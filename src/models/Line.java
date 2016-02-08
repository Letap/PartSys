package models;

import org.lwjgl.util.vector.Vector3f;

import partsys.ModelLoader;
import partsys.ModelTexture;

public class Line{
	public TexturedModel model;
	public Vector3f s,f;
	public ModelLoader loader;

	public Line(Vector3f start, Vector3f finish, ModelTexture texture, ModelLoader loader) {
		s=start; f=finish;
		this.loader = loader;
		model= new TexturedModel(makeModel(), texture);
	}
	
	private RawModel makeModel(){
		float[] vertices = {			
				s.getX(), s.getY(), s.getZ(),	
				s.getX()+0.02f, s.getY()+0.02f, s.getZ()+0.02f,	
				f.getX(), f.getY(), f.getZ(),	
				f.getX()+0.02f, f.getY()+0.02f, f.getZ()+0.02f,	
				
		};
		
		float[] textureCoords = {
				
				0,0,
				0,1,
				1,1,
				1,0,
		};
		
		int[] indices = {
				0,1,2,
				3,1,2
		};
		
		float[] normals = {
				0,0,1,
				0,0,1,
				0,0,1,
				0,0,1,

				0,0,1,
				0,0,1,
				0,0,1,
				0,0,1,

				0,0,1,
				0,0,1,
				0,0,1,
				0,0,1,

				0,0,1,
				0,0,1,
				0,0,1,
				0,0,1,

				0,0,1,
				0,0,1,
				0,0,1,
				0,0,1,

				0,0,1,
				0,0,1,
				0,0,1,
				0,0,1,
				
		};
		
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}

	

}
