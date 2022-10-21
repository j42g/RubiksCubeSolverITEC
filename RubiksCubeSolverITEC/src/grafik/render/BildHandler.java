package grafik.render;

import org.lwjgl.glfw.GLFW;

import grafik.math.Vector3f;

public class BildHandler extends Thread {
	
	private Window window;
	private Renderer renderer;
	private final int width = 1280;
	private final int height = 760;
	
	private Mesh m = new Mesh(new Vertex[] {
			new Vertex(new Vector3f(-0.5,  0.5, 0.0)),
			new Vertex(new Vector3f( 0.5,  0.5, 0.0)),
			new Vertex(new Vector3f( 0.5, -0.5, 0.0)),
			new Vertex(new Vector3f(-0.5, -0.5, 0.0))
			
	}, new int[] {
			0, 1, 2,
			0, 3, 2
	});
	
	public void init() {
		this.window = new Window(width, height, "RubiksCube");
		this.renderer = new Renderer();
		m.create();
		window.create();
		
		
		
	}
	
	public void run() {
		this.init();
		while(!window.shouldClose()) {
			this.update();
			this.render();
		}
		window.destory();
	}

	private void render() {
		renderer.renderMesh(this.m);
		window.swapBuffers();
		
	}

	private void update() {
		window.update();
		
	}

}
