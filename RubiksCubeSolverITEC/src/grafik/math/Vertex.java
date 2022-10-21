package grafik.math;

public class Vertex {
	
	private double x;
	private double y;
	private double z;
	
	public Vertex(double _x, double _y, double _z) {
		this.x = _x;
		this.y = _y;
		this.z = _z;
	}
	
	public Vertex(Quad a) {
		this.x = a.getQ1();
		this.y = a.getQ2();
		this.z = a.getQ3();
	}
	
}
