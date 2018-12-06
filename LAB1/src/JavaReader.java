import java.io.*;

public class JavaReader {
    private static final String inputFilePath = "input.txt";
    private Reader reader;

    JavaReader() {
        File file = new File(inputFilePath);
        try {
            reader = new InputStreamReader(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public char readChar() {
        try {
            int tempChar;
            if ((tempChar = reader.read()) != -1) {
                return (char) tempChar;
            } else {
                reader.close();
                return (char) 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (char) 0;
    }
}
