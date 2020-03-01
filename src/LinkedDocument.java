
public class LinkedDocument extends Document {
    private final String ID;
    private String[] outgoingIDs;
    private LinkedDocumentCollection outgoingDocuments = new LinkedDocumentCollection();
    private LinkedDocumentCollection incomingDocuments = new LinkedDocumentCollection();

    //LinkedDocument enthält zusätzlich zu dem Text-Array wie in Document ein Array (Arrays?) mit Links;
    public LinkedDocument(String title, String language, String description, Date releaseDate, Author author,
                          String text, String theID) {
        super(title, language, description, releaseDate, author, text);
        //ID ist der name von dem LinkedDocument
        this.ID = theID;
        //hier werden die Namen der im Text verwiesenen Dokumenten (outgoing Links) gespeichert als String[]
        this.outgoingIDs = this.findOutgoingIDs(text);
        this.setLinksCountZero();
    }

    public String getID() {
        return this.ID;
    }

    @Override
    public boolean equals(Document doc) {
        return (doc instanceof LinkedDocument && ((LinkedDocument) doc).getID().equals(this.getID())) || super.equals(doc);
    }

    //erstellt ein Array mit Links (Namen der Dokumenten), auf die das Dokument verweist im Text
    public static String[] findOutgoingIDs(String text) {
        if (text == null) {
            return null;
        }
        //tokenize den text in ein Array von Strings
        int linksArrayLength = 0;
        String[] splittedText = text.split(" ");
        //ersetze alle elemente die nicht mit link: beginnen durch "";
        for (int i = 0; i < splittedText.length; i++) {

            if (!splittedText[i].contains("link:")) {
                splittedText[i] = "";
                //sonst mach daraus ein substring nut mit dem link
            } else {
                splittedText[i] = splittedText[i].substring(5);
                linksArrayLength++;
            }
        }

        int linksIndex = 0;
        //schreibe die links in in das links-Array rein
        String[] links = new String[linksArrayLength];
        for (int i = 0; i < splittedText.length; i++) {
            if (!splittedText[i].equals("")) {
                links[linksIndex] = splittedText[i];
                linksIndex++;
            }
        }
        return links;
    }

    private void setLinksCountZero() {
        if (this.getWordCountArray() == null) {
            return;
        }
        for (int i = 0; i < this.getWordCountArray().size(); i++) {
            if (this.getWordCountArray().getWord(i).contains("link:")) {
                this.getWordCountArray().setCount(i, 0);
            }
        }
    }

    // checkt ob das LD i (= incominglink) in seinen outgoingDocuments auf das this.LD zeigt. Falls ja, wird es
    // den incomingDocuments von this.LD hinzugefügt
    public void addIncomingLink(LinkedDocument incomingLink) {
        if (this.ID.equals(incomingLink.getID())) {
            return;
        }
        this.incomingDocuments.addLast(incomingLink);
    }

    public static LinkedDocument createLinkedDocumentFromFile(String fileName) {
        String[] fileContent = Terminal.readFile("./src/" + fileName);

        if (fileContent.length < 2) {
            return null;
        }

        Date dummyDate = new Date();
        Author dummyAuthor = new Author("dummy", "dummy", dummyDate,
                "dummy", "dummy");
        LinkedDocument createdDocument = new LinkedDocument(fileContent[0], "dummy", "dummy",
                dummyDate, dummyAuthor, fileContent[1], fileName);
        return createdDocument;
    }

    private void createOutgoingDocumentCollection() {
        for (int i = 0; i < this.outgoingIDs.length; i++) {
            if (!this.outgoingIDs[i].equals(this.getID())) {
                Document outgoingLink = createLinkedDocumentFromFile(this.outgoingIDs[i]);
                this.outgoingDocuments.addLast(outgoingLink);
            }
        }
    }

    //gibt outgoingDocuments zurück
    public LinkedDocumentCollection getOutgoingLinks() {
        // falls LDC null ist
        if (this.outgoingDocuments.getDocumentCollectionCellAnfang() == null) {
            createOutgoingDocumentCollection();
        }
        return this.outgoingDocuments;
    }

    public LinkedDocumentCollection getIncomingLinks() {
        return this.incomingDocuments;
    }

}