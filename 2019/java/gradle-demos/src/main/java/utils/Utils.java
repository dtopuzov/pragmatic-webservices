package utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("ConstantConditions")
public class Utils {

    public static String readFile(String fileName) throws IOException {
        Path filePath = getResourcePath(fileName);
        byte[] encoded = Files.readAllBytes(filePath);
        return new String(encoded, Charset.defaultCharset());
    }

    public static Path getResourcePath(String fileName) {
        ClassLoader classLoader = Utils.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return Paths.get(file.getAbsolutePath());
    }

    public static String getEnvironmentVariable(String name) {
        return System.getenv(name);
    }
}
