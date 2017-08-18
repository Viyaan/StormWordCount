package com.storm.topology;


import com.storm.bolts.FirstBolt;
import com.storm.spout.FirstSpout;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

public class FirstTopology {

	public static void main(String[] args) throws Exception {
		TopologyBuilder builder = new TopologyBuilder();
        
        builder.setSpout("firstSpout", new FirstSpout(), 2);        
        builder.setBolt("firstBolt", new FirstBolt(), 3).globalGrouping("firstSpout");
                
        Config conf = new Config();
        conf.setDebug(false);
        
        if(args!=null && args.length > 0) {
            conf.setNumWorkers(20);
            StormSubmitter.submitTopology("first-topology", conf, builder.createTopology());
        } else {
        
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("first-topology", conf, builder.createTopology());
            Utils.sleep(10000);
            cluster.killTopology("first-topology");
            cluster.shutdown();    
        }

	}

}
