import java.util.ArrayList;

public class TreeNode {
    public Token token;
    public ArrayList<TreeNode> children;

    public TreeNode(Token token) {
        this.token = token;
        this.children = new ArrayList<>();
    }

    public String toString(String lftStr, String append) {
        StringBuilder b = new StringBuilder();
        b.append(append + token.getValue());
        b.append("\n");
        if (children.size() > 0) {
            for (int i = 0; i < children.size() - 1; i++) {
                b.append(lftStr+children.get(i).toString(lftStr + "│   ", "├── "));
            }
            b.append(lftStr + children.get(children.size() - 1).toString(lftStr + "    ", "└── "));

        }
        return b.toString();
    }
}
