public class WordCountArray {

    private WordCount[] wordCountObjects;

    public WordCountArray(int maxSize) {
        this.wordCountObjects = new WordCount[maxSize];
    }

    public void add(String word, int count) {
        if (word == null || word.equals("") || count < 0) {
            return;
        }
        word = word.toLowerCase();
        WordCount wc = new WordCount(word, count);

        //System.out.println("ADD WCobjects.length = " + this.wordCountObjects.length);
        for (int i = 0; i < this.wordCountObjects.length; i++) {

            if (this.wordCountObjects[i] != null && this.wordCountObjects[i].getWord().equals(word)) {
                this.wordCountObjects[i].setWordFrequency(this.wordCountObjects[i].getWordFrequency() + count);
                return;
            }

            if (this.wordCountObjects[i] == null) {
                this.wordCountObjects[i] = wc;
                return;
            }
        }

        int arrayLength = this.wordCountObjects.length;
        WordCount[] newWordCountObjects = new WordCount[arrayLength * 2];


        for (int i = 0; i < arrayLength; i++) {
            newWordCountObjects[i] = this.wordCountObjects[i];
        }
        newWordCountObjects[arrayLength] = wc;
        this.wordCountObjects = newWordCountObjects;
    }

    // gibt Anzahl der verwalteten WordCount-Objecte als Rückgabewert;
    public int size() {

        for (int i = 0; i < this.wordCountObjects.length; i++) {
            if (this.wordCountObjects[i] == null) {
                return i;
            }
        }

        return this.wordCountObjects.length;
    }

    public String getWord(int index) {
        if (index < 0 || index >= this.wordCountObjects.length || this.wordCountObjects[index] == null) {
            return null;
        }

        return this.wordCountObjects[index].getWord();
    }

    public int getCount(int index) {
        if (index < 0 || index >= this.wordCountObjects.length || this.wordCountObjects[index] == null) {
            return -1;
        }

        return this.wordCountObjects[index].getWordFrequency();
    }

    public void setCount(int index, int count) {
        for (int i = 0; i < this.wordCountObjects.length; i++) {
            if (index == i) {
                this.wordCountObjects[i].setWordFrequency(count);
            }
        }
    }

    @Override
    public String toString() {
        String wordCountArrayString = "[";
        for (int i = 0; i < this.wordCountObjects.length; i++) {
            wordCountArrayString = wordCountArrayString + this.wordCountObjects[i];
        }
        wordCountArrayString = wordCountArrayString + "]";
        return wordCountArrayString;
    }

    /**
     * Vergleiche this mit wca unabhängig von der Reihenfolge
     *
     * @param wca
     * @return true wenn this und wca die gleichen Elemente enthalten unabhängig von der Reihenfolge
     */
    public boolean equals(WordCountArray wca) {
        // erstelle ein neues WorCountArray
        WordCountArray copieOfwca = new WordCountArray(wca.wordCountObjects.length);
        // ordne die Objekte von wca an denselben Stellen um eine Kopie von wca zu erstellen:
        for (int i = 0; i < wca.wordCountObjects.length; i++) {
            copieOfwca.wordCountObjects[i] = wca.wordCountObjects[i];
        }

        // Annahme (in den Angaben): jedes Wort in den WordCountArrays mit Count 1;
        if (copieOfwca == null || this.wordCountObjects.length != copieOfwca.wordCountObjects.length) {
            return false;
        }
        // nehme das Wort von this, gehe wca durch, finde dort das erste Wort gleich dem Wort aus this, ersetze WordCountFrequency für diese Wort durch 0;, nehme das nächste Wort von this.
        // boolean true if a matching word is found in wca:
        boolean wordMatches = false;
        // for-loop for the WordCountObjects of the instance of WordCountArray;
        for (int i = 0; i < this.wordCountObjects.length; i++) {
            // for-loop for the WordCountObjects of wca;
            for (int x = 0; x < copieOfwca.wordCountObjects.length; x++) {
                if (copieOfwca.wordCountObjects[x].getWordFrequency() != 0 &&
                        this.wordCountObjects[i].equals(copieOfwca.wordCountObjects[x])) {
                    copieOfwca.wordCountObjects[x].setWordFrequency(0);
                    wordMatches = true;
                }
            }
            // wenn für ein Wort aus this. kein Match in wca gefunden, soll die Methode false zurückgeben.
            if (wordMatches == false) {
                return false;
            }
        }
        return true;
    }

