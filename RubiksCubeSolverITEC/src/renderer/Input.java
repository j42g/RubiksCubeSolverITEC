package renderer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Input {
	
	private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
	
	private GLFWKeyCallback keyboard;
	
	public Input() {
		this.keyboard = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				keys[key] = (action != GLFW.GLFW_RELEASE);
				
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
