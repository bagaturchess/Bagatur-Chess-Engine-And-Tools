package bagaturchess.search.impl.rootsearch.sequential;


import bagaturchess.search.api.ISearchConfig_AB;
import bagaturchess.search.api.internal.ISearchInfo;
import bagaturchess.search.impl.tpt.TPTable;
import bagaturchess.search.impl.uci_adaptor.UCISearchMediatorImpl_NormalSearch;
import bagaturchess.search.impl.uci_adaptor.timemanagement.ITimeController;
import bagaturchess.uci.api.BestMoveSender;
import bagaturchess.uci.impl.Channel;
import bagaturchess.uci.impl.commands.Go;


public class DualSearchMediatorImpl extends UCISearchMediatorImpl_NormalSearch {
	
	
	public DualSearchMediatorImpl(Channel _channel, Go _go,
			ITimeController _timeController, int _colourToMove,
			BestMoveSender _sender, TPTable _tpt, ISearchConfig_AB _searchConfig) {
		super(_channel, _go, _timeController, _colourToMove, _sender, _tpt, _searchConfig);
	}
	
	@Override
	public void changedMajor(ISearchInfo info) { 
		//timeController.newPVLine(info.getEval(), info.getDepth(), info.getBestMove());
		super.changedMajor(info);
	}
}
