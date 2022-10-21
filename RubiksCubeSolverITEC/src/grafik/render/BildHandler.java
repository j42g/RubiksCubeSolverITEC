package grafik.render;

import org.lwjgl.glfw.GLFW;

public class BildHandler extends Thread {
	
	private Window window;
	private final int width = 1280;
	private final int height = 760;
	
	public void init() {
		this.window = new Window(width, height, "RubiksCube");
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
		window.swapBuffers();
		
	}

	private void update() {
		window.update();
		
	}

}
