package renderer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {
	
	private int width;
	private int height;
	private String title;
	private long window;
	private Input eingabe;
	
	public Window(int _w, int _h, String _t) {
		this.width = _w;
		this.height = _h;
		this.title = _t;
	}
	
	public void create() {
		if(!GLFW.glfwInit()) {
			System.out.println("Konnte nicht initialisieren");
		}
		
		this.window = GLFW.glfwCreateWindow(this.width, this.height, this.title, 0, 0);
		this.eingabe = new Input();
		
		if(window == 0) {
			System.out.println("Window nicht erstellt");
		}
		
		
		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		
		
		GLFW.glfwMakeContextCurrent(this.window);
		
		
		GLFW.glfwSetKeyCallback(this.window, this.eingabe.getKeyboardCallback());
		
		
		GLFW.glfwSwapInterval(1); // 60fps
		
	}
	
	public void update() {
		GLFW.glfwPollEvents();
	}
	
	public void swapBuffers() {
		GLFW.glfwSwapBuffers(this.window);
	}
	
	public void destory() {
		this.eingabe.destory();
		GLFW.glfwDestroyWindow(this.window);
		GLFW.glfwWindowShouldClose(this.window);
		GLFW.glfwTerminate();
	}
	
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(this.window);
	}
}
