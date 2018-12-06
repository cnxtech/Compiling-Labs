public enum TokenType {
    $(100), S(101), E(102), E1(103), T(104), T1(105), F(106),
    C(107), C1(108), D(109), M(110), K(111), P(112),
    NUM(1), ID(2), L_BRACE(3), R_BRACE(4), EQUAL(5), PLUS(6),
    MINUS(7), MUL(8), DIV(9), SEMICOLON(10), IF(11), ELSE(12),
    WHILE(13), L_CURLY_BRACE(14), R_CURLY_BRACE(15), AND(16),
    OR(17), GREATER(18),GREATER_OR_EQUAL(19), LESS(20),
    LESS_OR_EQUAL(21), DOUBLE_EQUAL(22), NOT_EQUAL(23),
    UNRECOGNIZED(-1);
    int code;

    TokenType(int code) {
        this.code = code;
    }
}
