package particles;

import org.lwjgl.util.vector.Vector3f;

import models.Entity;
import models.RawModel;
import models.TexturedModel;

public class Particle extends Entity {
	private RawModel model;

	public Particle(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
	
		super(model,position,rotX,rotY,rotZ,scale);
	}
	
	public void updatePosition(){
		
	}
	
	
}
