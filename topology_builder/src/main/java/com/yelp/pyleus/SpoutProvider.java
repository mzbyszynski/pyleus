package com.yelp.pyleus;

import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.TopologyBuilder;

import com.yelp.pyleus.spec.SpoutSpec;

public interface SpoutProvider {

   IRichSpout handleKafkaSpout(final TopologyBuilder builder,
            final SpoutSpec spec);
}
