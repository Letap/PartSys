package poly3d;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import partsys.Shader;

public class PolyShader extends Shader {

	private static final String VERTEX_FILE = "src/poly/polyVShader.txt";
	private static final String FRAGMENT_FILE = "src/poly/polyFShader.txt";


	private int location_modelViewMatrix;
	private int location_projectionMatrix;
	private int location_is3d;
	private int location_texOffset1;
	private int location_texOffset2;
	private int location_texCoordInfo;

	public PolyShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_modelViewMatrix = super.getUniformLocation("modelViewMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_texOffset1 = super.getUniformLocation("texOffset1");
		location_texOffset2 = super.getUniformLocation("texOffset2");
		location_texCoordInfo = super.getUniformLocation("texCoordInfo");
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
	

	public void loadTextureCoordInfo(Vector2f offset1, Vector2f offset2, float numRows, float blend){
		super.load2DVector(location_texOffset1, offset1);
		super.load2DVector(location_texOffset2, offset2);
		super.load2DVector(location_texCoordInfo, new Vector2f(numRows, blend));
	}
}

