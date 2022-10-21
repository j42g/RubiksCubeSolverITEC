package grafik.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Renderer {
	public void renderMesh(Mesh m) {
		GL30.glBindVertexArray(m.getVAO());
		GL30.glEnableVertexAttribArray(0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, m.getIBO());
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, m.getIndices().length, GL11.GL_DOUBLE, 0);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	
}