    public int getIndex(String word) {
        for (int i = 0; i < this.size(); i++) {
            if (this.wordCountObjects[i].getWord().equals(word)) {
                return i;
            }
        }
        return -1;
    }

    private boolean wordsEqual(WordCountArray wca) {
        if (wca == null || this.size() != wca.size()) {
            return false;
        }

        for (int i = 0; i < this.size(); i++) {
            if (!this.wordCountObjects[i].getWord().equals(wca.wordCountObjects[i].getWord())) {
                return false;
            }
        }
        return true;
    }

    private double scalarProduct(WordCountArray wca) {
        double productResult = 0.0;

        if (wca == null || !this.wordsEqual(wca)) {
            return productResult;
        }
        this.sort();
        wca.sort();
        for (int i = 0; i < this.size(); i++) {
            productResult = productResult +
                    this.wordCountObjects[i].getWordFrequency() * wca.getCount(i);
        }

        return productResult;
    }

    public void sort() {
        bubbleSort();
    }

    private void bubbleSort() {
        // for-loop um die Wörtern an den letzten Stellen nicht nochmal zu vergleichen, weil sie bereits richtig platziert sind;
        for (int i = 0; i < this.size() - 1; i++) {
            // for-loop für die restlichen Wörter;
            for (int x = 0; x < this.size() - i - 1; x++) {
                if (this.wordCountObjects[x].getWord().compareTo(this.wordCountObjects[x + 1].getWord()) > 0) {
                    WordCount temp = wordCountObjects[x];
                    wordCountObjects[x] = wordCountObjects[x + 1];
                    wordCountObjects[x + 1] = temp;
                }
            }
        }
    }

    private void bucketSort() {
        // sortiere die Wordcounts in 4 Buckets, abhängig von der ersten Buchstabe
        WordCountArray bucketAF = new WordCountArray(1);
        WordCountArray bucketGL = new WordCountArray(1);
        WordCountArray bucketMS = new WordCountArray(1);
        WordCountArray bucketTZ = new WordCountArray(1);
        for (int i = 0; i < this.size(); i++) {
            String word = this.wordCountObjects[i].getWord();
            int count = this.wordCountObjects[i].getWordFrequency();
            if (this.wordCountObjects[i].getWord().substring(0, 1).compareTo("s") > 0) {
                bucketTZ.add(word, count);
            }
        }
        for (int i = 0; i < this.size(); i++) {
            String word = this.wordCountObjects[i].getWord();
            int count = this.wordCountObjects[i].getWordFrequency();
            if (this.wordCountObjects[i].getWord().substring(0, 1).compareTo("l") > 0 &&
                    this.wordCountObjects[i].getWord().substring(0, 1).compareTo("t") < 0) {
                bucketMS.add(word, count);
            }
        }
        for (int i = 0; i < this.size(); i++) {
            String word = this.wordCountObjects[i].getWord();
            int count = this.wordCountObjects[i].getWordFrequency();
            if (this.wordCountObjects[i].getWord().substring(0, 1).compareTo("f") > 0 &&
                    this.wordCountObjects[i].getWord().substring(0, 1).compareTo("m") < 0) {
                bucketGL.add(word, count);
            }
        }
        for (int i = 0; i < this.size(); i++) {
            String word = this.wordCountObjects[i].getWord();
            int count = this.wordCountObjects[i].getWordFrequency();
            if (this.wordCountObjects[i].getWord().substring(0, 1).compareTo("g") < 0) {
                bucketAF.add(word, count);
            }
        }
        //sortiere Wordcountobjects in jedem Bucket mit bubbleSort
        bucketAF.bubbleSort();
        bucketGL.bubbleSort();
        bucketMS.bubbleSort();
        bucketTZ.bubbleSort();
        //konkateniere Buckets
        for (int i = 0; i < bucketGL.size(); i++) {
            String word = bucketGL.wordCountObjects[i].getWord();
            int count = bucketGL.wordCountObjects[i].getWordFrequency();
            bucketAF.add(word, count);
        }
        for (int i = 0; i < bucketMS.size(); i++) {
            String word = bucketMS.wordCountObjects[i].getWord();
            int count = bucketMS.wordCountObjects[i].getWordFrequency();
            bucketAF.add(word, count);
        }
        for (int i = 0; i < bucketTZ.size(); i++) {
            String word = bucketTZ.wordCountObjects[i].getWord();
            int count = bucketTZ.wordCountObjects[i].getWordFrequency();
            bucketAF.add(word, count);
        }
        //System.out.println("BUCKET THIS SIZE = " + this.size());
        //System.out.println("BUCKET bucketAF SIZE = " + bucketAF.size());
        for (int i = 0; i < this.size(); i++) {
            this.wordCountObjects[i] = bucketAF.wordCountObjects[i];
        }

    }

