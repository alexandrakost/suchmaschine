public class DocumentCollection {

    protected DocumentCollectionCell documentCollectionCellAnfang;

    public DocumentCollection() {
        this.documentCollectionCellAnfang = null;
    }

    public DocumentCollection(DocumentCollectionCell konkDocumentCollectionCell) {
        this.documentCollectionCellAnfang = konkDocumentCollectionCell;
    }

    public DocumentCollectionCell getDocumentCollectionCellAnfang() {
        return documentCollectionCellAnfang;
    }

    public void addFirst(Document doc) {
        if (doc == null) {
            return;
        }
        this.documentCollectionCellAnfang = new DocumentCollectionCell(doc, this.documentCollectionCellAnfang);
    }

    public void addLast(Document doc) {
        if (doc == null) {
            return;
        }

        if (this.documentCollectionCellAnfang == null) {
            addFirst(doc);
            return;
        }

        DocumentCollectionCell navigator = this.documentCollectionCellAnfang;

        while (navigator.getNext() != null) {
            navigator = navigator.getNext();
        }
        // Listenende erreicht
        navigator.setNext(new DocumentCollectionCell(doc));
    }

    public boolean isEmpty() {
        if (this.documentCollectionCellAnfang == null) {
            return true;
        }
        return false;
    }

    public int size() {
        if (this.documentCollectionCellAnfang == null) {
            return 0;
        }
        // Liste hat size = 1 wenn Anfang !=0;
        int size = 1;
        // schaut ob das Element zeigt auf null. wenn nicht dann size++;
        DocumentCollectionCell navigator = this.documentCollectionCellAnfang;
        while (navigator.getNext() != null) {
            navigator = navigator.getNext();
            size++;
        }
        return size;
    }

    public Document getFirst() {
        if (this.documentCollectionCellAnfang == null) {
            return null;
        }
        return this.documentCollectionCellAnfang.getDocument();
    }

    public Document getLast() {
        if (this.documentCollectionCellAnfang == null) {
            return null;
        }
        DocumentCollectionCell zeiger = this.documentCollectionCellAnfang;
        while (zeiger.getNext() != null) {
            zeiger = zeiger.getNext();
        }
        return zeiger.getDocument();
    }

    public void removeFirst() {
        if (this.documentCollectionCellAnfang == null) {
            return;
        }
        this.documentCollectionCellAnfang = this.documentCollectionCellAnfang.getNext();
    }

    public void removeLast() {
        if (this.documentCollectionCellAnfang == null) {
            return;
        }

        if (this.documentCollectionCellAnfang.getNext() == null) {
            this.documentCollectionCellAnfang = null;
        }

        DocumentCollectionCell navigator = this.documentCollectionCellAnfang;
        while (navigator.getNext().getNext() != null) {
            navigator = navigator.getNext();
        }
        navigator.setNext(null);
    }

    public boolean remove(int index) {
        if (this.documentCollectionCellAnfang == null || index < 0 || index >= this.size()) {
            return false;
        }

        if (this.documentCollectionCellAnfang.getNext() == null) {
            this.documentCollectionCellAnfang = null;
            return true;
        }

        int cellIndex = 0;
        DocumentCollectionCell navigator = this.documentCollectionCellAnfang;

        while (cellIndex < index - 1) {
            cellIndex++;
            navigator = navigator.getNext();
        }
        navigator.setNext(navigator.getNext().getNext());
        return true;
    }

    // gibt document an der Stelle index in der Liste zurück;
    public Document get(int index) {
        if (this.documentCollectionCellAnfang == null || index < 0 || index >= this.size()) {
            return null;
        }

        if (this.documentCollectionCellAnfang.getNext() == null) {
            return this.documentCollectionCellAnfang.getDocument();
        }

        int cellIndex = 0;
        DocumentCollectionCell navigator = this.documentCollectionCellAnfang;

        while (cellIndex < index) {
            cellIndex++;
            navigator = navigator.getNext();
        }
        return navigator.getDocument();
    }

    // gibt index vom doc zurück, falls doc in der Liste enthalten ist;
    public int indexOf(Document doc) {
        if (doc == null || this.documentCollectionCellAnfang == null) {
            return -1;
        }
        DocumentCollectionCell navigator = this.documentCollectionCellAnfang;
        int cellIndex = 0;

        while (cellIndex < this.size()) {
            if (doc.equals(navigator.getDocument())) {
                return cellIndex;
            }
            cellIndex++;
            navigator = navigator.getNext();
        }
        return -1;
    }

    // checkt ob ein Document in der Liste enthalten ist;
    public boolean contains(Document doc) {
        return indexOf(doc) != -1;
    }

    // erstellt ein WCA mit unique Wörtern aus allen WCAs der Dokumente der Liste;
    private WordCountArray allWords() {
        if (this.documentCollectionCellAnfang == null) {
            return null;
        }
        WordCountArray collectiveWCA = new WordCountArray(10);

        DocumentCollectionCell navigator = this.documentCollectionCellAnfang;
        int cellIndex = 0;
        while (cellIndex < this.size()) {
            cellIndex++;
            //for-loop für Wörter im nächsten WCA
            for (int i = 0; i < navigator.getDocument().getWordCountArray().size(); i++) {
                collectiveWCA.add(navigator.getDocument().getWordCountArray().getWord(i), 0);
            }
            navigator = navigator.getNext();
        }
        return collectiveWCA;
    }

    // fügt zu allen WCAs die fehlenden Wörter aus allWords mit count 0;
    private void addWordsToDocumentsWithCountZero() {
        if (this.documentCollectionCellAnfang == null || this.allWords() == null) {
            return;
        }
        int cellIndex = 0;
        DocumentCollectionCell navigator = this.documentCollectionCellAnfang;
        /*System.out.println("ADDWORDS (anfang) size of DocumentCollection = " + this.size());
        System.out.println("ADDWORDS (anfang) wca 0 sieht so aus: " + this.get(0).getWordCountArray().toString());
        System.out.println("ADDWORDS (anfang) wca 1 sieht so aus: " + this.get(1).getWordCountArray().toString());
        System.out.println("ADDWORDS (anfang) wca 2 sieht so aus: " + this.get(2).getWordCountArray().toString());
        System.out.println("ADDWORDS (anfang) wca 3 sieht so aus: " + this.get(3).getWordCountArray().toString());
        System.out.println("ADDWORDS (anfang) AllWords sieht so aus: " + this.allWords());*/
        //while-loop for list elements of DocumentCollection
        while (cellIndex < this.size()) {
            cellIndex++;
            //for-loop für Wörter in allWords
            for (int i = 0; i < this.allWords().size(); i++) {
                //System.out.println(navigator.getDocument().getWordCountArray());
                navigator.getDocument().getWordCountArray().add(this.allWords().getWord(i), 0);

            }
            navigator = navigator.getNext();
        }
        /*System.out.println("ADDWORDS (ende) wca 0 sieht so aus: " + this.get(0).getWordCountArray().toString());
        System.out.println("ADDWORDS (ende) wca 1 sieht so aus: " + this.get(1).getWordCountArray().toString());
        System.out.println("ADDWORDS (ende) wca 2 sieht so aus: " + this.get(2).getWordCountArray().toString());
        System.out.println("ADDWORDS (ende) wca 3 sieht so aus: " + this.get(3).getWordCountArray().toString());*/
    }

    //berechnet Similarity to query für alle Dokumente und sortiert nach Similarity
    public void match(String query) {
        if (this.documentCollectionCellAnfang == null || this.allWords() == null|| query == null) {
            return;
        }
        /*System.out.println("MATCH (anfang) array all words: " + this.allWords().toString());
        System.out.println("MATCH (anfang) wca doc 0: " + this.get(0).getWordCountArray().toString());
        System.out.println("MATCH (anfang) wca doc 1: " + this.get(1).getWordCountArray().toString());
        System.out.println("MATCH (anfang) wca doc 2: " + this.get(2).getWordCountArray().toString());*/
        //erstelle ein neues Document mit einem Array mit unique Wörtern aus String query
        LinkedDocument docQuery = new LinkedDocument("query", null, null, null, null, query, "query");

        // addiere query an der ersten Stelle in der Liste
        this.addFirst(docQuery);
        //System.out.println("MATCH (mitte) array all words: " + this.allWords().toString());

        //jedem document werden fehlende wörter aus anderen documenten mit Häufigkeit null hinzugefügt
        this.addWordsToDocumentsWithCountZero();
        /*System.out.println("MATCH (mitte) wca doc 0: " + this.get(0).getWordCountArray().toString());
        System.out.println("MATCH (mitte) wca doc 1: " + this.get(1).getWordCountArray().toString());
        System.out.println("MATCH (mitte) wca doc 2: " + this.get(2).getWordCountArray().toString());
        System.out.println("MATCH (mitte) wca doc 3: " + this.get(3).getWordCountArray().toString());*/
        //speichere similarity in SimilarityToQuery
        int cellIndex = 0;
        DocumentCollectionCell navigator = this.documentCollectionCellAnfang;
        while (cellIndex < this.size()) {
            cellIndex++;
            navigator.getDocument().getWordCountArray().sort();
            //System.out.println("MATCH (before sort) wca doc " + (cellIndex - 1) + " " + this.get(cellIndex - 1).getWordCountArray().toString());
            //navigator.setSimilarityToQuery(this.getFirst().getWordCountArray().similarity(navigator.getDocument().getWordCountArray()));
            //navigator.setSimilarityToQuery(navigator.getDocument().getWordCountArray().similarity(this.getFirst().getWordCountArray()));
            navigator.setSimilarityToQuery(navigator.getDocument().getWordCountArray().similarity(this.getFirst().getWordCountArray(),this));
            //System.out.println("MATCH (after sort) wca doc " + (cellIndex - 1) + " " + this.get(cellIndex - 1).getWordCountArray().toString());
            navigator = navigator.getNext();
        }
        //System.out.println("MATCH ende size of this (testCollection): " + this.size());
        //löschen von docQuery:
        this.removeFirst();
        //System.out.println("MATCH ende First removed size of this (testCollection): " + this.size());

        this.sortBySimilarity();
        //System.out.println("MATCH ende ende size of this (testCollection): " + this.size());

    }

    public double getQuerySimilarity(int index) {
        if (this.documentCollectionCellAnfang == null || index < 0 || index >= this.size()) {
            return -1;
        }
        if (this.documentCollectionCellAnfang.getNext() == null) {
            return this.documentCollectionCellAnfang.getSimilarityToQuery();
        }

        int cellIndex = 0;
        DocumentCollectionCell navigator = this.documentCollectionCellAnfang;

        while (cellIndex < index) {
            cellIndex++;
            navigator = navigator.getNext();
        }
        return navigator.getSimilarityToQuery();
    }

    private void sortBySimilarity() {
        for (int cellIndex1 = 0; cellIndex1 < this.size() - 1; cellIndex1++) {
            DocumentCollectionCell navigator = this.documentCollectionCellAnfang;
            for (int cellIndex2 = 0; cellIndex2 < this.size() - cellIndex1 - 1; cellIndex2++) {
                if (navigator.getSimilarityToQuery() < navigator.getNext().getSimilarityToQuery()) {
                    Document tempDoc = navigator.getDocument();
                    double tempSimilarity = navigator.getSimilarityToQuery();
                    navigator.setSimilarityToQuery(navigator.getNext().getSimilarityToQuery());
                    navigator.setDocument(navigator.getNext().getDocument());
                    navigator.getNext().setDocument(tempDoc);
                    navigator.getNext().setSimilarityToQuery(tempSimilarity);
                }
                navigator = navigator.getNext();
            }

        }
    }

    // berechnet Anzahl der Dokumente, wo word >= 1 Mal vorkommt
    public int noOfDocumentsContainingWord(String word) {
        int noOfDocuments = 0;
        if (word == null) {
            return -1;
        }
        for (int i = 0; i < this.size(); i++) {
            WordCountArray wcai = this.get(i).getWordCountArray();
            if (wcai.getIndex(word) > -1) {
                noOfDocuments++;
            }
        }
        return noOfDocuments;
    }

    //Hilfsmethode für sortByRelevance
    public double getRelevance(int index) {
        if (this.documentCollectionCellAnfang == null || index < 0 || index >= this.size()) {
            return -1;
        }
        if (this.documentCollectionCellAnfang.getNext() == null) {
            return this.documentCollectionCellAnfang.getRelevance();
        }

        int cellIndex = 0;
        DocumentCollectionCell navigator = this.documentCollectionCellAnfang;

        while (cellIndex < index) {
            cellIndex++;
            navigator = navigator.getNext();
        }
        return navigator.getRelevance();
    }
}
