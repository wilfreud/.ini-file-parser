import types.Document;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        final String filePath = "source.ini";
        final Document doc = new Document();
        File file = null;

//        Try to open file
        try {
            file = new File(filePath);
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

//        Double check if file is legit
        if (!file.isFile()) {
            System.err.println(filePath + " is not a file");
            System.exit(-1);
        }

//      Trying to make a buffer out of the file
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        String line;

        try {
            while ((line = br.readLine()) != null) {
                doc.parseLine(line.strip());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        System.out.println("Finished parsing");
        System.out.println(doc.getStats());

        doc.traverse();
    }
}