package kociembaDarstellung;

import representation.CubieWuerfel;
import representation.Util;

public class Symmetrie {
	
	public static final int ROT_URF3 = 0;
	public static final int ROT_F2 = 1;
	public static final int ROT_U4 = 2;
	public static final int MIRR_LR2 = 3;
	

	public static final int[] cpROT_URF3 = {Ecken.URF, Ecken.DFR, Ecken.DLF, Ecken.UFL, Ecken.UBR, Ecken.DRB, Ecken.DBL, Ecken.ULB};
	public static final int[] coROT_URF3 = {1, 2, 1, 2, 2, 1, 2, 1};
	public static final int[] epROT_URF3 = {Kanten.UF, Kanten.FR, Kanten.DF, Kanten.FL, Kanten.UB, Kanten.BR, Kanten.DB, Kanten.BL, Kanten.UR, Kanten.DR, Kanten.DL, Kanten.UL};
	public static final int[] eoROT_URF3 = {1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1};
	
	public static final int[] cpROT_F2 = {Ecken.DLF, Ecken.DFR, Ecken.DRB, Ecken.DBL, Ecken.UFL, Ecken.URF, Ecken.UBR, Ecken.ULB};
	public static final int[] coROT_F2 = {0, 0, 0, 0, 0, 0, 0, 0};
	public static final int[] epROT_F2 = {Kanten.DL, Kanten.DF, Kanten.DR, Kanten.DB, Kanten.UL, Kanten.UF, Kanten.UR, Kanten.UB, Kanten.FL, Kanten.FR, Kanten.BR, Kanten.BL};
	public static final int[] eoROT_F2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	
	public static final int[] cpROT_U4 = {Ecken.UBR, Ecken.URF, Ecken.UFL, Ecken.ULB, Ecken.DRB, Ecken.DFR, Ecken.DLF, Ecken.DBL};
	public static final int[] coROT_U4 = {0, 0, 0, 0, 0, 0, 0, 0};
	public static final int[] epROT_U4 = {Kanten.UB, Kanten.UR, Kanten.UF, Kanten.UL, Kanten.DB, Kanten.DR, Kanten.DF, Kanten.DL, Kanten.BR, Kanten.FR, Kanten.FL, Kanten.BL};
	public static final int[] eoROT_U4 = {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1};
	
	public static final int[] cpMIRR_LR2 = {Ecken.UFL, Ecken.URF, Ecken.UBR, Ecken.ULB, Ecken.DLF, Ecken.DFR, Ecken.DRB, Ecken.DBL};
	public static final int[] coMIRR_LR2 = {3, 3, 3, 3, 3, 3, 3, 3};
	public static final int[] epMIRR_LR2 = {Kanten.UL, Kanten.UF, Kanten.UR, Kanten.UB, Kanten.DL, Kanten.DF, Kanten.DR, Kanten.DB, Kanten.FL, Kanten.FR, Kanten.BR, Kanten.BL};
	public static final int[] eoMIRR_LR2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	
	
	public static final CubieWuerfel[] grundSym = {
			new CubieWuerfel(cpROT_URF3, coROT_URF3, epROT_URF3, eoROT_URF3),
			new CubieWuerfel(cpROT_F2, coROT_F2, epROT_F2, eoROT_F2),
			new CubieWuerfel(cpROT_U4, coROT_U4, epROT_U4, eoROT_U4),
			new CubieWuerfel(cpMIRR_LR2, coMIRR_LR2, epMIRR_LR2, eoMIRR_LR2),
	};
	
	public static final CubieWuerfel[] alleSym = new CubieWuerfel[48];
	static {
		CubieWuerfel cw = new CubieWuerfel();
		int index = 0;
		for(int urf3 = 0; urf3 < 3; urf3++) {
			for(int f2 = 0; f2 < 2; f2++) {
				for(int u4 = 0; u4 < 4; u4++) {
					for(int lr2 = 0; lr2 < 2; lr2++) {
						alleSym[index] = new CubieWuerfel(cw.getEp(), cw.getEo(), cw.getKp(), cw.getKo());
						index++;
						cw.mul(grundSym[MIRR_LR2]);
					}
					cw.mul(grundSym[ROT_U4]);
				}
				cw.mul(grundSym[ROT_F2]);
			}
			cw.mul(grundSym[ROT_URF3]);
		}
	}
	
	public static int[] inv_idx = new int[Andere.N_SYM];
	static {
		CubieWuerfel cc;
		for(int j = 0; j < Andere.N_SYM; j++) {
			for(int i = 0; i < Andere.N_SYM; i++) {
				cc = new CubieWuerfel(alleSym[j].getEp(), alleSym[j].getEo(), alleSym[j].getKp(),alleSym[j].getKo());
				cc.eckenMul(alleSym[i]);
				if(cc.getEp()[Ecken.URF] == Ecken.URF &&
				   cc.getEp()[Ecken.UFL] == Ecken.UFL &&
				   cc.getEp()[Ecken.ULB] == Ecken.ULB) {
					inv_idx[j] = i;
				}
			}
		}
	}
	
	
	
}
