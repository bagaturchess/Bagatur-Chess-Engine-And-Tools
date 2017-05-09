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
package bagaturchess.search.impl.rootsearch.sequential;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bagaturchess.bitboard.api.IBitBoard;
import bagaturchess.bitboard.impl.movegen.MoveInt;
import bagaturchess.bitboard.impl.utils.BinarySemaphore_Dummy;
import bagaturchess.bitboard.impl.utils.ReflectionUtils;
import bagaturchess.search.api.IEvaluator;
import bagaturchess.search.api.IFinishCallback;
import bagaturchess.search.api.IRootSearchConfig;
import bagaturchess.search.api.internal.CompositeStopper;
import bagaturchess.search.api.internal.ISearch;
import bagaturchess.search.api.internal.ISearchInfo;
import bagaturchess.search.api.internal.ISearchMediator;
import bagaturchess.search.api.internal.ISearchStopper;
import bagaturchess.search.api.internal.SearchInterruptedException;
import bagaturchess.search.impl.evalcache.EvalCache1;
import bagaturchess.search.impl.info.SearchInfoFactory;
import bagaturchess.search.impl.pv.PVHistoryEntry;
import bagaturchess.search.impl.pv.PVNode;
import bagaturchess.search.impl.rootsearch.RootSearch_BaseImpl;
import bagaturchess.search.impl.rootsearch.multipv.MultiPVMediator;
import bagaturchess.search.impl.rootsearch.sequential.mtd.Mediator_AlphaAndBestMoveWindow;
import bagaturchess.search.impl.rootsearch.sequential.mtd.SearchManager;
import bagaturchess.search.impl.uci_adaptor.timemanagement.ITimeController;
import bagaturchess.search.impl.utils.DEBUGSearch;
import bagaturchess.uci.api.ChannelManager;
import bagaturchess.uci.impl.commands.Go;


/**
 * This implementation is 70 ELO scores weaker than SequentialSearch_MTD.
 * That is why the distribution currently works with SequentialSearch_MTD.
 */
