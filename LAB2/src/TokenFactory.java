public class TokenFactory {
    public static Token getToken(TokenType tokenType) {
        switch (tokenType) {
            case ID:
                return new Token(tokenType.code, "id");
            case L_BRACE:
                return new Token(tokenType.code, "(");
            case PLUS:
                return new Token(tokenType.code, "+");
            case NUM:
                return new Token(tokenType.code, "num");
            case E1:
                return new Token(tokenType.code, "E'");
            case T1:
                return new Token(tokenType.code, "T'");
            case C1:
                return new Token(tokenType.code, "C'");
            case DIV:
                return new Token(tokenType.code, "/");
            case MUL:
                return new Token(tokenType.code, "*");
            case MINUS:
                return new Token(tokenType.code, "-");
            case SEMICOLON:
                return new Token(tokenType.code, ";");
            case R_BRACE:
                return new Token(tokenType.code, ")");
            case EQUAL:
                return new Token(tokenType.code, "=");
            case IF:
                return new Token(tokenType.code, "if");
            case ELSE:
                return new Token(tokenType.code, "else");
            case WHILE:
                return new Token(tokenType.code, "while");
            case AND:
                return new Token(tokenType.code, "&&");
            case OR:
                return new Token(tokenType.code, "||");
            case DOUBLE_EQUAL:
                return new Token(tokenType.code, "==");
            case NOT_EQUAL:
                return new Token(tokenType.code, "!=");
            case GREATER_OR_EQUAL:
                return new Token(tokenType.code, ">=");
            case GREATER:
                return new Token(tokenType.code, ">");
            case LESS:
                return new Token(tokenType.code, "<");
            case LESS_OR_EQUAL:
                return new Token(tokenType.code, "<=");
            case L_CURLY_BRACE:
                return new Token(tokenType.code, "{");
            case R_CURLY_BRACE:
                return new Token(tokenType.code, "}");
            default:
                return new Token(tokenType.code, tokenType.name());
        }
    }
}
