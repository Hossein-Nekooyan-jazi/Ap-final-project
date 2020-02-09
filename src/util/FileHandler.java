package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileHandler {

    public static String[][] parseWithSpaces(String filePath) throws IOException {
        String inputFileString = new String(Files.readAllBytes(Paths.get(filePath)));
        String[] lines = inputFileString.split("\n");
        String[][] result = new String[lines.length][];
        for(int i=0 ; i<lines.length ; i++) {
            result[i] = new String[lines[i].length()];
            for (int j = 0; j < lines[i].length(); j++)
                result[i][j] = lines[i].substring(j, j+1);
        }
        return result;
    }
}