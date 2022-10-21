package grafik.math;

public class Vector3f {
	private double x;
	private double y;
	private double z;
	
	public Vector3f(double _x, double _y, double _z) {
		this.x = _x;
		this.y = _y;
		this.z = _z;
	}
	
	public Vector3f(Quad a) {
		this.x = a.getQ1();
		this.y = a.getQ2();
		this.z = a.getQ3();
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
}
