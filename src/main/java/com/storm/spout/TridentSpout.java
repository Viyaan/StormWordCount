package com.storm.spout;

import java.util.Map;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.trident.spout.ITridentSpout;
import org.apache.storm.tuple.Fields;

public class TridentSpout implements ITridentSpout{

	public BatchCoordinator getCoordinator(String txStateId, Map conf, TopologyContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	public Emitter getEmitter(String txStateId, Map conf, TopologyContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	public Fields getOutputFields() {
		// TODO Auto-generated method stub
		return null;
	}

}
