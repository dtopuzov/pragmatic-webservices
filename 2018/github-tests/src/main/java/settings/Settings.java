package settings;

import utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

public class Settings {
    public static final String BASE_API_URL = getProperty("baseApiUrl", "https://api.github.com");
    public static final String BASE_WEB_URL = getProperty("baseWebUrl", "https://github.com");
    public static final String REPO = getProperty("repo", "test");
    public static final String USER = getProperty("user", "ws-test-user");
    public static final String PASS = getProperty("pass", "you-know-it");

    private static String getProperty(String property, String defaultValue) {
        try {
            return readProperties().getProperty(property, defaultValue);
        } catch (IOException e) {
            return defaultValue;
        }
    }

    private static Properties readProperties() throws IOException {
        Path filePath = Utils.getResourcePath("config" + File.separator + "TEST.properties");
        InputStream input = new FileInputStream(filePath.toString());
        Properties prop = new Properties();
        prop.load(input);
        return prop;
    }
}
