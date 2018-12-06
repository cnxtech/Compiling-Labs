import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SyntaxParser {
    private StringBuilder output = new StringBuilder();
    private Stack<Token> tokenStack = new Stack<>();
    private List<Token> tokenList;
    private Generation[] generations = new Generation[]{
            new Generation(),
            new Generation(TokenFactory.getToken(TokenType.S),
                    new Token[]{
                            TokenFactory.getToken(TokenType.ID),
                            TokenFactory.getToken(TokenType.EQUAL),
                            TokenFactory.getToken(TokenType.E),
                            TokenFactory.getToken(TokenType.SEMICOLON),
                            TokenFactory.getToken(TokenType.S)
                    }),
            new Generation(TokenFactory.getToken(TokenType.E),
                    new Token[]{
                            TokenFactory.getToken(TokenType.T),
                            TokenFactory.getToken(TokenType.E1)
                    }),
            new Generation(TokenFactory.getToken(TokenType.E1),
                    new Token[]{
                            TokenFactory.getToken(TokenType.PLUS),
                            TokenFactory.getToken(TokenType.T),
                            TokenFactory.getToken(TokenType.E1)
                    }),
            new Generation(TokenFactory.getToken(TokenType.E1),
                    new Token[]{
                            TokenFactory.getToken(TokenType.MINUS),
                            TokenFactory.getToken(TokenType.T),
                            TokenFactory.getToken(TokenType.E1)
                    }),
            new Generation(TokenFactory.getToken(TokenType.E1),
                    new Token[]{}),
            new Generation(TokenFactory.getToken(TokenType.T),
                    new Token[]{
                            TokenFactory.getToken(TokenType.F),
                            TokenFactory.getToken(TokenType.T1),
                    }),
            new Generation(TokenFactory.getToken(TokenType.T1),
                    new Token[]{
                            TokenFactory.getToken(TokenType.MUL),
                            TokenFactory.getToken(TokenType.F),
                            TokenFactory.getToken(TokenType.T1),
                    }),
            new Generation(TokenFactory.getToken(TokenType.T1),
                    new Token[]{
                            TokenFactory.getToken(TokenType.DIV),
                            TokenFactory.getToken(TokenType.F),
                            TokenFactory.getToken(TokenType.T1),
                    }),
            new Generation(TokenFactory.getToken(TokenType.T1),
                    new Token[]{}),
            new Generation(TokenFactory.getToken(TokenType.F),
                    new Token[]{
                            TokenFactory.getToken(TokenType.NUM),
                    }),
            new Generation(TokenFactory.getToken(TokenType.F),
                    new Token[]{
                            TokenFactory.getToken(TokenType.ID),
                    }),
            new Generation(TokenFactory.getToken(TokenType.F),
                    new Token[]{
                            TokenFactory.getToken(TokenType.L_BRACE),
                            TokenFactory.getToken(TokenType.E),
                            TokenFactory.getToken(TokenType.R_BRACE),
                    }),
            new Generation(TokenFactory.getToken(TokenType.S),
                    new Token[]{
                            TokenFactory.getToken(TokenType.IF),
                            TokenFactory.getToken(TokenType.L_BRACE),
                            TokenFactory.getToken(TokenType.C),
                            TokenFactory.getToken(TokenType.R_BRACE),
                            TokenFactory.getToken(TokenType.L_CURLY_BRACE),
                            TokenFactory.getToken(TokenType.S),
                            TokenFactory.getToken(TokenType.R_CURLY_BRACE),
                            TokenFactory.getToken(TokenType.ELSE),
                            TokenFactory.getToken(TokenType.L_CURLY_BRACE),
                            TokenFactory.getToken(TokenType.S),
                            TokenFactory.getToken(TokenType.R_CURLY_BRACE),
                            TokenFactory.getToken(TokenType.S),

                    }),
            new Generation(TokenFactory.getToken(TokenType.S),
                    new Token[]{
                            TokenFactory.getToken(TokenType.WHILE),
                            TokenFactory.getToken(TokenType.L_BRACE),
                            TokenFactory.getToken(TokenType.C),
                            TokenFactory.getToken(TokenType.R_BRACE),
                            TokenFactory.getToken(TokenType.L_CURLY_BRACE),
                            TokenFactory.getToken(TokenType.S),
                            TokenFactory.getToken(TokenType.R_CURLY_BRACE),
                            TokenFactory.getToken(TokenType.S),

                    }),
            new Generation(TokenFactory.getToken(TokenType.S), new Token[]{}),
            new Generation(TokenFactory.getToken(TokenType.C),
                    new Token[]{
                            TokenFactory.getToken(TokenType.D),
                            TokenFactory.getToken(TokenType.C1)
                    }),
            new Generation(TokenFactory.getToken(TokenType.C1),
                    new Token[]{
                            TokenFactory.getToken(TokenType.AND),
                            TokenFactory.getToken(TokenType.D),
                            TokenFactory.getToken(TokenType.C1)
                    }),
            new Generation(TokenFactory.getToken(TokenType.C1),
                    new Token[]{
                            TokenFactory.getToken(TokenType.OR),
                            TokenFactory.getToken(TokenType.D),
                            TokenFactory.getToken(TokenType.C1)
                    }),
            new Generation(TokenFactory.getToken(TokenType.C1),
                    new Token[]{}),
            new Generation(TokenFactory.getToken(TokenType.D),
                    new Token[]{
                            TokenFactory.getToken(TokenType.L_BRACE),
                            TokenFactory.getToken(TokenType.C),
                            TokenFactory.getToken(TokenType.R_BRACE),
                    }),
            new Generation(TokenFactory.getToken(TokenType.M),
                    new Token[]{
                            TokenFactory.getToken(TokenType.K),
                            TokenFactory.getToken(TokenType.P),
                            TokenFactory.getToken(TokenType.K),
                    }),
            new Generation(TokenFactory.getToken(TokenType.K),
                    new Token[]{
                            TokenFactory.getToken(TokenType.NUM)
                    }),
            new Generation(TokenFactory.getToken(TokenType.K),
                    new Token[]{
                            TokenFactory.getToken(TokenType.ID)
                    }),
            new Generation(TokenFactory.getToken(TokenType.P),
                    new Token[]{
                            TokenFactory.getToken(TokenType.GREATER)
                    }),
            new Generation(TokenFactory.getToken(TokenType.P),
                    new Token[]{
                            TokenFactory.getToken(TokenType.GREATER_OR_EQUAL)
                    }),
            new Generation(TokenFactory.getToken(TokenType.P),
                    new Token[]{
                            TokenFactory.getToken(TokenType.LESS)
                    }),
            new Generation(TokenFactory.getToken(TokenType.P),
                    new Token[]{
                            TokenFactory.getToken(TokenType.LESS_OR_EQUAL)
                    }),
            new Generation(TokenFactory.getToken(TokenType.P),
                    new Token[]{
                            TokenFactory.getToken(TokenType.DOUBLE_EQUAL)
                    }),
            new Generation(TokenFactory.getToken(TokenType.P),
                    new Token[]{
                            TokenFactory.getToken(TokenType.NOT_EQUAL)
                    }),
            new Generation(TokenFactory.getToken(TokenType.D),
                    new Token[]{
                            TokenFactory.getToken(TokenType.M)
                    })


    };
    private Map<Integer, Map<Integer, Integer>> M = new HashMap<>();

    SyntaxParser(List<Token> tokenList) {
        this.tokenList = tokenList;
        tokenList.add(TokenFactory.getToken(TokenType.$));

        Map<Integer, Integer> SMap = new HashMap<>();
        SMap.put(TokenType.ID.code, 1);
        SMap.put(TokenType.IF.code, 13);
        SMap.put(TokenType.WHILE.code, 14);
        SMap.put(TokenType.R_CURLY_BRACE.code, 15);
        SMap.put(TokenType.$.code, 15);
        M.put(TokenType.S.code, SMap);

        Map<Integer, Integer> EMap = new HashMap<>();
        EMap.put(TokenType.NUM.code, 2);
        EMap.put(TokenType.ID.code, 2);
        EMap.put(TokenType.L_BRACE.code, 2);
        M.put(TokenType.E.code, EMap);

        Map<Integer, Integer> E1Map = new HashMap<>();
        E1Map.put(TokenType.R_BRACE.code, 5);
        E1Map.put(TokenType.PLUS.code, 3);
        E1Map.put(TokenType.MINUS.code, 4);
        E1Map.put(TokenType.SEMICOLON.code, 5);
        E1Map.put(TokenType.$.code, 5);
        M.put(TokenType.E1.code, E1Map);

        Map<Integer, Integer> TMap = new HashMap<>();
        TMap.put(TokenType.NUM.code, 6);
        TMap.put(TokenType.ID.code, 6);
        TMap.put(TokenType.L_BRACE.code, 6);
        M.put(TokenType.T.code, TMap);

        Map<Integer, Integer> T1Map = new HashMap<>();
        T1Map.put(TokenType.R_BRACE.code, 9);
        T1Map.put(TokenType.PLUS.code, 9);
        T1Map.put(TokenType.MINUS.code, 9);
        T1Map.put(TokenType.SEMICOLON.code, 9);
        T1Map.put(TokenType.$.code, 9);
        T1Map.put(TokenType.MUL.code, 7);
        T1Map.put(TokenType.DIV.code, 8);
        M.put(TokenType.T1.code, T1Map);

        Map<Integer, Integer> FMap = new HashMap<>();
        FMap.put(TokenType.NUM.code, 10);
        FMap.put(TokenType.ID.code, 11);
        FMap.put(TokenType.L_BRACE.code, 12);
        M.put(TokenType.F.code, FMap);

        Map<Integer, Integer> CMap = new HashMap<>();
        CMap.put(TokenType.NUM.code, 16);
        CMap.put(TokenType.ID.code, 16);
        CMap.put(TokenType.L_BRACE.code, 16);
        M.put(TokenType.C.code, CMap);

        Map<Integer, Integer> C1Map = new HashMap<>();
        C1Map.put(TokenType.R_BRACE.code, 19);
        C1Map.put(TokenType.AND.code, 17);
        C1Map.put(TokenType.OR.code, 18);
        M.put(TokenType.C1.code, C1Map);

        Map<Integer, Integer> DMap = new HashMap<>();
        DMap.put(TokenType.L_BRACE.code, 20);
        DMap.put(TokenType.NUM.code, 30);
        DMap.put(TokenType.ID.code, 30);
        M.put(TokenType.D.code, DMap);

        Map<Integer, Integer> MMap = new HashMap<>();
        MMap.put(TokenType.NUM.code, 21);
        MMap.put(TokenType.ID.code, 21);
        M.put(TokenType.M.code, MMap);

        Map<Integer, Integer> KMap = new HashMap<>();
        KMap.put(TokenType.NUM.code, 22);
        KMap.put(TokenType.ID.code, 23);
        M.put(TokenType.K.code, KMap);

        Map<Integer, Integer> PMap = new HashMap<>();
        PMap.put(TokenType.GREATER.code, 24);
        PMap.put(TokenType.GREATER_OR_EQUAL.code, 25);
        PMap.put(TokenType.LESS.code, 26);
        PMap.put(TokenType.LESS_OR_EQUAL.code, 27);
        PMap.put(TokenType.DOUBLE_EQUAL.code, 28);
        PMap.put(TokenType.NOT_EQUAL.code, 29);
        M.put(TokenType.P.code, PMap);
    }

    public void parse() {
        tokenStack.clear();
        tokenStack.push(TokenFactory.getToken(TokenType.$));
        tokenStack.push(TokenFactory.getToken(TokenType.S));
        Token X = tokenStack.peek();

        Stack<TreeNode> treeNodeStack = new Stack<>();
        treeNodeStack.push(new TreeNode(TokenFactory.getToken(TokenType.$)));
        treeNodeStack.push(new TreeNode(X));
        TreeNode XT = treeNodeStack.peek();
        TreeNode root = XT;

        int ip = 0;
        while (TokenType.$.code != X.getCode() && ip < tokenList.size()) {
            Token a = tokenList.get(ip);
            if (a.getCode() == TokenType.UNRECOGNIZED.code) {
                ip++;
                output.append("Error: invalid input \"").append(a.getValue()).append("\"").append(System.lineSeparator());
            }else if (X.equals(a)) {
                ip++;
                tokenStack.pop();
                treeNodeStack.pop();
                TreeNode aT = new TreeNode(a);
                XT.children.add(aT);
            } else if (X.isTerminal()) {
                output.append("Error: invalid input \"").append(a.getValue()).append("\"").append(System.lineSeparator());
            } else if (M.get(X.getCode()) == null || M.get(X.getCode()).get(a.getCode()) == -1) {
                output.append("Error: invalid input \"").append(a.getValue()).append("\"").append(System.lineSeparator());
            } else {
                int generationNo = M.get(X.getCode()).get(a.getCode());
                Generation generation = generations[generationNo];
                output.append(generation.toString()).append(System.lineSeparator());
                tokenStack.pop();
                treeNodeStack.pop();
                for (int i = generation.right.length - 1; i >= 0; i--) {
                    tokenStack.push(generation.right[i]);
                    TreeNode treeNode = new TreeNode(generation.right[i]);
                    XT.children.add(treeNode);
                    treeNodeStack.push(treeNode);
                }
            }
            X = tokenStack.peek();
            XT = treeNodeStack.peek();
        }
        exportParseResult(root);
    }

    public void exportParseResult(TreeNode root) {
        File file = new File("output.txt");
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write("-------- Derivation --------" + System.lineSeparator());
            writer.write(output.toString());
            writer.write("-------- AST --------" + System.lineSeparator());
            writer.write(root.toString("", ""));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
       ;
    }
}
