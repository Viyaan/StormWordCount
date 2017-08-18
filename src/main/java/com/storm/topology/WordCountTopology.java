package com.storm.topology;


import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

import com.storm.bolts.ReportBolt;
import com.storm.bolts.SplitSentenceBolt;
import com.storm.bolts.WordCountBolt;
import com.storm.spout.SentenceSpout;

public class WordCountTopology {
	
	public static final String SENTENCE_SPOUT_ID="sentence-spout";
	public static final String SPLIT_BOLT_ID="split-bolt";
	public static final String COUNT_BOLT_ID="count-bolt";
	public static final String REPORT_BOLT_ID="report-bolt";
	public static final String TOPOLOGY_NAME="word-count-topology";
	
	
	public static void main(String[] args) throws Exception {
		TopologyBuilder builder = new TopologyBuilder();
		SentenceSpout spout = new SentenceSpout();
		SplitSentenceBolt splitBolt = new SplitSentenceBolt();
		WordCountBolt countBolt = new WordCountBolt();
		ReportBolt reportBolt = new ReportBolt();
        
        builder.setSpout(SENTENCE_SPOUT_ID, spout);        
        builder.setBolt(SPLIT_BOLT_ID, splitBolt).shuffleGrouping(SENTENCE_SPOUT_ID);
        builder.setBolt(COUNT_BOLT_ID, countBolt).fieldsGrouping(SPLIT_BOLT_ID, new Fields("word"));
        builder.setBolt(REPORT_BOLT_ID, reportBolt).globalGrouping(COUNT_BOLT_ID);
                
        Config conf = new Config();
        conf.setDebug(false);
        
        if(args!=null && args.length > 0) {
            conf.setNumWorkers(20);
            StormSubmitter.submitTopology(TOPOLOGY_NAME, conf, builder.createTopology());
        } else {
        
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology(TOPOLOGY_NAME, conf, builder.createTopology());
            Utils.sleep(10000);
            cluster.killTopology(TOPOLOGY_NAME);
            cluster.shutdown();    
        }

	}

}
