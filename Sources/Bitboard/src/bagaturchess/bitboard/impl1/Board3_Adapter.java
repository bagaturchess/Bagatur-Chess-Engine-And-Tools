package bagaturchess.bitboard.impl1;


import bagaturchess.bitboard.api.IBitBoard;
import bagaturchess.bitboard.api.IBoardConfig;
import bagaturchess.bitboard.api.IFieldsAttacks;
import bagaturchess.bitboard.api.IGameStatus;
import bagaturchess.bitboard.api.IInternalMoveList;
import bagaturchess.bitboard.api.IMoveIterator;
import bagaturchess.bitboard.api.IPlayerAttacks;
import bagaturchess.bitboard.api.PawnsEvalCache;
import bagaturchess.bitboard.impl.Constants;
import bagaturchess.bitboard.impl.Fields;
import bagaturchess.bitboard.impl.state.PiecesList;


public class Board3_Adapter extends Board3 implements IBitBoard {
	
	
	public Board3_Adapter(String fenStr, IBoardConfig boardConfig) {
		super(fenStr, boardConfig);
	}
	
	
	public Board3_Adapter(String fenStr, PawnsEvalCache pawnsCache, IBoardConfig boardConfig) {
		super(fenStr, pawnsCache, boardConfig);
	}

	
	public Board3_Adapter() {
		super();
	}


	public Board3_Adapter(String fen) {
		super(fen);
	}


	@Override
	public boolean isPossible(int move) {
		//throw new UnsupportedOperationException();
		return true;
	}
	
	@Override
	public void setPawnsCache(PawnsEvalCache _pawnsCache) {
		pawnsCache = _pawnsCache;
	}
	
	
	@Override
	public boolean isInCheck(int colour) {
		return super.isInCheck(colour);
	}
	
	
	public final long getFiguresBitboardByPID(int pid) {
		
		//if (true) throw new UnsupportedOperationException();
		
		//throw new IllegalStateException();
		
		PiecesList piecesList = pieces.getPieces(pid);
		int size = piecesList.getDataSize();
		int[] ids = piecesList.getData();
		long bitboard = 0L;
		for (int i=0; i<size; i++) {
			int fieldID = ids[i];
			bitboard |= Fields.ALL_A1H1[fieldID];
		}
		return bitboard;
		
	}
	
	
	public long getFiguresBitboardByColourAndType(int colour, int type) {
		
		//if (true) throw new UnsupportedOperationException();
		
		return getFiguresBitboardByPID(Constants.COLOUR_AND_TYPE_2_PIECE_IDENTITY[colour][type]);
	}
	
	
	public long getFiguresBitboardByColour(int colour) {
		
		//if (true) throw new UnsupportedOperationException();
		
		long result = 0L;
		if (colour == Constants.COLOUR_WHITE) {
			result |= getFiguresBitboardByPID(Constants.PID_W_KING);
			result |= getFiguresBitboardByPID(Constants.PID_W_PAWN);
			result |= getFiguresBitboardByPID(Constants.PID_W_BISHOP);
			result |= getFiguresBitboardByPID(Constants.PID_W_KNIGHT);
			result |= getFiguresBitboardByPID(Constants.PID_W_QUEEN);
			result |= getFiguresBitboardByPID(Constants.PID_W_ROOK);
		} else {
			result |= getFiguresBitboardByPID(Constants.PID_B_KING);
			result |= getFiguresBitboardByPID(Constants.PID_B_PAWN);
			result |= getFiguresBitboardByPID(Constants.PID_B_BISHOP);
			result |= getFiguresBitboardByPID(Constants.PID_B_KNIGHT);
			result |= getFiguresBitboardByPID(Constants.PID_B_QUEEN);
			result |= getFiguresBitboardByPID(Constants.PID_B_ROOK);
		}
		return result;
	}
	
	
	public final long getFreeBitboard() {
		long all = getFiguresBitboardByColour(Constants.COLOUR_WHITE) | getFiguresBitboardByColour(Constants.COLOUR_BLACK);
		return ~all;
	}
	
	
	@Override
	public IGameStatus getStatus() {
		//return IGameStatus.NONE;
		throw new UnsupportedOperationException();
	}
	

	@Override
	public IPlayerAttacks getPlayerAttacks(int colour) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IFieldsAttacks getFieldsAttacks() {
		throw new UnsupportedOperationException();
	}


	@Override
	public int genKingEscapes(IInternalMoveList list) {
		throw new UnsupportedOperationException();
	}


	@Override
	public boolean hasSingleMove() {
		throw new UnsupportedOperationException();
	}
	

	@Override
	public boolean isCheckMove(int move) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getAttacksSupport() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getFieldsStateSupport() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAttacksSupport(boolean attacksSupport,
			boolean fieldsStateSupport) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int genAllMoves_ByFigureID(int fieldID, long excludedToFields,
			IInternalMoveList list) {
		throw new UnsupportedOperationException();
	}


	/* (non-Javadoc)
	 * @see bagaturchess.bitboard.api.IBoard#makeMoveForward(java.lang.String)
	 */
	@Override
	public void makeMoveForward(String ucimove) {
		throw new UnsupportedOperationException();
	}


	/* (non-Javadoc)
	 * @see bagaturchess.bitboard.api.IBoard#isCaptureMove(int)
	 */
	@Override
	public boolean isCaptureMove(int move) {
		throw new UnsupportedOperationException();
	}


	/* (non-Javadoc)
	 * @see bagaturchess.bitboard.api.IBoard#isPromotionMove(int)
	 */
	@Override
	public boolean isPromotionMove(int move) {
		throw new UnsupportedOperationException();
	}


	/* (non-Javadoc)
	 * @see bagaturchess.bitboard.api.IBoard#isCaptureOrPromotionMove(int)
	 */
	@Override
	public boolean isCaptureOrPromotionMove(int move) {
		throw new UnsupportedOperationException();
	}


	/* (non-Javadoc)
	 * @see bagaturchess.bitboard.api.IBoard#getSEEScore(int)
	 */
	@Override
	public int getSEEScore(int move) {
		throw new UnsupportedOperationException();
	}


	/* (non-Javadoc)
	 * @see bagaturchess.bitboard.api.IBitBoard#isEnpassantMove(int)
	 */
	@Override
	public boolean isEnpassantMove(int move) {
		throw new UnsupportedOperationException();
	}


	/* (non-Javadoc)
	 * @see bagaturchess.bitboard.api.IBitBoard#isCastlingMove(int)
	 */
	@Override
	public boolean isCastlingMove(int move) {
		throw new UnsupportedOperationException();
	}

}
