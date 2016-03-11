/*
 *  BagaturChess (UCI chess engine and tools)
 *  Copyright (C) 2005 Krasimir I. Topchiyski (k_topchiyski@yahoo.com)
 *  
 *  Open Source project location: http://sourceforge.net/projects/bagaturchess/develop
 *  SVN repository https://bagaturchess.svn.sourceforge.net/svnroot/bagaturchess
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
 *  along with BagaturChess. If not, see <http://www.eclipse.org/legal/epl-v10.html/>.
 *
 */
package bagaturchess.uci.impl;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import bagaturchess.bitboard.api.IBitBoard;
import bagaturchess.bitboard.impl.Board;
import bagaturchess.bitboard.impl.BoardUtils;
import bagaturchess.bitboard.impl.Figures;
import bagaturchess.bitboard.impl.movegen.MoveInt;
import bagaturchess.uci.api.BestMoveSender;
import bagaturchess.uci.api.IUCIConfig;
import bagaturchess.uci.api.IUCISearchAdaptor;
import bagaturchess.uci.api.UCISearchAdaptorFactory;
import bagaturchess.uci.impl.commands.Go;
import bagaturchess.uci.impl.commands.Position;
import bagaturchess.uci.impl.commands.options.SetOption;


public class StateManager extends Protocol implements BestMoveSender {
	
	
	protected IUCIConfig engineBootCfg;
	private Channel channel;
	private IBitBoard board;
	
	private IUCISearchAdaptor searchAdaptor;
	
	private OptionsManager optionsManager;
	
	
	public StateManager(IUCIConfig _engineBootCfg) {
		engineBootCfg = _engineBootCfg;
		board = new Board();
	}
	
	
	public void setOptionsManager(OptionsManager om) {
		optionsManager = om;
	}
	

	public void setChannel(Channel _channel) {
		channel = _channel;
	}
	
	
	public void recreateSearchAdaptor() throws FileNotFoundException {
		searchAdaptor = null;
		System.gc();
		searchAdaptor = UCISearchAdaptorFactory.newUCISearchAdaptor(engineBootCfg, board);
	}
	
	
	//"single file", "multiple files", "none"
	public void initLogging() throws FileNotFoundException {
		if ("single file".equals(engineBootCfg.getUCIAdaptor_LoggingPolicy())) {
			channel.setPrintStream_1File();
		} else if ("multiple files".equals(engineBootCfg.getUCIAdaptor_LoggingPolicy())) {
			channel.setPrintStream_MFiles();
		} else if ("none".equals(engineBootCfg.getUCIAdaptor_LoggingPolicy())) {
			channel.setPrintStream_None();
		} else {
			throw new IllegalStateException("Wrong logging option: '" + engineBootCfg.getUCIAdaptor_LoggingPolicy() + "'");
		}
	}
	
	
	public void communicate() throws Exception {
		
		sendHello();
			
		while (true) {
			try {
				String fromGUILine = channel.receiveCommandFromGUI();
				String fromGUICommand = getFromGUICommand(fromGUILine);
				int fromGUICommandID = getToEngineCommandID(fromGUICommand);
				if (fromGUICommandID == Protocol.COMMAND_UNDEFINED) {
					channel.sendLogToGUI("Command " + fromGUICommand + " UNSUPPORTED");
					//Thread.sleep(100);
				} else {
					channel.sendLogToGUI("exec command " + fromGUICommandID + " > " + fromGUICommand);
					switch (fromGUICommandID) {
						case COMMAND_TO_ENGINE_UCI:
							sendEngineID();
							sendOptions();
							sendUCIOK();
							break;
						case COMMAND_TO_ENGINE_ISREADY:
							sendReadyOK();
							break;
						case COMMAND_TO_ENGINE_NEWGAME:
							createNewGame();
							break;
						case COMMAND_TO_ENGINE_POSITION:
							setupBoard(fromGUILine);
							break;
						case COMMAND_TO_ENGINE_GO:
							goSearch(fromGUILine);
							break;
						case COMMAND_TO_ENGINE_PONDERHIT:
							ponderHit(fromGUILine);
							break;
						case COMMAND_TO_ENGINE_SETOPTION:
							setOption(fromGUILine);
							break;
						case COMMAND_TO_ENGINE_STOP:
							sendBestMove();
							break;
						case COMMAND_TO_ENGINE_QUIT:
							System.exit(0);
							break;
						default:
							throw new IllegalStateException();
					}
				}
			
				sendNewline();
				
			} catch(Throwable e) {
				channel.dump(e);
				channel.sendLogToGUI("Error: " + e.getMessage());
				Thread.sleep(500);
			}
		}
	}