public class SequentialSearch_Classic extends RootSearch_BaseImpl {
	
	
	private ExecutorService executor;
	private ISearch searcher;
	
	
	public SequentialSearch_Classic(Object[] args) {
		super(args);
		executor = Executors.newFixedThreadPool(1);
	}
	
	
	public IRootSearchConfig getRootSearchConfig() {
		return (IRootSearchConfig) super.getRootSearchConfig();
	}
	
	
	@Override
	public void createBoard(IBitBoard _bitboardForSetup) {
		
		super.createBoard(_bitboardForSetup);
		
		String searchClassName =  getRootSearchConfig().getSearchClassName();
		searcher = (ISearch) ReflectionUtils.createObjectByClassName_ObjectsConstructor(
						searchClassName,
						new Object[] {getBitboardForSetup(),  getRootSearchConfig(), getSharedData()}
					);
	}
	
	
	@Override
	public void negamax(IBitBoard _bitboardForSetup, ISearchMediator mediator, ITimeController timeController,
			final IFinishCallback multiPVCallback, final Go go) {
		negamax(_bitboardForSetup, mediator, timeController, multiPVCallback, go, false);
	}

	
	public void negamax(IBitBoard _bitboardForSetup, ISearchMediator mediator, ITimeController timeController,
			final IFinishCallback multiPVCallback, final Go go, boolean dont_wrap_mediator) {
		
		if (stopper != null) {
			throw new IllegalStateException("MTDSequentialSearch started whithout beeing stopped");
		}
		stopper = new Stopper();
		
		
		searcher.newSearch();
		
		setupBoard(_bitboardForSetup);
		
		
		final int startIteration = (go.getStartDepth() == Go.UNDEF_STARTDEPTH) ? 1 : go.getStartDepth();
		int maxIterations_tmp = (go.getDepth() == Go.UNDEF_DEPTH) ? ISearch.MAX_DEPTH : go.getDepth();
		Integer initialValue = (go.getBeta() == Go.UNDEF_BETA) ? null : go.getBeta();
		int[] prevPV = MoveInt.getPV(go.getPv(), _bitboardForSetup);
		
		if (maxIterations_tmp > ISearch.MAX_DEPTH) {
			maxIterations_tmp = ISearch.MAX_DEPTH;
			go.setDepth(maxIterations_tmp);
		}
		final int maxIterations = maxIterations_tmp; 
		
		if (DEBUGSearch.DEBUG_MODE) ChannelManager.getChannel().dump("MTDSequentialSearch started from depth " + startIteration + " to depth " + maxIterations);
		
		
		if (prevPV == null) {
			PVHistoryEntry historyEntry = searcher.getEnv().getPVs().getPV(getBitboardForSetup().getHashKey());
			if (historyEntry != null) {
				prevPV = historyEntry.getPv();
				initialValue = historyEntry.getEval();
			}
		} else {
			//TODO: store pv in pvhistory with given depth and evaluation
		}
		
		final int[] final_prevPV = prevPV;
		
		if (initialValue == null) {
			IEvaluator evaluator = getSharedData().getEvaluatorFactory().create(
					getBitboardForSetup(),
					//new EvalCache(100, true, new BinarySemaphore_Dummy()),
					new EvalCache1(5, 100, true, new BinarySemaphore_Dummy()),
					getRootSearchConfig().getEvalConfig());
			initialValue = (int) evaluator.fullEval(0, ISearch.MIN, ISearch.MAX, getBitboardForSetup().getColourToMove());
		}
		
		final int final_initialValue = initialValue;
		
		if (!dont_wrap_mediator) {
			//Original mediator should be an instance of UCISearchMediatorImpl_Base
			mediator = (mediator instanceof MultiPVMediator) ?
					new Mediator_AlphaAndBestMoveWindow(mediator) :
					new NPSCollectorMediator(new Mediator_AlphaAndBestMoveWindow(mediator));
		}
		
		final SearchManager distribution = new SearchManager(mediator, getBitboardForSetup().getHashKey(),
				startIteration, maxIterations, initialValue, searcher.getEnv().getPVs());
		
		//final ISearchStopper stopper = new MTDStopper(getBitboardForSetup().getColourToMove(), distribution);
		mediator.setStopper(new CompositeStopper(new ISearchStopper[] {mediator.getStopper(), stopper}, true ));
		
		
		final ISearchMediator final_mediator = mediator;
		
		
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					
					if (DEBUGSearch.DEBUG_MODE) ChannelManager.getChannel().dump("MTDSequentialSearch before loop");
					
					int prevEval = final_initialValue;
					int ASPIRATION_WINDOW = 10;
					
					for (int maxdepth = startIteration; maxdepth <= maxIterations; maxdepth++) {
						
						ISearchInfo info = SearchInfoFactory.getFactory().createSearchInfo();
						final_mediator.registerInfoObject(info);
						info.setDepth(maxdepth);
						info.setSelDepth(maxdepth);
						
						try {
							
							//Search with null window to prepare evaluation score and TPT entries
							int eval = searcher.nullwin_search(final_mediator, info,
									ISearch.PLY * maxdepth, ISearch.PLY * maxdepth,	0,
									prevEval,
									false, 0, 0, final_prevPV, searcher.getEnv().getBitboard().getColourToMove(), 0, 0, false, 0, !go.isPonder());
							
							//Search around evaluation score with aspiration window +- ASPIRATION_WINDOW
							int window_min = eval - ASPIRATION_WINDOW;
							int window_max = eval + ASPIRATION_WINDOW;
							eval = searcher.pv_search(final_mediator,
									null, info,
									ISearch.PLY * maxdepth, ISearch.PLY * maxdepth, 0,
									window_min, window_max,
									0, 0, final_prevPV,
									false, 0, searcher.getEnv().getBitboard().getColourToMove(),
									0, 0, false, 0, !go.isPonder());
							
							//If eval is outside the aspiration window, than search with MIN and MAX values
							if (eval < window_min || eval > window_max) {
								eval = searcher.pv_search(final_mediator,
										null, info,
										ISearch.PLY * maxdepth, ISearch.PLY * maxdepth, 0,
										ISearch.MIN, ISearch.MAX,
										0, 0, final_prevPV,
										false, 0, searcher.getEnv().getBitboard().getColourToMove(),
										0, 0, false, 0, !go.isPonder());
							}
							
							
							prevEval = eval;
							
							
							List<Integer> pv_buffer = new ArrayList<Integer>();
							info.setPV(PVNode.convertPV(searcher.getPvman().load(0), pv_buffer));
							if (info.getPV().length > 0) {
								info.setBestMove(info.getPV()[0]);
							}
							info.setEval(eval);
							
							final_mediator.changedMajor(info);
							
						} catch(SearchInterruptedException sie) {
							//The time is over and the sendBestMove method will be called below
							break;
						}
						
						if (final_mediator.getStopper().isStopped()) {
							break;
						}
					}
					
					if (DEBUGSearch.DEBUG_MODE) ChannelManager.getChannel().dump("MTDSequentialSearch after loop final_mediator.getStopper().isStopped()="
							+ final_mediator.getStopper().isStopped()
							+ ", distribution.getCurrentDepth()=" + distribution.getCurrentDepth() + ", distribution.getMaxIterations()=" + distribution.getMaxIterations());
					
					if (!isStopped()) {
						
						ChannelManager.getChannel().dump("MTDSequentialSearch not stopped - stopping searcher ...");
						
						if (stopper == null) {
							throw new IllegalStateException();
						}
						stopper.markStopped();
						stopper = null;
						
						
						if (multiPVCallback == null) {//Non multiPV search
							ChannelManager.getChannel().dump("MTDSequentialSearch calling final_mediator.getBestMoveSender().sendBestMove()");
							final_mediator.getBestMoveSender().sendBestMove();
						} else {
							//MultiPV search
							multiPVCallback.ready();
						}
					}
					
				} catch(Throwable t) {
					ChannelManager.getChannel().dump(t);
					ChannelManager.getChannel().dump(t.getMessage());
				}
			}
		});
	}
	
	
	@Override
	public void shutDown() {
		try {
			
			executor.shutdownNow();
			searcher = null;
			
		} catch(Throwable t) {
			//Do nothing
		}
	}


	@Override
	public int getTPTUsagePercent() {
		return searcher.getEnv().getTPTUsagePercent();
	}


	@Override
	public void decreaseTPTDepths(int reduction) {
		searcher.getEnv().getTPT().correctAllDepths(reduction);
	}
}
