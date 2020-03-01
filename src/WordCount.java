
public class WordCount {

    private String word;
    private int wordFrequency;
    private double weight;
    private double normalizedWeight;

    public WordCount(String theWord, int theWordFrequency) {
        this.word = theWord;
        this.wordFrequency = theWordFrequency;
    }

    public void setWordFrequency(int wordFrequency) {
        if (wordFrequency >= 0) {
            this.wordFrequency = wordFrequency;
        }
    }

    public String getWord() {
        return word;
    }

    public int getWordFrequency() {
        return wordFrequency;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getNormalizedWeight() {
        return normalizedWeight;
    }

    public void setNormalizedWeight(double normalizedWeight) {
        this.normalizedWeight = normalizedWeight;
    }

    public int incrementCount() {
        this.wordFrequency = this.wordFrequency + 1;
        return this.wordFrequency;
    }

    /**
     * this method increments the existing wordFreuncy by n and returns the new value
     *
     * @param n count to be incremented to wordFrequency
     * @return wordFrequency
     */
    public int incrementCount(int n) {
        if (n >= 0) {
            this.wordFrequency = this.wordFrequency + n;
        }
        return this.wordFrequency;
    }

    @Override
    public String toString() {
        return "(word: " + this.word + ", " + this.wordFrequency + ")";
    }

    public boolean equals(WordCount wc) {
        if (wc == null) {
            return false;
        }
        if (this.word.equals(wc.word) && this.wordFrequency == wc.wordFrequency) {
            return true;
        }
        return false;
    }

}