	private String getFromGUICommand(String fromGUILine) {
		String command = fromGUILine;
		if (command != null) {
			int index = fromGUILine.indexOf(' ');
			if (index != -1) {
				command = fromGUILine.substring(0, index);
			}
		}
		return command;
	}
	
	
	private void sendHello() throws IOException {
		String result = "\r\n\r\n";
		result += "***************************************************************************";
		result += "\r\n";
		result += "* Copyright (C) 2005-2011 Krasimir I. Topchiyski (k_topchiyski@yahoo.com) *";
		result += "\r\n";
		result += "*                                                                         *";
		result += "\r\n";
		result += "* Welcome to Bagatur UCI engine, version " + COMMAND_TO_GUI_ID_VERSION_STR + "                              *";
		result += "\r\n";
		result += "*                                                                         *";
		result += "\r\n";
		result += "* For help, have a look at the UCI protocol definition at:                *";
		result += "\r\n";
		result += "* http://wbec-ridderkerk.nl/html/UCIProtocol.html                         *";
		result += "\r\n";
		result += "***************************************************************************";
		result += "\r\n";
		result += ">";
		channel.sendCommandToGUI_no_newline(result);
	}
	
	
	private void sendNewline() throws IOException {
		//String result = "\r\n";
		//result += ">";
		//channel.sendCommandToGUI_no_newline(result);
	}
	
	
	private void sendEngineID() throws IOException {
		String id_name = COMMAND_TO_GUI_ID_STR + Channel.WHITE_SPACE;
		id_name += COMMAND_TO_GUI_ID_NAME_STR + Channel.WHITE_SPACE + "Bagatur " + COMMAND_TO_GUI_ID_VERSION_STR;
		channel.sendCommandToGUI(id_name);
		
		String id_author = COMMAND_TO_GUI_ID_STR + Channel.WHITE_SPACE;
		id_author += COMMAND_TO_GUI_ID_AUTHOR_STR + Channel.WHITE_SPACE + "Krasimir Topchiyski" + ", Bulgaria";
		channel.sendCommandToGUI(id_author);
	}
	
	
	private void sendOptions() throws IOException {
		for (int i=0; i<optionsManager.getOptions().getAllOptions().length; i++) {
			String line = COMMAND_TO_GUI_OPTION_STR + Channel.WHITE_SPACE
							+ COMMAND_TO_ENGINE_SETOPTION_NAME_STR + Channel.WHITE_SPACE
							+ optionsManager.getOptions().getAllOptions()[i].getDefineCommand();
			channel.sendCommandToGUI(line);
		}
	}
	
	
	private void setOption(String fromGUILine) throws IOException {
		
		channel.sendLogToGUI("Set-option called with line: " + fromGUILine);
		SetOption setoption = new SetOption(fromGUILine);
		channel.sendLogToGUI("Set-option parsed: " + setoption);
		
		optionsManager.set(setoption);
	}
	
	
	private void sendUCIOK() throws IOException {
		channel.sendCommandToGUI(COMMAND_TO_GUI_UCIOK_STR);
	}
	
	
	private void sendReadyOK() throws IOException {
		channel.sendCommandToGUI(COMMAND_TO_GUI_READYOK);
	}
	
	
	private void createNewGame() {
		revertGame();
	}
	
	
	private void setupBoard(String fromGUILine) throws FileNotFoundException {
		
		/*
		 * Setup startup board with FEN or played moves
		 */
		Position position = new Position(fromGUILine);
		
		if (position.getFen() != null) {
			
			board = new Board(position.getFen());
			
			recreateSearchAdaptor();
			
		} else {
			
			revertGame();
			
			int colour = Figures.COLOUR_WHITE;
			List<String> moves = position.getMoves();
			int size = moves.size();
			for (int i = 0; i < size; i++ ) {
				
				String moveSign = moves.get(i);
				if (!moveSign.equals("...")) {
					int move = BoardUtils.parseSingleUCIMove(board, colour, moveSign);
					colour = Figures.OPPONENT_COLOUR[colour];
					
					board.makeMoveForward(move);
				}
			}
		}
	}
	
	
	private void goSearch(String fromGUILine) throws IOException {
		Go go = new Go(fromGUILine);	
		channel.sendLogToGUI(go.toString());
		searchAdaptor.goSearch(channel, this, go);
	}
	
	
	private void ponderHit(String fromGUILine) throws IOException {
		channel.sendLogToGUI("Ponder hit -> switching search");
		searchAdaptor.ponderHit();
	}
	
	
	public void sendBestMove() {

		int[] moveAndPonder = searchAdaptor.stopSearch();
		int move = moveAndPonder[0];
		int ponder = moveAndPonder[1];
		if (move != 0) { 
			
			board.makeMoveForward(move);
			
			String bestMoveCommand = COMMAND_TO_GUI_BESTMOVE_STR + Channel.WHITE_SPACE + MoveInt.moveToStringUCI(move);
			if (ponder != 0) {
				bestMoveCommand += Channel.WHITE_SPACE + COMMAND_TO_GUI_BESTMOVE_PONDER_STR + Channel.WHITE_SPACE;
				bestMoveCommand += MoveInt.moveToStringUCI(ponder);
			}
			
			try {
				channel.sendCommandToGUI(bestMoveCommand);
			} catch (IOException e) {
				channel.dump(e);
			}
		} else {
			channel.sendLogToGUI("WARNING: StateManager -> move returned from UCI Search adaptor is '0' and is not sent to the UCI platform");
		}
	}
	
	private void revertGame() {
		int count = board.getPlayedMovesCount();
		int[] moves = board.getPlayedMoves();
		for (int i = count - 1; i >=0; i--) {
			int move = moves[i];
			board.makeMoveBackward(move);
		}
	}
}
