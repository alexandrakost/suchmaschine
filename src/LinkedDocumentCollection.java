
public class LinkedDocumentCollection extends DocumentCollection {

    @Override
    public void addFirst(Document doc) {
        if (doc instanceof LinkedDocument && !(this.contains(doc))) {
            super.addFirst(doc);
        }
    }

    @Override
    public void addLast(Document doc) {
        if (doc instanceof LinkedDocument && !(this.contains(doc))) {
            super.addLast(doc);
        }
    }

    // wie viele andere LinkedDocuments verweisen auf dieses LDocument?
    // gehe LDC durch, gehe die outcgoingIds von LDs durch, incrementiere den Zähler
    public void calculateIncomingLinks() {
        //Hilfsvariablen
        LinkedDocument ldi;
        for (int i = 0; i < this.size(); i++) {
            ldi = (LinkedDocument) this.get(i);

            LinkedDocument ldj;
            //? wieso kann er DocumentCollCellAnfang ohne get nicht sehen
            for (int j = 0; j < this.size(); j++) {
                // ?der geht zurück zum for-loop for j und inkrementiert j?
                if (i == j) {
                    continue;
                }

                ldj = (LinkedDocument) this.get(j);

                //wenn LDC von OutgoingLinks von LD j contains das LD i
                // der LDC von incomingLinks von LD i wird das LD j hinzugefügt
                if (ldj.getOutgoingLinks().contains(ldi)) {
                    ldi.addIncomingLink(ldj);
                }
            }
        }
    }

    //der LDC werden alle Dokumente aus den OutgoingLinks von enthaltenen Dokumenten hinzugefügt
    public LinkedDocumentCollection crawl() {
        LinkedDocumentCollection resultCollection = new LinkedDocumentCollection();
        crawl(resultCollection);
        return resultCollection;
    }

    //Hilfsmethode für crawl
    private void crawl(LinkedDocumentCollection resultCollection) {
        /*if(resultCollection == null) {
            return;
        }*/
        // fügt alle LDs von this. zu resultCollection
        for (int i = 0; i < this.size(); i++) {
            resultCollection.addLast(this.get(i));
        }

        crawlRecursive(resultCollection);
    }

    //Hilfsmethode für Hilfsmethode für crawl
    private void crawlRecursive(LinkedDocumentCollection resultCollection) {
        int previousSize = resultCollection.size();

        // Verhlaten muster (logik)
        for (int i = 0; i < previousSize; i++) {
            LinkedDocument doci = ((LinkedDocument) resultCollection.get(i));
            for (int j = 0; j < doci.getOutgoingLinks().size(); j++) {
                if (!resultCollection.contains(doci.getOutgoingLinks().get(j))) {
                    resultCollection.addLast(doci.getOutgoingLinks().get(j));
                }
            }
        }

        // Abbruchbedingung
        if (resultCollection.size() == previousSize) {
            return;
        }

        // rekursiven Aufruf
        crawlRecursive(resultCollection);
    }


    private static double[] multiply(double[][] matrix, double[] vector) {
        if (matrix == null || matrix[0] == null || matrix.length != matrix[0].length) {
            return null;
        }

        int mZeilen = matrix.length;
        int mSpalten = matrix[0].length;
        double[] result = new double[mZeilen];
        for (int i = 0; i < mZeilen; i++) {
            for (int j = 0; j < mSpalten; j++) {
                result[i] = result[i] + matrix[i][j] * vector[j];
            }
        }
        return result;
    }

