package poly;

import org.lwjgl.util.vector.Matrix4f;

import partsys.Shader;

public class PolyShader extends Shader {

	private static final String VERTEX_FILE = "src/poly/polyVShader.txt";
	private static final String FRAGMENT_FILE = "src/poly/polyFShader.txt";


	private int location_modelViewMatrix;
	private int location_projectionMatrix;
	private int location_is3d;

	public PolyShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_modelViewMatrix = super.getUniformLocation("modelViewMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_is3d = super.getUniformLocation("is3d");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	protected void loadModelViewMatrix(Matrix4f modelView) {
		super.loadMatrix(location_modelViewMatrix, modelView);
	}

	protected void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}

	public void load_is3d(boolean is3d) {
		super.loadBoolean(location_is3d, is3d);
	}
	


}

