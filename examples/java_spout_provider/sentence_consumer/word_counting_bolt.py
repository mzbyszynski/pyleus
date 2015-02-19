from pyleus.storm import SimpleBolt

class WordCountingBolt(SimpleBolt):

  OUTPUT_FIELDS = ["wc"]

  def process_tuple(self, tup):
    sentence = tup.values[0]
    cnt = len(sentence.split())
    self.emit((cnt,), anchors=[tup])

if __name__ == '__main__':
  WordCountingBolt().run()