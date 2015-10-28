package partsys;

public class StaticShader extends Shader {
	
	private static final String VERTEX_FILE = "src/partsys/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/partsys/fragmentShader.txt";
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

}