    //gibt den nicht-sortierten pageRank-Vector für LDC zurück
    public double[] pageRank(double dampingFactor) {

        if(dampingFactor < 0 || dampingFactor > 1) {
            return null;
        }

        this.crawl();

        //Übergangsmatrix A mit Gewichtungen
        // erstelle eine leere Matrix A mit Größe this.size x this.size;
        double[][] a = new double[this.size()][this.size()];
        //befülle Matrix A mit 0s
        /*for (int j = 0; j < this.size(); j++) {
            for (int i = 0; i < this.size(); i++) {
                a[i][j] = 0;
            }
        }*/

        double sizeLDC = this.size();

        // gehe alle LDs in LDC durch, betrachte String[] outgoingIDs von dem Dokument, berechne die gewichte
        //for-loop für Spalten von A und somit für LDocs in LDC
        for (int j = 0; j < this.size(); j++) {
            LinkedDocument ldJ = (LinkedDocument) this.get(j);

            //falls LD auf nichts verweist (size von OutgoingLinks = 0), trage überall 1/size von LDC
            if (ldJ.getOutgoingLinks().size() < 1) {
                // for-loop für Elemente von A in den Zeilen von Spalte j
                for (int i = 0; i < this.size(); i++) {
                    //falls Indexes nicht gleich sind (wenn i = j, Gewicht = 0, weil Dokument nicht auf sich selber verweist)
                    if (i != j) {
                        a[i][j] = 1 / sizeLDC;
                    }
                }
            } else {
                // falls OutgoingLinks von LD nicht leer ist
                // for-loop für Elemente von LDC outgoingDocuments von docj und somit für Elemente von A in den Zeilen von Spalte j
                for (int i = 0; i < ldJ.getOutgoingLinks().size(); i++) {
                    //falls Indexes nicht gleich sind (wenn i = j, Gewicht = 0, weil Dokument nicht auf sich selber verweist)
                    if (i != j) {
                        //in welcher Zeile soll das Gewicht vom outgoingDocument i eingetragen werden, bzw auf welcher Stelle in LDC
                        // ist das i-te Element von OutgoingLinks von LinkedDocJ
                        int zeileI = this.indexOf(ldJ.getOutgoingLinks().get(i));
                        //das zu eintragende Gewicht wird berechnet als 1/size von OutgoingLinks() von ldJ
                        double sizeLDJ = ldJ.getOutgoingLinks().size();
                        double gewichtI = 1 / sizeLDJ;
                        a[zeileI][j] = gewichtI;
                    }
                }
            }
        }

        // berechne Matrix M basierend auf A:
        double[][] m = new double[this.size()][this.size()];
        //for-loop für Spalten von m:
        for (int j = 0; j < this.size(); j++) {
            //for-loop für Zeilen von m:
            for (int i = 0; i < this.size(); i++) {
                m[i][j] = dampingFactor * a[i][j] + ((1 - dampingFactor) / sizeLDC);
            }
        }

        //am Anfang ist pageRank vector v = (1/n, 1/n,..., 1/n)
        double[] pageRankVector = new double[this.size()];
        for (int i = 0; i < this.size(); i++) {
            pageRankVector[i] = 1 / sizeLDC;
        }

        //Aufruf der rekursiven Hilfsmethode
        return pageRankRecursive(pageRankVector, m);
    }

    // rekursive Hilfsmethode für pageRank
    private double[] pageRankRecursive(double[] pageRankVector, double[][] m) {

        if (pageRankVector == null || m == null) {
            return null;
        }
        double[] multiplied = multiply(m, pageRankVector);
        //rekursiver Aufruf
        for (int i = 0; i < pageRankVector.length; i++) {
            if (multiplied[i] - pageRankVector[i] > Math.pow(10, -6)) {
                return pageRankRecursive(multiplied, m);
            }
        }

        // Abbruchsfall
        return multiplied;
    }

    //berücksichtigt similarity to query und pageRank zum sortieren
    // ?? wozu braucht man diese return value wenn LDC sortiert wird und man kann relevance an der stell i in LDC zurückgeben
    private double[] sortByRelevance(double dampingFactor, double
            weightingFactor) {

        if(dampingFactor < 0 || dampingFactor > 1 || weightingFactor < 0 || weightingFactor > 1) {
            return null;
        }

        //erstelle den pageRankVector für this DC
        double[] pageRankVector = this.pageRank(dampingFactor);

        //berechne und sette relevance für alle LDs in LDC
        int cellIndex = 0;
        DocumentCollectionCell navigator = this.documentCollectionCellAnfang;
        while (cellIndex < this.size()) {
            double relevanceCell = weightingFactor * this.getQuerySimilarity(cellIndex) +
                    (1 - weightingFactor) * pageRankVector[cellIndex];
            navigator.setRelevance(relevanceCell);
            navigator = navigator.getNext();
            cellIndex++;
        }

        //sortiere wie in sortBySimilarity
        for (int cellIndex1 = 0; cellIndex1 < this.size() - 1; cellIndex1++) {
            DocumentCollectionCell navigator1 = this.documentCollectionCellAnfang;
            for (int cellIndex2 = 0; cellIndex2 < this.size() - cellIndex1 - 1; cellIndex2++) {
                if (navigator1.getRelevance() < navigator1.getNext().getRelevance()) {
                    LinkedDocument tempLinkDoc = (LinkedDocument) navigator1.getDocument();
                    double tempRelevance = navigator1.getRelevance();
                    navigator1.setRelevance(navigator1.getNext().getRelevance());
                    navigator1.setDocument(navigator1.getNext().getDocument());
                    navigator1.getNext().setDocument(tempLinkDoc);
                    navigator1.getNext().setRelevance(tempRelevance);
                }
                navigator1 = navigator1.getNext();
            }

        }

        // erstelle ein leeres result-array für relevances und befülle das
        double[] relevances = new double[this.size()];

        for (int j = 0; j < this.size(); j++) {
            relevances[j] = this.getRelevance(j);
        }
        //gebe ein Array von relevances zurück
        return relevances;
    }

    //Schnittstelle zur klasse Test, die similarity und PageRank berechnet und LDC nach pageRank sortiert
    public void publicSortByRelevance (String query, double dampingFactor, double weightingFactor) {

        if(query == null || this.documentCollectionCellAnfang == null) {
            return;
        }
        super.match(query);
        this.sortByRelevance(dampingFactor, weightingFactor);
    }
}


