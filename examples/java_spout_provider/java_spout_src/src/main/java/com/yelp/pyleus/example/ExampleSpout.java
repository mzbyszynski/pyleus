package com.yelp.pyleus.example;

import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

public class ExampleSpout extends BaseRichSpout {

   private static final long serialVersionUID = 1L;

   private SpoutOutputCollector collector;
   private final String sentence;
   private int sentencesPerMin = 30;

   public ExampleSpout(String sentence) {
      this.sentence = sentence;
   }

   public void nextTuple() {
      Utils.sleep(1000 * 60 / sentencesPerMin);
      collector.emit(new Values(sentence));
   }

   public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
      this.collector = collector;
   }

   public void declareOutputFields(OutputFieldsDeclarer declarer) {
      declarer.declare(new Fields("sentence"));
   }

   public int getSentencesPerMin() {
      return sentencesPerMin;
   }

   public void setSentencesPerMin(int sentencesPerMin) {
      this.sentencesPerMin = sentencesPerMin;
   }

}
