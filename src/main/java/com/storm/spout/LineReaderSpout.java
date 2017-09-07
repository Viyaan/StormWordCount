package com.storm.spout;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class LineReaderSpout implements IRichSpout {
	private SpoutOutputCollector collector;
	private FileReader fileReader;
	private boolean completed = false;
	private TopologyContext context;

	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		try {
			this.context = context;
			this.fileReader = new FileReader(conf.get("inputFile").toString());
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Error reading file " + conf.get("inputFile"));
		}
		this.collector = collector;
	}

	public void nextTuple() {
		if (completed) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
		}
		String str;
		BufferedReader reader = new BufferedReader(fileReader);
		try {
			while ((str = reader.readLine()) != null) {
				this.collector.emit(new Values(str), str);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error reading tuple", e);
		} finally {
			completed = true;
		}

	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("line"));
	}

	public void close() {
		try {
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isDistributed() {
		return false;
	}

	public void activate() {
	}

	public void deactivate() {
	}

	public void ack(Object msgId) {
	}

	public void fail(Object msgId) {
	}

	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}