package bagaturchess.learning.impl.eval.cfg;


public interface Weights_HU {
	public static final double MATERIAL_PAWN_O	=	56.44973408856641;
	public static final double MATERIAL_PAWN_E	=	83.57130098872405;
	public static final double MATERIAL_KNIGHT_O	=	364.00819752053224;
	public static final double MATERIAL_KNIGHT_E	=	291.6175941096139;
	public static final double MATERIAL_BISHOP_O	=	378.14035800028466;
	public static final double MATERIAL_BISHOP_E	=	306.05503337071997;
	public static final double MATERIAL_ROOK_O	=	511.71007063760817;
	public static final double MATERIAL_ROOK_E	=	522.3768395895304;
	public static final double MATERIAL_QUEEN_O	=	1257.3479991737888;
	public static final double MATERIAL_QUEEN_E	=	951.6595239862845;
	public static final double KINGSAFE_CASTLING_O	=	7.3469778961188705;
	public static final double KINGSAFE_CASTLING_E	=	0.0;
	public static final double KINGSAFE_FIANCHETTO_O	=	0.6814782400329077;
	public static final double KINGSAFE_FIANCHETTO_E	=	0.0;
	public static final double BISHOPS_DOUBLE_O	=	36.84804933504998;
	public static final double BISHOPS_DOUBLE_E	=	38.905106984330565;
	public static final double KNIGHTS_DOUBLE_O	=	-0.785183226120556;
	public static final double KNIGHTS_DOUBLE_E	=	-0.8320702501502143;
	public static final double ROOKS_DOUBLE_O	=	-0.9952503533862255;
	public static final double ROOKS_DOUBLE_E	=	-2.6209061957924606;
	public static final double PAWNS5_ROOKS_O	=	-1.0157810379417505;
	public static final double PAWNS5_ROOKS_E	=	-1.3722205676597283;
	public static final double PAWNS5_KNIGHTS_O	=	0.9648046546274403;
	public static final double PAWNS5_KNIGHTS_E	=	2.5350363406759033;
	public static final double KINGSAFE_F_O	=	-3.8897265080391397;
	public static final double KINGSAFE_F_E	=	0.0;
	public static final double KINGSAFE_G_O	=	-7.987851517804452;
	public static final double KINGSAFE_G_E	=	0.0;
	public static final double KINGS_DISTANCE_O	=	0.0;
	public static final double KINGS_DISTANCE_E	=	1.4502273243593269;
	public static final double PAWNS_DOUBLED_O	=	-0.09993647748613359;
	public static final double PAWNS_DOUBLED_E	=	-7.649478841792179;
	public static final double PAWNS_ISOLATED_O	=	-13.007591184937034;
	public static final double PAWNS_ISOLATED_E	=	-12.057819565745506;
	public static final double PAWNS_BACKWARD_O	=	-2.2311646881394274;
	public static final double PAWNS_BACKWARD_E	=	-2.495235351985101;
	public static final double PAWNS_SUPPORTED_O	=	0.986462137817011;
	public static final double PAWNS_SUPPORTED_E	=	3.0017932359372406;
	public static final double PAWNS_CANNOTBS_O	=	-0.20463784444112815;
	public static final double PAWNS_CANNOTBS_E	=	-0.3741330212102341;
	public static final double PAWNS_PASSED_O	=	7.262895174397539;
	public static final double PAWNS_PASSED_E	=	2.799725867443788;
	public static final double PAWNS_PASSED_RNK_O	=	1.0007393912883675;
	public static final double PAWNS_PASSED_RNK_E	=	0.9792478472957556;
	public static final double PAWNS_UNSTOPPABLE_PASSER_O	=	0.0;
	public static final double PAWNS_UNSTOPPABLE_PASSER_E	=	550.0;
	public static final double PAWNS_CANDIDATE_RNK_O	=	1.0868983980394418;
	public static final double PAWNS_CANDIDATE_RNK_E	=	0.8934080713097304;
	public static final double KINGS_PASSERS_F_O	=	0.0;
	public static final double KINGS_PASSERS_F_E	=	0.4883404430735569;
	public static final double KINGS_PASSERS_FF_O	=	0.0;
	public static final double KINGS_PASSERS_FF_E	=	0.5788728937648292;
	public static final double KINGS_PASSERS_F_OP_O	=	0.0;
	public static final double KINGS_PASSERS_F_OP_E	=	2.0768405057602037;
	public static final double PAWNS_ISLANDS_O	=	-0.5763183001702917;
	public static final double PAWNS_ISLANDS_E	=	-0.8691307231564851;
	public static final double PAWNS_GARDS_O	=	7.623383578781562;
	public static final double PAWNS_GARDS_E	=	0.0;
	public static final double PAWNS_GARDS_REM_O	=	-6.112972231642286;
	public static final double PAWNS_GARDS_REM_E	=	0.0;
	public static final double PAWNS_STORMS_O	=	2.9482551398340417;
	public static final double PAWNS_STORMS_E	=	0.0;
	public static final double PAWNS_STORMS_CLS_O	=	1.0231688405519292;
	public static final double PAWNS_STORMS_CLS_E	=	0.0;
	public static final double PAWNS_OPENNED_O	=	-39.22564720589503;
	public static final double PAWNS_OPENNED_E	=	0.0;
	public static final double PAWNS_SEMIOP_OWN_O	=	-27.931278437437882;
	public static final double PAWNS_SEMIOP_OWN_E	=	0.0;
	public static final double PAWNS_SEMIOP_OP_O	=	-9.54717200615522;
	public static final double PAWNS_SEMIOP_OP_E	=	0.0;
	public static final double PAWNS_WEAK_O	=	-1.2880828519666307;
	public static final double PAWNS_WEAK_E	=	-0.5358720346631676;
	public static final double SPACE_O	=	0.20384650346239383;
	public static final double SPACE_E	=	0.842413652580043;
	public static final double ROOK_INFRONT_PASSER_O	=	-0.11901248846421073;
	public static final double ROOK_INFRONT_PASSER_E	=	-0.15424078288105242;
	public static final double ROOK_BEHIND_PASSER_O	=	0.37837502275151885;
	public static final double ROOK_BEHIND_PASSER_E	=	1.127056950940148;
	public static final double PST_PAWN_O	=	0.5782811324753105;
	public static final double PST_PAWN_E	=	1.0827668075880188;
	public static final double PST_KING_O	=	0.8752316247399556;
	public static final double PST_KING_E	=	0.9967958872800357;
	public static final double PST_KNIGHTS_O	=	0.8029463157366066;
	public static final double PST_KNIGHTS_E	=	0.7841486563168014;
	public static final double PST_BISHOPS_O	=	0.7680453520698324;
	public static final double PST_BISHOPS_E	=	0.7048904911060885;
	public static final double PST_ROOKS_O	=	0.7829788533485446;
	public static final double PST_ROOKS_E	=	0.9544940572257793;
	public static final double PST_QUEENS_O	=	0.4458557666532838;
	public static final double PST_QUEENS_E	=	0.7740228858283147;
	public static final double BISHOPS_BAD_O	=	-1.0951723167004184;
	public static final double BISHOPS_BAD_E	=	-0.9825532876919625;
	public static final double KNIGHT_OUTPOST_O	=	10.93360969817998;
	public static final double KNIGHT_OUTPOST_E	=	0.6821503562120068;
	public static final double ROOKS_OPENED_O	=	24.753456921644624;
	public static final double ROOKS_OPENED_E	=	1.1008269509731772;
	public static final double ROOKS_SEMIOPENED_O	=	8.883227156865166;
	public static final double ROOKS_SEMIOPENED_E	=	0.5897022633139662;
	public static final double TROPISM_KNIGHT_O	=	0.07080542492536195;
	public static final double TROPISM_KNIGHT_E	=	0.0;
	public static final double TROPISM_BISHOP_O	=	0.2508060956586847;
	public static final double TROPISM_BISHOP_E	=	0.0;
	public static final double TROPISM_ROOK_O	=	0.13974419285714312;
	public static final double TROPISM_ROOK_E	=	0.0;
	public static final double TROPISM_QUEEN_O	=	0.10287591809192269;
	public static final double TROPISM_QUEEN_E	=	0.0;
	public static final double ROOKS_7TH_2TH_O	=	6.910796868987545;
	public static final double ROOKS_7TH_2TH_E	=	14.290008360374543;
	public static final double QUEENS_7TH_2TH_O	=	1.0118299221291718;
	public static final double QUEENS_7TH_2TH_E	=	6.967352885011109;
	public static final double KINGSAFETY_L1_O	=	17.663264604922457;
	public static final double KINGSAFETY_L1_E	=	0.0;
	public static final double KINGSAFETY_L2_O	=	2.7399984691996697;
	public static final double KINGSAFETY_L2_E	=	0.0;
	public static final double MOBILITY_KNIGHT_O	=	0.6985721518863736;
	public static final double MOBILITY_KNIGHT_E	=	0.7553847409474602;
	public static final double MOBILITY_BISHOP_O	=	0.6393343508849094;
	public static final double MOBILITY_BISHOP_E	=	0.7767883038730965;
	public static final double MOBILITY_ROOK_O	=	0.2854798022247948;
	public static final double MOBILITY_ROOK_E	=	0.7248693591747174;
	public static final double MOBILITY_QUEEN_O	=	0.08022377798578681;
	public static final double MOBILITY_QUEEN_E	=	0.7851378001339605;
	public static final double MOBILITY_KNIGHT_S_O	=	0.31516470134641705;
	public static final double MOBILITY_KNIGHT_S_E	=	0.6017880902511076;
	public static final double MOBILITY_BISHOP_S_O	=	0.511927303213927;
	public static final double MOBILITY_BISHOP_S_E	=	0.6729043384529192;
	public static final double MOBILITY_ROOK_S_O	=	0.36814853992287216;
	public static final double MOBILITY_ROOK_S_E	=	0.7320413987509187;
	public static final double MOBILITY_QUEEN_S_O	=	0.16040245730464675;
	public static final double MOBILITY_QUEEN_S_E	=	0.7548818147733412;
	public static final double PENETRATION_OP_O	=	0.09912991024390941;
	public static final double PENETRATION_OP_E	=	0.0;
	public static final double PENETRATION_OP_S_O	=	0.14355406486729527;
	public static final double PENETRATION_OP_S_E	=	0.0;
	public static final double PENETRATION_KING_O	=	0.1149365377159596;
	public static final double PENETRATION_KING_E	=	0.0;
	public static final double PENETRATION_KING_S_O	=	0.1731718831253475;
	public static final double PENETRATION_KING_S_E	=	0.0;
	public static final double ROOKS_PAIR_H_O	=	2.9139856987864756;
	public static final double ROOKS_PAIR_H_E	=	0.9793356207830123;
	public static final double ROOKS_PAIR_V_O	=	2.9214100296917374;
	public static final double ROOKS_PAIR_V_E	=	0.553862008006067;
	public static final double TRAP_KNIGHT_O	=	-0.286431343235483;
	public static final double TRAP_KNIGHT_E	=	-0.40450101738809724;
	public static final double TRAP_BISHOP_O	=	-0.477108253621181;
	public static final double TRAP_BISHOP_E	=	-0.5499089604632473;
	public static final double TRAP_ROOK_O	=	-0.28158021597145333;
	public static final double TRAP_ROOK_E	=	-0.516137265675638;
	public static final double TRAP_QUEEN_O	=	-0.010857781423011737;
	public static final double TRAP_QUEEN_E	=	-0.0474515036831806;
	public static final double PIN_BK_O	=	4.28739170701259;
	public static final double PIN_BK_E	=	5.196224136771332;
	public static final double PIN_BQ_O	=	0.738837536807657;
	public static final double PIN_BQ_E	=	1.2062826228393517;
	public static final double PIN_BR_O	=	1.5043939511322846;
	public static final double PIN_BR_E	=	1.971584736366976;
	public static final double PIN_BN_O	=	5.762435810247143;
	public static final double PIN_BN_E	=	2.258389089591065;
	public static final double PIN_RK_O	=	8.186395155777781;
	public static final double PIN_RK_E	=	4.91160690091452;
	public static final double PIN_RQ_O	=	2.0116487176533995;
	public static final double PIN_RQ_E	=	3.2254460157605735;
	public static final double PIN_RB_O	=	1.6162930753718159;
	public static final double PIN_RB_E	=	2.1790880015215572;
	public static final double PIN_RN_O	=	2.0960987827776263;
	public static final double PIN_RN_E	=	2.2039887882416083;
	public static final double PIN_QK_O	=	0.6672317969503426;
	public static final double PIN_QK_E	=	6.305269286633338;
	public static final double PIN_QQ_O	=	0.5070536003639032;
	public static final double PIN_QQ_E	=	5.425284910038383;
	public static final double PIN_QN_O	=	0.5330674107356745;
	public static final double PIN_QN_E	=	7.724009571133603;
	public static final double PIN_QR_O	=	0.20990188172429736;
	public static final double PIN_QR_E	=	6.114961696887928;
	public static final double PIN_QB_O	=	0.336798490930937;
	public static final double PIN_QB_E	=	7.893354491876309;
	public static final double ATTACK_BN_O	=	4.053203826932588;
	public static final double ATTACK_BN_E	=	4.049858886406102;
	public static final double ATTACK_BR_O	=	2.4036580882694722;
	public static final double ATTACK_BR_E	=	1.150943168284192;
	public static final double ATTACK_NB_O	=	5.474433422778637;
	public static final double ATTACK_NB_E	=	3.905054087079023;
	public static final double ATTACK_NR_O	=	3.509743479403009;
	public static final double ATTACK_NR_E	=	1.9349135874361623;
	public static final double ATTACK_NQ_O	=	3.356591140171485;
	public static final double ATTACK_NQ_E	=	0.5333530495421036;
	public static final double ATTACK_RB_O	=	3.323146985201511;
	public static final double ATTACK_RB_E	=	4.235861682219051;
	public static final double ATTACK_RN_O	=	2.9482433659629286;
	public static final double ATTACK_RN_E	=	3.844319984051265;
	public static final double ATTACK_QN_O	=	0.5519440003361276;
	public static final double ATTACK_QN_E	=	7.594290284141281;
	public static final double ATTACK_QB_O	=	0.8873742657576866;
	public static final double ATTACK_QB_E	=	5.760285080577084;
	public static final double ATTACK_QR_O	=	1.334973998946818;
	public static final double ATTACK_QR_E	=	5.609965787485958;
	public static final double HUNGED_PIECE_1_O	=	-1.3617673476333958;
	public static final double HUNGED_PIECE_1_E	=	-2.3528337654231466;
	public static final double HUNGED_PIECE_2_O	=	-5.570023325495654;
	public static final double HUNGED_PIECE_2_E	=	-7.941731456877587;
	public static final double HUNGED_PIECE_3_O	=	-7.592203189968395;
	public static final double HUNGED_PIECE_3_E	=	-6.725042612357443;
	public static final double HUNGED_PIECE_4_O	=	-3.1162810704913744;
	public static final double HUNGED_PIECE_4_E	=	-2.6537793991343444;
	public static final double HUNGED_PAWNS_1_O	=	-0.4416455919583633;
	public static final double HUNGED_PAWNS_1_E	=	-4.97523308648499;
	public static final double HUNGED_PAWNS_2_O	=	-1.9194493527406091;
	public static final double HUNGED_PAWNS_2_E	=	-6.824414561017303;
	public static final double HUNGED_PAWNS_3_O	=	-2.445561110000899;
	public static final double HUNGED_PAWNS_3_E	=	-2.8577148723143697;
	public static final double HUNGED_PAWNS_4_O	=	-0.28048355603186653;
	public static final double HUNGED_PAWNS_4_E	=	-3.140258846893514;
	public static final double HUNGED_ALL_1_O	=	-0.8214499369054178;
	public static final double HUNGED_ALL_1_E	=	-3.9476131092236604;
	public static final double HUNGED_ALL_2_O	=	-1.7514748078441746;
	public static final double HUNGED_ALL_2_E	=	-6.020628094421225;
	public static final double HUNGED_ALL_3_O	=	-4.446042931306031;
	public static final double HUNGED_ALL_3_E	=	-3.895291485108971;
	public static final double HUNGED_ALL_4_O	=	-2.725553309556043;
	public static final double HUNGED_ALL_4_E	=	-1.5422159724786106;
}
