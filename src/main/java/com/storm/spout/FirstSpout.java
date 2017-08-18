package com.storm.spout;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class FirstSpout extends BaseRichSpout {
		private static final long serialVersionUID = -4646687160233411001L;
	public static Logger LOG = Logger.getLogger(FirstSpout.class);
	private SpoutOutputCollector collector;
	private int referenceRandom;
	private static final int MAX_RANDOM = 10;
	
	public FirstSpout(){
		final Random rand = new Random();
		referenceRandom = rand.nextInt(MAX_RANDOM);
	}
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this.collector = collector;
	}
	
	public void nextTuple() {
		Utils.sleep(100);
		final Random rand = new Random();
		int instanceRandom = rand.nextInt(MAX_RANDOM);
		if(instanceRandom == referenceRandom){
			collector.emit(new Values("Hello World"));
		} else {
			collector.emit(new Values("Other Random Word"));
		}
	}
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sentence"));
	}
}