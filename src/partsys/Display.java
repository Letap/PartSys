package partsys;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import models.RawModel;
import models.TexturedModel;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
 
public class Display {
 
    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback   keyCallback;
 
    // The window handle
    private long window;
 
    public void run() {
        System.out.println("Particle system 0.01");

        try {
            init();
            loop();
 
            // Release window and window callbacks
            glfwDestroyWindow(window);
            keyCallback.release();
        } finally {
            // Terminate GLFW and release the GLFWErrorCallback
            glfwTerminate();
            errorCallback.release();
        }
    }
 
    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        //glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
 
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GL11.GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
 
        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
        //glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3); glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        int WIDTH = 1280;
        int HEIGHT = 720;
 
        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
 
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
            }
        });
 
        // Get the resolution of the primary monitor
       //GLFWvidmode vidmode = glfwGetVideoModes(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
            window,
            (1600 - WIDTH) / 2,
            (900 - HEIGHT) / 2
        );
 
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
 
        // Make the window visible
        glfwShowWindow(window);
    }
 
    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        
        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        

        ModelLoader loader = new ModelLoader();
        ModelRenderer renderer = new ModelRenderer();
        StaticShader shader = new StaticShader();
        
        float[] vertices = {
        		-0.5f, 0.5f, 0f,
        		-0.5f, -0.5f, 0f,
        		0.5f, -0.5f, 0f,
        		0.5f, 0.5f, 0f,
        };
        
        int[] indices = {
        		0, 1, 3, 3, 1, 2
        };
        
        float[] textureCoods={
        	0,0,
        	0,1,
        	1,1,
        	1,0
        };
        

        ModelTexture texture =null;// new ModelTexture(loader.loadTexture("res/image.png"));
        
        RawModel model = loader.loadToVAO(vertices, indices);
        TexturedModel texturedModel = new TexturedModel(model,texture);
 
 
        
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( glfwWindowShouldClose(window) == GL_FALSE ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        	renderer.prepare();
        	shader.start();
        	renderer.render(texturedModel);
        	shader.stop();
            glfwSwapBuffers(window); // swap the color buffers
 
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }

    	loader.deleteAll();
    	shader.cleanUp();
    }
 
    public static void main(String[] args) {
        new Display().run();
        
        
        
        
    }
 
}