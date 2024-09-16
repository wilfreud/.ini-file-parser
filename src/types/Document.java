package types;

import java.util.HashMap;

public class Document {
    private short sectionsCount = 0;
    private short commentCount = 0;
    private short kvpCount = 0;

    private final String DEFAULT_CATEGORY = "uncategorized";
    private final HashMap<String, Section> documentMap = new HashMap<>();

    private Section section;

    public Document() {
        this.section = new Section(DEFAULT_CATEGORY);
        documentMap.put(DEFAULT_CATEGORY, this.section);
    }

    private short outOfSectionDetector = 0;

    public void parseLine(String line) {
//        Check out for out-of-context KVPs
        if (line.isEmpty() && !this.section.getSectionName().equals(DEFAULT_CATEGORY)) {
            ++this.outOfSectionDetector;
        } else {
            this.outOfSectionDetector = 0;
        }

        if (this.outOfSectionDetector == 2) {
            this.section = this.documentMap.get(DEFAULT_CATEGORY);
            this.outOfSectionDetector = 0;
        }

//        First check: section
        if (this.isSection(line)) {
            final String sectionName = line.substring(1, line.length() - 1);
            this.section = new Section(sectionName);
            this.documentMap.put(sectionName, this.section);
            this.sectionsCount++;
            return;
        }

//        Second check: comment
        if (this.isComment(line)) {
            this.commentCount++;
            this.section.addComment(new Comment(line));
            return;
        }

//        Third check: Key-Value
        if (this.isKeyValuePair(line)) {
            String[] kv = line.split("=");
            KVPair kvp = new KVPair(kv[0], kv[1]);
            this.section.addKVPair(kvp);
            this.kvpCount++;
        }
    }

    public String getStats() {
        return "Sections: " + sectionsCount + "\nComments: " + commentCount + "\nKey-Values: " + kvpCount;
    }

    private boolean isComment(String line) {
        return line.startsWith("#") || line.startsWith(";");
    }

    private boolean isSection(String line) {
        return line.startsWith("[") && line.endsWith("]");
    }

    private boolean isKeyValuePair(String line) {
        String[] s = line.split("=");
        return s.length == 2;
    }

    public void traverse() {
        for (Section section : documentMap.values()) {
            System.out.println("[ Section: " + section.getSectionName() + " ]");
            section.traverseComments();
            section.traverseKVPaires();
        }
    }
}
