package renderer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Input {
	
	private GLFWKeyCallback keyboard;
	
	public Input() {
		this.keyboard = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				

				if(GLFW.GLFW_KEY_U == key && action == GLFW.GLFW_PRESS) {
					System.out.println("U-Zug");
				} else if(GLFW.GLFW_KEY_R == key && action == GLFW.GLFW_PRESS) {
					System.out.println("R-Zug");
				} else if(GLFW.GLFW_KEY_F == key && action == GLFW.GLFW_PRESS) {
					System.out.println("F-Zug");
				} else if(GLFW.GLFW_KEY_D == key && action == GLFW.GLFW_PRESS) {
					System.out.println("D-Zug");
				} else if(GLFW.GLFW_KEY_L == key && action == GLFW.GLFW_PRESS) {
					System.out.println("L-Zug");
				} else if(GLFW.GLFW_KEY_B == key && action == GLFW.GLFW_PRESS) {
					System.out.println("B-Zug");
				}
				
				
			}
		};
			
		
	}
	
	
	
	public void destory() {
		this.keyboard.free();
	}
	
	public GLFWKeyCallback getKeyboardCallback() {
		return keyboard;
	}

}
