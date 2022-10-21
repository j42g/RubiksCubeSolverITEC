package graphic.math;


public class Quad {
	private double q0;
	private double q1;
	private double q2;
	private double q3;

	public Quad(double _q0, double _q1, double _q2, double _q3) {
		this.q0 = _q0;
		this.q1 = _q1;
		this.q2 = _q2;
		this.q3 = _q3;
	}

	public Quad mulR(Quad o) {
		double nq0 = this.q0 * o.q0 - this.q1 * o.q1 - this.q2 * o.q2 - this.q3 * o.q3;
		double nq1 = this.q0 * o.q1 + this.q1 * o.q0 + this.q2 * o.q3 - this.q3 * o.q2;
		double nq2 = this.q0 * o.q2 - this.q1 * o.q3 + this.q2 * o.q0 + this.q3 * o.q1;
		double nq3 = this.q0 * o.q3 + this.q1 * o.q2 - this.q2 * o.q1 + this.q3 * o.q0;
		return new Quad(nq0, nq1, nq2, nq3);
	}

	public void mul(Quad o) {
		double nq0 = this.q0 * o.q0 - this.q1 * o.q1 - this.q2 * o.q2 - this.q3 * o.q3;
		double nq1 = this.q0 * o.q1 + this.q1 * o.q0 + this.q2 * o.q3 - this.q3 * o.q2;
		double nq2 = this.q0 * o.q2 - this.q1 * o.q3 + this.q2 * o.q0 + this.q3 * o.q1;
		double nq3 = this.q0 * o.q3 + this.q1 * o.q2 - this.q2 * o.q1 + this.q3 * o.q0;
		this.q0 = nq0;
		this.q1 = nq1;
		this.q2 = nq2;
		this.q3 = nq3;
	}

	public Quad conjugateR() {
		return new Quad(this.q0, -this.q1, -this.q2, -this.q3);
	}

	public void conjugate() {
		this.q1 = - this.q1;
		this.q2 = - this.q2;
		this.q3 = - this.q3;
	}

	public String toString() {
		return this.q0 + " + " + this.q1 + "i + " + this.q2 + "j + " + this.q3 + "k";
	}

	public void print() {
		System.out.println(this.toString());
	}

	public double getQ0() {
		return q0;
	}

	public double getQ1() {
		return q1;
	}

	public double getQ2() {
		return q2;
	}

	public double getQ3() {
		return q3;
	}

}
