package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileHandler {
    private static String storePath = "store";

    public static String[][] parseWithSpaces(String filePath) throws IOException {
        String inputFileString = new String(Files.readAllBytes(Paths.get(filePath)));
        String[] lines = inputFileString.split("\n");
        String[][] result = new String[lines.length - 1][];
        return result;
    }
}