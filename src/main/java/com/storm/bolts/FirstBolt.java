package com.storm.bolts;


import java.util.Map;

import org.apache.log4j.Logger;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

public class FirstBolt extends BaseRichBolt {
	
	public static Logger LOG = Logger.getLogger(FirstBolt.class);
	
	private static final long serialVersionUID = -841805977046116528L;
	
	private int myCount = 0;
	
	private int myCount_2 = 0;

	
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
	}

	public void execute(Tuple input) {
		String test = input.getStringByField("sentence");
		if(test == "Hello World"){
			myCount++;
			System.out.println("Found a Hello World! My Count is now: " + Integer.toString(myCount));
			LOG.debug("Found a Hello World! My Count is now: " + Integer.toString(myCount));
		}else {
			myCount_2++;
			System.out.println("Found a Other Random Word! My Count is now: " + Integer.toString(myCount_2));
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("myCount"));

	}

}
