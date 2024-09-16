package types;

public class Comment {
    private final String value;

    public Comment(String comment) {
        this.value = comment.substring(1).strip();
    }

    public String getValue() {
        return value;
    }

}
