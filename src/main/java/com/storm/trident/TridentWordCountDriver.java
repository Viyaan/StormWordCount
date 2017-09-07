package com.storm.trident;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.operation.builtin.Debug;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.trident.testing.Split;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class TridentWordCountDriver {

	public static void main(String[] argv) {
		Config config = new Config();
		config.setDebug(true);


/*		String inputFile = "C:\\Users\\Admin\\Documents\\GitHub\\StormWordCount\\src\\main\\resources\\storm_destination_file.txt";
		config.put("inputFile", inputFile);
		LineReaderSpout lineReaderSpout = new LineReaderSpout();*/

		@SuppressWarnings("unchecked")
		FixedBatchSpout spout = new FixedBatchSpout(new Fields("line"), 3, new Values("the cow jumped over the moon"),
				new Values("the man went to the store and bought some candy"),
				new Values("four score and seven years ago"), new Values("how many apples can you eat"));
		spout.setCycle(false);
		
		TridentTopology topology = new TridentTopology();
		topology.newStream("spout", spout).each(new Fields("line"), new Split(), new Fields("word"))
				.groupBy(new Fields("word"))
				.persistentAggregate(new MemoryMapState.Factory(), new Count(), new Fields("count")).newValuesStream()
				.each(new Fields("word", "count"), new Debug());

		LocalCluster localCluster = new LocalCluster();
		localCluster.submitTopology("WordCount", config, topology.build());

	}
}