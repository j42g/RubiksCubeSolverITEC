package kociembaDarstellung;

public class Andere {
	/**
	 * Was bedeuten diese Sachen? A: https://github.com/hkociemba/RubiksCube-TwophaseSolver/blob/master/package_src/twophase/defs.py
	 */
	public static final int N_PERM_4 = 24;
	public static final int N_CHOOSE_8_4 = 70;
	public static final int N_MOVE = 18;
	
	public static final int N_TWIST = 2187;
	public static final int N_FLIP = 2048;
	public static final int N_SLICE_SORTED = 11880;
	public static final int N_SLICE = N_SLICE_SORTED / N_PERM_4;
	public static final int N_FLIPSLICE_CLASS = 64430;
	
	public static final int N_U_EDGES_PHASE2 = 1680;
	public static final int N_CORNERS = 40320;
	public static final int N_CORNERS_CLASS = 2768;
	public static final int N_UD_EDGES = 40320;
	
	public static final int N_SYM = 48;
	public static final int N_SYM_D4h = 16;
	public static final String FOLDER = "twophase";
}
