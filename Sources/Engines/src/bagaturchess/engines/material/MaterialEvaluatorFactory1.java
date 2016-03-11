package bagaturchess.engines.material;


import bagaturchess.bitboard.api.IBitBoard;
import bagaturchess.search.api.IEvalConfig;
import bagaturchess.search.api.IEvaluator;
import bagaturchess.search.api.IEvaluatorFactory;
import bagaturchess.search.impl.evalcache.EvalCache;
import bagaturchess.search.impl.evalcache.IEvalCache;



public class MaterialEvaluatorFactory1 implements IEvaluatorFactory {
	
	public MaterialEvaluatorFactory1() {
	}
	
	public IEvaluator create(IBitBoard bitboard, IEvalCache evalCache) {
		return new MaterialEvaluator1(bitboard);
	}
	
	public IEvaluator create(IBitBoard bitboard, IEvalCache evalCache, IEvalConfig evalConfig) {
		return new MaterialEvaluator1(bitboard);
	}
	
}
