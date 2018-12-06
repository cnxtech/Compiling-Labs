public class Token {
    private int code;
    private String value;
    private String error;
    private String type;
    private int lineNum;

    public Token(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public boolean isTerminal() {
        return code < 100;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(value);
        if (error == null) {
            builder.append("\t").append("<").append(type).append(">");
        } else {
            builder.append("Error: ").append(error).append(" '").append(value).append("' at row ").append(lineNum);
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        Token token = (Token)obj;
        return token.getCode() == code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }
}
