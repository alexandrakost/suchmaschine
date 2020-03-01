import java.io.File;

public class Test {
    public static void main(String[] args) {
        System.out.println(args[0]);

        /*String testTextA = "hello hi";
        String testTextB = "me you are be zu op li jnh you hi";
        Date dummyReleaseA = new Date();
        Author dummyAuthorA = new Author("dummy", "dummy", dummyReleaseA, "dummy", "dummy");
        LinkedDocument a = new LinkedDocument("titelA", "dummy", "dummy", dummyReleaseA, dummyAuthorA, testTextA, "IDa");
        Date dummyReleaseB = new Date();
        Author dummyAuthorB = new Author("dummy", "dummy", dummyReleaseB, "dummy", "dummy");
        LinkedDocument testDocument2 = new LinkedDocument("titelB", "dummy", "dummy", dummyReleaseB, dummyAuthorB, testTextB, "IDb");
        System.out.println(testDocument.getWordCountArray().toString());
        System.out.println(testDocument.findOutgoingIDs(testText)[0]);*/


        LinkedDocumentCollection testCollection = new LinkedDocumentCollection();
        //System.out.println("Colection Size am Anfang: " + testCollection.size());
        boolean running = true;
        while (running) {
            String eingabe = Terminal.askString("Enter command: ");

            if (eingabe.substring(0, 4).equals("exit")) {
                System.out.println("Method terminated");
                break;
            }
            // methode add
            if (eingabe.substring(0, 3).equals("add")) {
                processAdd(testCollection, eingabe);
            }
            //methode list
            if (eingabe.length() >= 4 && eingabe.substring(0, 4).equals("list")) {
                //System.out.println("in case of list");
                processList(testCollection);
            }
            //methode count word
            //gehe liste durch, gehe wca durch, suche nach dem word (get count(getIndex(word)));
            if (eingabe.length() >= 5 && eingabe.substring(0, 5).equals("count")) {
                processCount(testCollection, eingabe);
            }
            // methode query suchanfrage
            if (eingabe.length() >= 5 && eingabe.substring(0, 5).equals("query")) {
                String suchanfrage = eingabe.substring(6);
                System.out.println(suchanfrage);
                //sortiere die liste nach relevanz
                testCollection.publicSortByRelevance(suchanfrage,  0.85, 0.6);
                // nummerierte liste ausgeben:
                System.out.println("QUERY size testCollection: " + testCollection.size());
                for (int i = 0; i < testCollection.size(); i++) {
                    System.out.println(i + ". Titel: " + testCollection.get(i).getTitle() + ", Relevanz: " + testCollection.getRelevance(i));
                }
            }
            // methode pageRank - liste von Titeln und Pageranks ausgeben (nicht sortiert):
            if (eingabe.length() >= 8 && eingabe.substring(0, 8).equals("pageRank")) {

                for (int i = 0; i < testCollection.size(); i++) {
                    System.out.println("Titel: " + testCollection.get(i).getTitle() + ", PageRank: " +
                            testCollection.pageRank(0.85)[i]);
                }
            }
            //methode crawl
            if (eingabe.length() >= 5 && eingabe.substring(0, 5).equals("crawl")) {
                testCollection = testCollection.crawl();
            }
        }
    }

    private static void processList(LinkedDocumentCollection testCollection) {
        for (int i = 0; i < testCollection.size(); i++) {
            System.out.println(testCollection.get(i).getTitle());
        }
    }

    private static void processAdd(LinkedDocumentCollection testCollection, String eingabe) {
        //trennt Titel vom fließtext
        //finde an welcher Stelle in der Eingabe : ist
        int trennung = eingabe.indexOf(":");

        //substring(int beginIndex = 0, int endIndex)
        String testTitel = eingabe.substring(4, trennung);
        //nun sind im Text link: drin, die solen nicht als wörter behandelt werden. ID nach dem link soll nicht zu kleintext umgewandelt werden
        String testText = eingabe.substring(trennung + 1);

        // erstelle ein neues Dokument mit Eingaben von Terminal der zur DocumentCollection hinzugefügt wird;
        Date dummyRelease = new Date();
        Author dummyAutor = new Author("dummy", "dummy", dummyRelease, "dummy", "dummy");
        LinkedDocument testDocument = new LinkedDocument(testTitel, "dummy", "dummy", dummyRelease, dummyAutor, testText, testTitel);

        // füge dieses Dokument der Document collection hinzu
        testCollection.addLast(testDocument);
        //System.out.println("Titel von dem zu addieredem Dokument: " + testDocument.getTitle());
        //System.out.println("Collection Size nach hinzufügen vom Dokument: " + testCollection.size());
        //System.out.println("Titel check: " + testCollection.get(testCollection.size()-1).getTitle());
    }

    private static void processCount(LinkedDocumentCollection testCollection, String eingabe) {
        String word = eingabe.substring(6);
        Document tempDoc;
        for (int i = 0; i < testCollection.size(); i++) {
            tempDoc = testCollection.get(i);
            int index = tempDoc.getWordCountArray().getIndex(word);
            if (index == -1) {
                System.out.println("Das angegebene Wort kommt nicht in dem Dokument " + tempDoc.getTitle() + " vor.");
                continue;
            }
            int count = tempDoc.getWordCountArray().getCount(index);
            System.out.println("Das angegebene Wort kommt " + count + " Mal in dem Dokument " + tempDoc.getTitle() + " vor.");
        }

        /*WordCountArray test = new WordCountArray(10);
        System.out.println("ARRAY BEGIN: " + test.toString());
        test.add("hello", 1);
        System.out.println("ARRAY 1: " + test.toString());
        test.add("hi", 2);
        System.out.println("ARRAY 2: " + test.toString());
        test.add("hi", 2);
        System.out.println("ARRAY 3: " + test.toString());
        test.add("hello", 1);
        System.out.println("ARRAY 4: " + test.toString());*/

    }
}
