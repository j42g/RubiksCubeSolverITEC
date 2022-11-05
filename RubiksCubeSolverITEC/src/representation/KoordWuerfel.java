package representation;

public class KoordWuerfel {
	
	private final static int SOLVED = 0; // auﬂer u_edges
	private int twist;
	private int flip;
	private int slice_sorted;
	private int u_edges;
	private int d_edges;
	private int corners;
	private int ud_edges;

	public KoordWuerfel(){
		this.twist = SOLVED;
		this.slice_sorted = SOLVED;
		this.flip = SOLVED;
		this.u_edges = 1656; // SOLVED
		this.d_edges = SOLVED;
		this.corners = SOLVED;
		this.u_edges = SOLVED;
	}

	public KoordWuerfel(CubieWuerfel cc){
		this.twist = cc.getTwist();
		/*this.flip = cc.get_flip();
		this.slice_sorted = cc.get_slice_sorted();
		this.u_edges = cc.get_u_edges();
		this.d_edges = cc.get_d_edges();
		this.corners = cc.get_corners();*/
	}
	
}
