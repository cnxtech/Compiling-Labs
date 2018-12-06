import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lioder at 2018/10/30
 */
public class Analyzer {
    private State state = State.START;
    private StringBuilder output = new StringBuilder();
    private StringBuilder token = new StringBuilder();
    private boolean putBackFlag = false;
    private char putBackChar;
    private int lineNum = 1;
    private int number = 0;

    public void analyze() {
        JavaReader javaReader = new JavaReader();

        while (true) {
            char c;
            if (putBackFlag) {
                putBackFlag = false;
                c = putBackChar;
            } else {
                c = javaReader.readChar();
            }

            if (c == 0) {
                break;
            }

            if (c == '\n') {
                lineNum ++;
            }

            switch (state) {
                case START:
                    if (c == ' ' || c == '\r' || c == '\n' || c == '\t') {
                        continue;
                    } else if (letter(c)) {
                        state = State.FINAL_LETTER;
                        token.append(c);
                    } else if (digit(c)) {
                        state = State.FINAL_INT;
                        number += (c - '0');
                        token.append(c);
                    } else {
                        switch (c) {
                            case '+':
                                state = State.TRAN_ADD;
                                token.append(c);
                                break;
                            case '-':
                                state = State.TRAN_SUB;
                                token.append(c);
                                break;
                            case '&':
                                state = State.TRAN_AND;
                                token.append(c);
                                break;
                            case '|':
                                state = State.TRAN_OR;
                                token.append(c);
                                break;
                            case '>':
                                state = State.TRAN_G;
                                token.append(c);
                                break;
                            case '<':
                                state = State.TRAN_L;
                                token.append(c);
                                break;
                            case '*':
                            case '/':
                                state = State.TRAN_SLASH;
                                token.append(c);
                                break;
                            case '%':
                            case '!':
                            case '=':
                                state = State.TRAN_OP;
                                token.append(c);
                                break;
                            case '?':
                            case ':':
                            case '~':
                            case '^':
                            case '.':
                                token.append(c);
                                recordToken("operator");
                                break;
                            case ';':
                            case '(':
                            case ')':
                            case '[':
                            case ']':
                            case '{':
                            case '}':
                                token.append(c);
                                recordToken("delimiter");
                                break;
                            case '\"':
                                state = State.TRAN_DOUBLE_QUOTE;
                                token.append(c);
                                break;
                            case '\'':
                                state = State.TRAN_SINGLE_QUOTE;
                                token.append(c);
                                break;
                            default:
                                token.append(c);
                                recordToken("Undefined character", lineNum);
                        }
                    }
                    break;
                case FINAL_LETTER:
                    if (letter(c) || digit(c)) {
                        token.append(c);
                    } else {
                        state = State.START;
                        if (keyword(token.toString())) {
                            recordToken("keyword");
                        } else {
                            recordToken("identifier");
                        }
                        putBack(c);
                    }
                    break;
                case FINAL_INT:
                    if (c == '.') {
                        state = State.TRAN_DOT;
                        token.append(c);
                        number = 0;
                    } else if (digit(c)) {
                        token.append(c);
                        number = number * 10 + (c - '0');
                    } else {
                        if (number < 0) {
                            recordToken("Too large int", lineNum);
                        } else {
                            recordToken("int");
                        }
                        state = State.START;
                        putBack(c);
                        number = 0;
                    }
                    break;
                case TRAN_DOT:
                    if (digit(c)) {
                        state = State.FINAL_DOUBLE;
                        token.append(c);
                    }
                    break;
                case FINAL_DOUBLE:
                    if (digit(c)) {
                        token.append(c);
                    } else {
                        state = State.START;
                        recordToken("double");
                        putBack(c);
                    }
                    break;
                case TRAN_ADD:
                    if (c == '=' || c == '+') {
                        token.append(c);
                        state = State.START;
                        recordToken("operator");
                    } else {
                        recordToken("operator");
                        state = State.START;
                        putBack(c);
                    }
                    break;
                case TRAN_SUB:
                    if (c == '=' || c == '-') {
                        token.append(c);
                        state = State.START;
                        recordToken("operator");
                    } else {
                        recordToken("operator");
                        state = State.START;
                        putBack(c);
                    }
                    break;
                case TRAN_G:
                    if (c == '=' || c == '>') {
                        token.append(c);
                        state = State.START;
                        recordToken("operator");
                    } else {
                        recordToken("operator");
                        state = State.START;
                        putBack(c);
                    }
                    break;
                case TRAN_L:
                    if (c == '=' || c == '<') {
                        token.append(c);
                        state = State.START;
                        recordToken("operator");
                    } else {
                        recordToken("operator");
                        state = State.START;
                        putBack(c);
                    }
                    break;
                case TRAN_AND:
                    if (c == '&') {
                        token.append(c);
                        state = State.START;
                        recordToken("operator");
                    } else {
                        recordToken("operator");
                        state = State.START;
                        putBack(c);
                    }
                    break;
                case TRAN_OR:
                    if (c == '|') {
                        token.append(c);
                        state = State.START;
                        recordToken("operator");
                    } else {
                        recordToken("operator");
                        state = State.START;
                        putBack(c);
                    }
                    break;
                case TRAN_SLASH:
                    if (c == '=') {
                        token.append(c);
                        state = State.START;
                        recordToken("operator");
                    } else if (c == '*') {
                        token.append(c);
                        state = State.TRAN_STAR_ANNOTATION_BEGIN;
                    } else if (c == '/') {
                        token.append(c);
                        state = State.TRAN_DOUBLE_SLASH;
                    } else {
                        recordToken("operator");
                        state = State.START;
                        putBack(c);
                    }
                    break;
                case TRAN_OP:
                    if (c == '=') {
                        token.append(c);
                        state = State.START;
                        recordToken("operator");
                    } else {
                        recordToken("operator");
                        state = State.START;
                        putBack(c);
                    }
                    break;
                case TRAN_SINGLE_QUOTE:
                    if (c == '\'') {
                        token.append(c);
                        state = State.START;
                        recordToken("character");
                    } else {
                        token.append(c);
                    }
                    break;
                case TRAN_DOUBLE_QUOTE:
                    if (c == '\"') {
                        token.append(c);
                        state = State.START;
                        recordToken("string");
                    } else {
                        token.append(c);
                    }
                    break;
                case TRAN_DOUBLE_SLASH:
                    if (c != '\n') {
                        token.append(c);
                    } else {
                        state = State.START;
                        recordToken("annotation");
                    }
                    break;
                case TRAN_STAR_ANNOTATION_BEGIN:
                    if (c == '*') {
                        token.append(c);
                        state = State.TRAN_STAR_IN_ANNOTATION;
                    } else {
                        token.append(c);
                    }
                    break;
                case TRAN_STAR_IN_ANNOTATION:
                    if (c == '/') {
                        token.append(c);
                        state = State.START;
                        recordToken("annotation");
                    } else if (c == '*'){
                        token.append(c);
                    } else {
                        state = State.TRAN_STAR_ANNOTATION_BEGIN;
                        token.append(c);
                    }
                    break;
            }
        }
    }

