import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Main {
    public ArrayList<Integer> finder(String fileName, String substring) {
        int symbolsReaded = 0;
        int j;
        char[] buffer = new char[substring.length()];
        ArrayList<Integer> result = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        try {
            BufferedReader inputData = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8));
            if (inputData.read(buffer, 0, substring.length()) != -1) {
                for (char c : buffer) {
                    str.append(c);
                }
                if((j = str.indexOf(substring)) != -1) result.add(j + symbolsReaded);
                symbolsReaded += substring.length();
            }
            while (inputData.read(buffer, 0, substring.length()) != -1) {
                for (char c : buffer) {
                    str.append(c);
                }
                j = str.indexOf(substring);
                if (j != -1 && !result.contains(j + symbolsReaded - substring.length())) {
                    result.add(j + symbolsReaded - substring.length());
                }
                symbolsReaded += substring.length();
                str.delete(0, substring.length());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}