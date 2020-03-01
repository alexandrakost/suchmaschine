
public class DocumentCollectionCell {

    private Document document;
    private DocumentCollectionCell next;
    private double similarityToQuery;
    private DocumentCollectionCell previous;
    private double relevance;

    public DocumentCollectionCell (Document konkDocument, DocumentCollectionCell documentCollectionCell) {
        this.document = konkDocument;
        this.next = documentCollectionCell;
        this.previous = null;
    }

    public  DocumentCollectionCell (Document konkDocument) {
        this.document = konkDocument;
        this.next = null;
        this.previous = null;
    }

    public DocumentCollectionCell getNext() {
        return next;
    }

    public void setNext(DocumentCollectionCell next) {
        this.next = next;
    }

    public DocumentCollectionCell getPrevious() {
        return previous;
    }

    public void setPrevious(DocumentCollectionCell previous) {
        this.previous = previous;
    }

    public Document getDocument() { return document; }

    public void setDocument(Document document) {
        this.document = document;
    }

    public double getSimilarityToQuery() {
        return similarityToQuery;
    }

    public void setSimilarityToQuery(double similarityToQuery) {
        this.similarityToQuery = similarityToQuery;
    }

    public double getRelevance() {
        return relevance;
    }

    public void setRelevance(double relevance) {
        this.relevance = relevance;
    }

}

