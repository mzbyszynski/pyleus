package com.yelp.pyleus.kafka;

import storm.kafka.KafkaSpout;
import storm.kafka.KeyValueSchemeAsMultiScheme;
import storm.kafka.SpoutConfig;
import storm.kafka.StringKeyValueScheme;
import storm.kafka.ZkHosts;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.TopologyBuilder;

import com.yelp.pyleus.SpoutProvider;
import com.yelp.pyleus.spec.SpoutSpec;

public class KafkaSpoutProvider implements SpoutProvider {

   public static final String KAFKA_ZK_ROOT_FMT = "/pyleus-kafka-offsets/%s";
   public static final String KAFKA_CONSUMER_ID_FMT = "pyleus-%s";

   @Override
   public IRichSpout provide(TopologyBuilder builder, SpoutSpec spec) {
      String topic = (String) spec.options.get("topic");
      if (topic == null) { throw new RuntimeException("Kafka spout must have topic"); }

      String zkHosts = (String) spec.options.get("zk_hosts");
      if (zkHosts == null) { throw new RuntimeException("Kafka spout must have zk_hosts"); }

      String zkRoot = (String) spec.options.get("zk_root");
      if (zkRoot == null) {
         zkRoot = String.format(KAFKA_ZK_ROOT_FMT, spec.name);
      }

      String consumerId = (String) spec.options.get("consumer_id");
      if (consumerId == null) {
         consumerId = String.format(KAFKA_CONSUMER_ID_FMT, spec.name);
      }

      SpoutConfig config = new SpoutConfig(
               new ZkHosts(zkHosts),
               topic,
               zkRoot,
               consumerId
               );

      Boolean forceFromStart = (Boolean) spec.options.get("from_start");
      if (forceFromStart != null) {
         config.forceFromStart = forceFromStart;
      }

      Long startOffsetTime = (Long) spec.options.get("start_offset_time");
      if (startOffsetTime != null) {
         config.startOffsetTime = startOffsetTime;
      }

      // TODO: this mandates that messages are UTF-8. We should allow for binary data
      // in the future, or once users can have Java components, let them provide their
      // own JSON serialization method. Or wait on STORM-138.
      config.scheme = new KeyValueSchemeAsMultiScheme(new StringKeyValueScheme());

      return new KafkaSpout(config);
   }

}
