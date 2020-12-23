/**
 *  BagaturChess (UCI chess engine and tools)
 *  Copyright (C) 2005 Krasimir I. Topchiyski (k_topchiyski@yahoo.com)
 *  
 *  This file is part of BagaturChess program.
 * 
 *  BagaturChess is open software: you can redistribute it and/or modify
 *  it under the terms of the Eclipse Public License version 1.0 as published by
 *  the Eclipse Foundation.
 *
 *  BagaturChess is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  Eclipse Public License for more details.
 *
 *  You should have received a copy of the Eclipse Public License version 1.0
 *  along with BagaturChess. If not, see http://www.eclipse.org/legal/epl-v10.html
 *
 */
package bagaturchess.scanner.utils;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bagaturchess.bitboard.api.IBitBoard;
import bagaturchess.bitboard.impl.Constants;
import bagaturchess.bitboard.impl1.internal.ChessConstants;


public class ScannerUtils {
	
	
	public static void saveImage(String fen, BufferedImage image) {
		try {
			File file = new File("./data/" + (fen + ".jpg").replace('/', '_'));
			ImageIO.write(image, "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static float[] convertToFlatGrayArray(BufferedImage image) {
		
		if (image.getHeight() != image.getWidth()) {
			throw new IllegalStateException();
		}
		
		int count = 0;
		float[] inputs = new float[image.getHeight() * image.getHeight()];
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				
				int rgb = image.getRGB(i, j);
				
				//int alpha = (rgb & 0xff000000) >>> 24;
				int red = (rgb & 0xff0000) >> 16;
				int green = (rgb & 0xff00) >> 8;
				int blue = rgb & 0xff;
			    
				if (red != green || blue != green) {
					throw new IllegalStateException();
				}
				
				//inputs[count] = (red + green + blue) / 3;
			    //inputs[count++] = red * 0.299 + green * 0.587 + blue * 0.114;
				inputs[count++] = green;
			}
		}
		
		return inputs;
	}
	
	
	public static double[] convertToFlatRGBArray(BufferedImage image) {
		
		if (image.getHeight() != image.getWidth()) {
			throw new IllegalStateException();
		}
		
		int count = 0;
		double[] inputs = new double[3 * image.getHeight() * image.getHeight()];
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				
				int rgb = image.getRGB(i, j);
				
				//int alpha = (rgb & 0xff000000) >>> 24;
				int red = (rgb & 0xff0000) >> 16;
				int green = (rgb & 0xff00) >> 8;
				int blue = rgb & 0xff;
				
				inputs[count + 0] = red;
				inputs[count + 1] = green;
				inputs[count + 2] = blue;
				count += 3;
			}
		}
		
		return inputs;
	}
	
	
	public static BufferedImage convertToGrayScale(BufferedImage image) {
	  BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
	  Graphics g = result.getGraphics();
	  g.drawImage(image, 0, 0, null);
	  return result;
	}
	
	
	public static String convertOutputToFEN(float[] actual_output) {
		
		float[] signals = new float[64];
		int[] pids = new int[64];
		for (int i = 0; i < actual_output.length; i++) {
			
			int pid = i / 64;			
			int squareID = i % 64;
			
			if (signals[squareID] <= actual_output[i]) {
				signals[squareID] = actual_output[i];
				pids[squareID] = pid;
			}
		}
		
		/*for (int i = 0; i < signals.length; i++) {
			if (signals[i] <= 0.5) {
				pids[i] = Constants.PID_NONE;
			}
		}*/
		
		
		StringBuilder sb = new StringBuilder();
		for (int i = 63; i >= 0; i--) {
			if (pids[i] >= 1 && pids[i] <= 6) {
				sb.append(ChessConstants.FEN_WHITE_PIECES[Constants.PIECE_IDENTITY_2_TYPE[pids[i]]]);
			} else {
				sb.append(ChessConstants.FEN_BLACK_PIECES[Constants.PIECE_IDENTITY_2_TYPE[pids[i]]]);
			}
			
			if (i % 8 == 0 && i != 0) {
				sb.append("/");
			}
		}
		
		String fen = sb.toString();
		fen = fen.replaceAll("11111111", "8");
		fen = fen.replaceAll("1111111", "7");
		fen = fen.replaceAll("111111", "6");
		fen = fen.replaceAll("11111", "5");
		fen = fen.replaceAll("1111", "4");
		fen = fen.replaceAll("111", "3");
		fen = fen.replaceAll("11", "2");
		
		return fen;
	}
	
	
	public static float[] createOutputArray(IBitBoard bitboard) {
		
		float[] result = new float[64 * 13];
		{
			long bb_w_king = bitboard.getFiguresBitboardByColourAndType(Constants.COLOUR_WHITE, Constants.TYPE_KING);
			long bb_w_queens = bitboard.getFiguresBitboardByColourAndType(Constants.COLOUR_WHITE, Constants.TYPE_QUEEN);
			long bb_w_rooks = bitboard.getFiguresBitboardByColourAndType(Constants.COLOUR_WHITE, Constants.TYPE_ROOK);
			long bb_w_bishops = bitboard.getFiguresBitboardByColourAndType(Constants.COLOUR_WHITE, Constants.TYPE_BISHOP);
			long bb_w_knights = bitboard.getFiguresBitboardByColourAndType(Constants.COLOUR_WHITE, Constants.TYPE_KNIGHT);
			long bb_w_pawns = bitboard.getFiguresBitboardByColourAndType(Constants.COLOUR_WHITE, Constants.TYPE_PAWN);
			
			int squareID_w_king = Long.numberOfTrailingZeros(bb_w_king);
			result[64 * Constants.PID_W_KING + squareID_w_king] = 1;
			
	        while (bb_w_pawns != 0) {
	        	int squareID_pawn = Long.numberOfTrailingZeros(bb_w_pawns);
	        	result[64 * Constants.PID_W_PAWN + squareID_pawn] = 1;
	        	bb_w_pawns &= bb_w_pawns - 1;
	        }
	        
	        while (bb_w_knights != 0) {
	        	int squareID_knight = Long.numberOfTrailingZeros(bb_w_knights);
	        	result[64 * Constants.PID_W_KNIGHT + squareID_knight] = 1;
	        	bb_w_knights &= bb_w_knights - 1;
	        }
	        
	        while (bb_w_bishops != 0) {
	        	int squareID_bishop = Long.numberOfTrailingZeros(bb_w_bishops);
	        	result[64 * Constants.PID_W_BISHOP + squareID_bishop] = 1;
	        	bb_w_bishops &= bb_w_bishops - 1;
	        }
	        
	        while (bb_w_rooks != 0) {
	        	int squareID_rook = Long.numberOfTrailingZeros(bb_w_rooks);
	        	result[64 * Constants.PID_W_ROOK + squareID_rook] = 1;
	        	bb_w_rooks &= bb_w_rooks - 1;
	        }
	        
	        while (bb_w_queens != 0) {
	        	int squareID_queen = Long.numberOfTrailingZeros(bb_w_queens);
	        	result[64 * Constants.PID_W_QUEEN + squareID_queen] = 1;
	        	bb_w_queens &= bb_w_queens - 1;
	        }
		}
        
		{
			long bb_b_king = bitboard.getFiguresBitboardByColourAndType(Constants.COLOUR_BLACK, Constants.TYPE_KING);
			long bb_b_queens = bitboard.getFiguresBitboardByColourAndType(Constants.COLOUR_BLACK, Constants.TYPE_QUEEN);
			long bb_b_rooks = bitboard.getFiguresBitboardByColourAndType(Constants.COLOUR_BLACK, Constants.TYPE_ROOK);
			long bb_b_bishops = bitboard.getFiguresBitboardByColourAndType(Constants.COLOUR_BLACK, Constants.TYPE_BISHOP);
			long bb_b_knights = bitboard.getFiguresBitboardByColourAndType(Constants.COLOUR_BLACK, Constants.TYPE_KNIGHT);
			long bb_b_pawns = bitboard.getFiguresBitboardByColourAndType(Constants.COLOUR_BLACK, Constants.TYPE_PAWN);
			
			int squareID_b_king = Long.numberOfTrailingZeros(bb_b_king);
			result[64 * Constants.PID_B_KING + squareID_b_king] = 1;
			
	        while (bb_b_pawns != 0) {
	        	int squareID_pawn = Long.numberOfTrailingZeros(bb_b_pawns);
	        	result[64 * Constants.PID_B_PAWN + squareID_pawn] = 1;
	        	bb_b_pawns &= bb_b_pawns - 1;
	        }
	        
	        while (bb_b_knights != 0) {
	        	int squareID_knight = Long.numberOfTrailingZeros(bb_b_knights);
	        	result[64 * Constants.PID_B_KNIGHT + squareID_knight] = 1;
	        	bb_b_knights &= bb_b_knights - 1;
	        }
	        
	        while (bb_b_bishops != 0) {
	        	int squareID_bishop = Long.numberOfTrailingZeros(bb_b_bishops);
	        	result[64 * Constants.PID_B_BISHOP + squareID_bishop] = 1;
	        	bb_b_bishops &= bb_b_bishops - 1;
	        }
	        
	        while (bb_b_rooks != 0) {
	        	int squareID_rook = Long.numberOfTrailingZeros(bb_b_rooks);
	        	result[64 * Constants.PID_B_ROOK + squareID_rook] = 1;
	        	bb_b_rooks &= bb_b_rooks - 1;
	        }
	        
	        while (bb_b_queens != 0) {
	        	int squareID_queen = Long.numberOfTrailingZeros(bb_b_queens);
	        	result[64 * Constants.PID_B_QUEEN + squareID_queen] = 1;
	        	bb_b_queens &= bb_b_queens - 1;
	        }
		}
        
		long free = bitboard.getFreeBitboard();
        while (free != 0) {
        	int squareID_free = Long.numberOfTrailingZeros(free);
        	result[squareID_free] = 1;
        	free &= free - 1;
        }
		
		return result;
	}
}