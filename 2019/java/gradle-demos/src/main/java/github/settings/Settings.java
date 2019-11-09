package github.settings;

import utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

public class Settings {
    public static final String CONFIG = getProperty("CONFIG", "DEFAULT");
    public static final String BASE_API_URL = getProperty("baseApiUrl", "https://api.github.com");
    public static final String BASE_WEB_URL = getProperty("baseWebUrl", "https://github.com");
    public static final String REPO = getProperty("repo", "test");
    public static final String USER = getProperty("user", Utils.getEnvironmentVariable("USER"));
    public static final String PASS = getProperty("pass", Utils.getEnvironmentVariable("PASS"));

    private static String getProperty(String property, String defaultValue) {
        try {
            return readProperties().getProperty(property, defaultValue);
        } catch (IOException e) {
            return defaultValue;
        }
    }

    private static Properties readProperties() throws IOException {
        return readProperties(CONFIG);
    }

    private static Properties readProperties(String fileName) throws IOException {
        Path filePath = Utils.getResourcePath("config" + File.separator + fileName + ".properties");
        InputStream input = new FileInputStream(filePath.toString());
        Properties prop = new Properties();
        prop.load(input);
        return prop;
    }
}
