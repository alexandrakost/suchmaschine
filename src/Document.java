public class Document {

    public static final String[] SUFFICES = {
            "in", "ei", "ab", "al", "ant", "artig", "bar", "chen", "eln", "en", "end", "ent", "er", "fach", "fikation",
            "fizieren", "fähig", "gemäß", "gerecht", "haft", "haltig", "heit", "ieren", "ig", "ion", "iren",
            "isch", "isieren", "isierung", "ismus", "ist", "ität", "iv", "keit", "kunde", "legen", "lein", "lich",
            "ling", "logie", "los", "mal", "meter", "mut", "nis", "or", "sam", "schaft", "tum", "ung", "voll", "wert",
            "wurdig"};

    private String title;
    private String language;
    private String description;
    private Date releaseDate;
    private Author author;
    private WordCountArray wordCountArray;
    private String text;

    public Document(String theTitle, String theLanguage, String theDescription, Date theReleaseDate, Author theAuthor) {
        this.title = theTitle;
        this.language = theLanguage;
        this.description = theDescription;
        this.releaseDate = theReleaseDate;
        this.author = theAuthor;
    }

    public Document(String title, String language, String description, Date releaseDate, Author author, String text) {
        this.title = title;
        this.language = language;
        this.description = description;
        this.releaseDate = releaseDate;
        this.author = author;
        this.text = text;
        addText(text); // this.wordCountArray = blabla;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    public String getDescription() {
        return description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Author getAuthor() {
        return author;
    }

    public String toString() {
        return "Document characteristics: Title:" + this.title + "; Language:" + this.language + "; Release date:"
                + this.releaseDate + "; Author:" + this.author.toString().substring(7) + ".";
    }
    /*public String toString2() {
        return "Document title als test:" + this.title;
    }*/

    public int getAge(Date today) {
        return this.releaseDate.getAgeInYears(today);
    }

    public WordCountArray getWordCountArray() {
        return this.wordCountArray;
    }

    private static String[] tokenize(String text) {
        String[] retArray;

        // check for special cases
        if (text == "" || text == null || text == " ") {
            retArray = new String[0];
            return retArray;
        }

        // calcualte the length of the returned string array
        int numberOfWordInText = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                numberOfWordInText++;
            }
        }

        numberOfWordInText++;

        // initialize retArray
        retArray = new String[numberOfWordInText];

        // add words from text string as elements to the array
        String tempWord = "";
        int resultArrayIndex = 0;
        for (int i = 0; i < text.length(); i++) {
            if (i == text.length() -1) {
                retArray[resultArrayIndex] = tempWord + text.charAt(i);
                return retArray;
            }

            if (text.charAt(i) == ' ') {
                retArray[resultArrayIndex] = tempWord;
                resultArrayIndex ++;
                tempWord = "";
            } else {
                tempWord = tempWord + text.charAt(i);
            }
            /*if (text.charAt(i) != ' ' && i != text.length()-1) {
                tempWord = tempWord + text.charAt(i);
            } else {
                if (i == text.length() -1) {
                    retArray[resultArrayIndex] = tempWord + text.charAt(i);
                } else {
                    retArray[resultArrayIndex] = tempWord;
                    resultArrayIndex++;
                    tempWord = "";
                }
            }*/
        }
        return retArray;
    }
    //schaut, ob die letzten n Buchstaben des Wortes 1 und des Wortes 2 gleich sind
    private static boolean suffixEqual(String word1, String word2, int n) {
        // special cases
        if (n > word1.length() || n > word2.length()) {
            return false;
        }
        boolean isEqual = true;
        int i = 0;
        while (isEqual && i < n) {
            // compare
            if (word1.charAt(word1.length() - 1 - i) != word2.charAt(word2.length() - 1 - i)) {
                isEqual = false;
            }
            i++;
        }
        return isEqual;
    }

    private static String findSuffix(String word) {
        if (word == null || word.equals("")) {
            return null;
        }

        String suffix = "";
        String suffixFound = "";

        for (int i = 0; i < Document.SUFFICES.length; i++) {
            suffix = SUFFICES[i];
            if (suffixEqual(word, suffix, suffix.length())) {
                suffixFound = suffix;
            }
        }

        return suffixFound;
    }

    private static String cutSuffix(String word, String suffix) {
        if (suffix == null || suffix.equals("")) {
            return word;
        }

        if (word == null) {
            return null;
        }

        if (!suffixEqual(word, suffix, suffix.length())) {
            return word;
        }

        String wordWithoutSuffix = "";
        for (int x = 0; x < word.length() - suffix.length(); x++) {
            wordWithoutSuffix = wordWithoutSuffix + word.charAt(x);
        }

        return wordWithoutSuffix;
    }


    /**
     * transforms text to an array of unique words without suffices with relevant word frequences.
     *
     * @param text the raw text of a document
     */
    private void addText(String text) {
        // zerlegt Fließtext in einzelne Wörter
        String[] textWords = tokenize(text);

        // entfernt Suffices und ersetzt die wörter im Array textWords durch Wörter ohne Suffices
        for (int i = 0; i < textWords.length; i++) {
            String rawWord = textWords[i];
            String suffixFound = findSuffix(rawWord);
            String wordWithoutSuffix = cutSuffix(rawWord, suffixFound);
            textWords[i] = wordWithoutSuffix;
        }
        // erstellt ein neues Wordcount-Array mit der alten Größe
        WordCountArray uniqueWordCountArray = new WordCountArray(textWords.length);

        // für jedes wort, zählt wie oft es in dem alten Array vorkommt
        // und schreibt das Wort und die Häufigkeit mit Hilfe von add-Methode in das neue WordCount-Array

        // for-loop for the word in question
        for (int i = 0; i < textWords.length; i++) {
            int wordFrequency = 1;
            // for-loop for the rest of the words
            for (int x = i + 1; x < textWords.length; x++) {
                if (textWords[i].equals(textWords[x]) && !textWords[i].equals("") && !textWords[x].equals("")) {
                    wordFrequency++;
                    textWords[x] = "";
                }
            }
            uniqueWordCountArray.add(textWords[i], wordFrequency);
        }
        this.wordCountArray = uniqueWordCountArray;

    }

    public boolean equals (Document document) {
        if (document == null) {
            return false;
        }
        if (this.title.equals(document.title) && this.language.equals(document.language) &&
                this.description.equals(document.description) && this.releaseDate.equals(document.releaseDate) &&
                this.author.equals(document.author) && this.text.equals(document.text) &&
                this.wordCountArray.equals(document.wordCountArray)) {
            return true;
        }
        return false;
    }

}