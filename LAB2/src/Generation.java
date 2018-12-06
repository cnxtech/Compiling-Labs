public class Generation {
    Token left;
    Token[] right;

    Generation() {}

    Generation(Token left, Token[] right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(left.getValue()).append(" -> ");
        for (Token token : right) {
            builder.append(token.getValue());
        }
        if (right.length == 0) {
            builder.append("ℇ");
        }
        return builder.toString();
    }
}
