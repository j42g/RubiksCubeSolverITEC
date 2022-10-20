package kociembaDarstellung;

public class Facelet {
	/**
	 * Facelet Numerierung nach Kociemba
	 */
	public static final int U1 = 0;
	public static final int U2 = 1;
	public static final int U3 = 2;
	public static final int U4 = 3;
	public static final int U5 = 4;
	public static final int U6 = 5;
	public static final int U7 = 6;
	public static final int U8 = 7;
	public static final int U9 = 8;
	public static final int R1 = 9;
	public static final int R2 = 10;
	public static final int R3 = 11;
	public static final int R4 = 12;
	public static final int R5 = 13;
	public static final int R6 = 14;
	public static final int R7 = 15;
	public static final int R8 = 16;
	public static final int R9 = 17;
	public static final int F1 = 18;
	public static final int F2 = 19;
	public static final int F3 = 20;
	public static final int F4 = 21;
	public static final int F5 = 22;
	public static final int F6 = 23;
	public static final int F7 = 24;
	public static final int F8 = 25;
	public static final int F9 = 26;
	public static final int D1 = 27;
	public static final int D2 = 28;
	public static final int D3 = 29;
	public static final int D4 = 30;
	public static final int D5 = 31;
	public static final int D6 = 32;
	public static final int D7 = 33;
	public static final int D8 = 34;
	public static final int D9 = 35;
	public static final int L1 = 36;
	public static final int L2 = 37;
	public static final int L3 = 38;
	public static final int L4 = 39;
	public static final int L5 = 40;
	public static final int L6 = 41;
	public static final int L7 = 42;
	public static final int L8 = 43;
	public static final int L9 = 44;
	public static final int B1 = 45;
	public static final int B2 = 46;
	public static final int B3 = 47;
	public static final int B4 = 48;
	public static final int B5 = 49;
	public static final int B6 = 50;
	public static final int B7 = 51;
	public static final int B8 = 52;
	public static final int B9 = 53;

	public static final int[][] eckenFacelet = { { U9, R1, F3 }, { U7, F1, L3 }, { U1, L1, B3 }, { U3, B1, R3 },
			{ D3, F9, R7 }, { D1, L9, F7 }, { D7, B9, L7 }, { D9, R9, B7 } };
	public static final int[][] kantenFacelet = { { U6, R2 }, { U8, F2 }, { U4, L2 }, { U2, B2 }, { D6, R8 },
			{ D2, F8 }, { D4, L8 }, { D8, B8 }, { F6, R4 }, { F4, L6 }, { B6, L4 }, { B4, R6 } };
	public static final int[][] eckenFarbe = { { Farbe.U, Farbe.R, Farbe.F }, { Farbe.U, Farbe.F, Farbe.L },
			{ Farbe.U, Farbe.L, Farbe.B }, { Farbe.U, Farbe.B, Farbe.R }, { Farbe.D, Farbe.F, Farbe.R },
			{ Farbe.D, Farbe.L, Farbe.F }, { Farbe.D, Farbe.B, Farbe.L }, { Farbe.D, Farbe.R, Farbe.B } };
	public static final int[][] kantenFarbe = { { Farbe.U, Farbe.R }, { Farbe.U, Farbe.F }, { Farbe.U, Farbe.L },
			{ Farbe.U, Farbe.B }, { Farbe.D, Farbe.R }, { Farbe.D, Farbe.F }, { Farbe.D, Farbe.L },
			{ Farbe.D, Farbe.B }, { Farbe.F, Farbe.R }, { Farbe.F, Farbe.L }, { Farbe.B, Farbe.L },
			{ Farbe.B, Farbe.R } };
}
