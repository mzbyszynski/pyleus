package com.yelp.pyleus.example;

import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.TopologyBuilder;

import com.yelp.pyleus.SpoutProvider;
import com.yelp.pyleus.spec.SpoutSpec;

public class ExampleSpoutProvider implements SpoutProvider {

   public IRichSpout provide(TopologyBuilder builder, SpoutSpec spec) {
      String sentence = "No Sentence Specified";
      Object o = spec.options.get("sentence");
      if (o != null && o instanceof String)
         sentence = (String) o;

      Integer sentencesPerMin = null;
      o = spec.options.get("sentencesPerMin");
      if (o != null)
         // this will fail fast in case of non-null, invalid numeric value.
         sentencesPerMin = Integer.valueOf(o.toString());

      ExampleSpout out = new ExampleSpout(sentence);
      if (sentencesPerMin != null && sentencesPerMin.intValue() > 0)
         out.setSentencesPerMin(sentencesPerMin);
      return out;
   }

}
