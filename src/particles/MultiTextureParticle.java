package particles;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class MultiTextureParticle extends Particle {
	public int stageCount = texture.getNumberOfRows()*texture.getNumberOfRows();
	public int atlasProg =(int)( Math.random() * stageCount);

	public MultiTextureParticle(TexturedModel model, ParticleTexture texture, Vector3f position, float rotX, float rotY,
			float rotZ, float scale) {
		super(model, texture, position, rotX, rotY, rotZ, scale);
	}
	
	public void updateTextureInfo(){
		
		
		setTextureOffset(texOffset1, atlasProg);
		setTextureOffset(texOffset2, atlasProg);
	}

}
