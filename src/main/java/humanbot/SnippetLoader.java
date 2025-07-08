package humanbot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class SnippetLoader {
    public List<String> loadSnippets() throws Exception {
        List<String> snippets = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/snippets.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                snippets.add(line.trim());
            }
        }
        br.close();
        return snippets;
    }
}