    private static boolean letter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private static boolean digit(char c) {
        return (c >= '0' && c <= '9');
    }

    private static boolean keyword(String word) {
        String[] keywords = new String[]{"public", "protected", "private",
                "abstract", "class", "interface", "implements", "extends",
                "new", "if", "else", "instanceof", "switch", "case", "break",
                "default", "for", "do", "while", "continue", "return", "goto",
                "static", "final", "const", "native", "synchronized", "transient",
                "volatile", "strictfp", "try", "catch", "finally", "throw", "throws",
                "import", "package", "byte", "short", "int", "long", "float", "double",
                "char", "boolean", "super", "this", "void", "assert", "enum"};

        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return keywordSet.contains(word);
    }

    private void recordToken(String type) {
        output.append(token.toString());
        output.append("\t").append("<").append(type).append(">");
        output.append(System.lineSeparator());
        token = new StringBuilder();
    }

    private void recordToken(String error, int lineNum) {
        output.append("Error: ").append(error).append(" '").append(token.toString()).append("' at row ").append(lineNum);
        output.append(System.lineSeparator());
        token = new StringBuilder();
    }

    private void putBack(char c) {
        putBackChar = c;
        putBackFlag = true;
    }

    public void exportTokens() {
        String s = output.toString();
        System.out.println(s);
        File file = new File("output.txt");
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(s);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
