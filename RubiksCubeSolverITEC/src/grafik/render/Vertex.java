package grafik.render;

import grafik.math.Vector3f;

public class Vertex {
	
	private Vector3f pos;
	
	public Vertex(double _x, double _y, double _z) {
		this.pos = new Vector3f(_x, _y, _z);
	}
	
	public Vertex(Vector3f a) {
		this.pos = a;
	}
	
	public Vector3f getPosition() {
		return this.pos;
	}
	
}
