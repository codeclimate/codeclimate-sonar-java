package support;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class File {
    public static String read(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
}