    public double similarity(WordCountArray wca) {
        double result = 0.0;
        if (wca != null) {
            this.sort();
            wca.sort();

            result = ((this.scalarProduct(wca)) / (Math.sqrt((this.scalarProduct(this) * wca.scalarProduct(wca)))));
        }
        return result;
    }

    //HA 4
    private void calculateWeights(DocumentCollection dc) {
        if (dc == null) {
            return;
        }

        // for-loop für Words this
        for (int j = 0; j < this.size(); j++) {
            String wordj = this.getWord(j);
            int noOfDocsWithWordj = dc.noOfDocumentsContainingWord(wordj);
            double invertedFrequencyj = Math.log((dc.size() + 1.0) / noOfDocsWithWordj);
            double weightj = this.getCount(j) * invertedFrequencyj;
            this.wordCountObjects[j].setWeight(weightj);
        }
    }

    private void calculateNormalizedWeights(DocumentCollection dc) {
        if (dc == null) {
            return;
        }

        this.calculateWeights(dc);

        double nenner = 0;
        for (int j = 0; j < this.size(); j++) {
            double weightj = this.wordCountObjects[j].getWeight();
            nenner = nenner + Math.pow(weightj, 2.0);
        }

        // for-loop für Words um den normalized weight zu berechnen:
        for (int j = 0; j < this.size(); j++) {
            double weightj = this.wordCountObjects[j].getWeight();
            double normWeightj = weightj / Math.sqrt(nenner);
            this.wordCountObjects[j].setNormalizedWeight(normWeightj);
        }
    }

    private double scalarProduct(WordCountArray wca, DocumentCollection dc) {
        if (dc == null || !this.wordsEqual(wca)) {
            return -1.0;
        }

        this.sort();
        this.calculateNormalizedWeights(dc);

        wca.sort();
        wca.calculateNormalizedWeights(dc);

        double scalarProduct = 0.0;
        // for-loop für Wörter in Arrays:
        for (int i = 0; i < this.size(); i++) {
            double iWeightThis = this.wordCountObjects[i].getNormalizedWeight();
            double iWeightWca = wca.wordCountObjects[i].getNormalizedWeight();
            scalarProduct = scalarProduct + iWeightThis * iWeightWca;
        }

        return scalarProduct;
    }

    public double similarity(WordCountArray wca, DocumentCollection dc) {
        return scalarProduct(wca, dc);
    }
}

