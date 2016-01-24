package models;

import partsys.ModelTexture;

public class TexturedModel {
	private RawModel rawModel;
	private ModelTexture texture;
	
	public TexturedModel() {
		super();
	}
	
	public TexturedModel(RawModel rawModel, ModelTexture texture) {
		super();
		this.rawModel = rawModel;
		this.texture = texture;
	}

	public RawModel getRawModel() {
		return rawModel;
	}

	public ModelTexture getTexture() {
		return texture;
	}
	
	
}
