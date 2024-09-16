package types;

import java.util.ArrayList;

public class Section {
    private final String sectionName;

    private final ArrayList<Comment> comments = new ArrayList<>();
    private final ArrayList<KVPair> kvPairs = new ArrayList<>();

    public Section(String sectionName) {
        this.sectionName = sectionName;
    }

    public void addComment(Comment c) {
        comments.add(c);
    }

    public String getSectionName() {
        return sectionName;
    }

    public void addKVPair(KVPair kvp) {
        kvPairs.add(kvp);
    }

    public void traverseKVPaires() {
        for (KVPair kvp : kvPairs) {
            System.out.println(kvp.getKey() + " = " + kvp.getValue());
        }
    }

    public void traverseComments() {
        for (Comment c : comments) {
            System.out.println("# " + c.getValue());
        }
    }
}
