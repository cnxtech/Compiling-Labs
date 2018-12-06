public class Main {
    public static void main(String[] args) {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
        lexicalAnalyzer.analyze();

        SyntaxParser syntaxParser = new SyntaxParser(lexicalAnalyzer.getTokens());
        syntaxParser.parse();
    }
}
