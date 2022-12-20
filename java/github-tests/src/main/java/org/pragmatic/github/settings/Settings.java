package org.pragmatic.github.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.pragmatic.utils.OS;

public class Settings {
    public static final String BASE_API_URL = getProperty("baseApiUrl", "https://api.github.com");
    public static final String BASE_WEB_URL = getProperty("baseWebUrl", "https://github.com");
    public static final String REPO = getProperty("repo", OS.getEnvironmentVariable("GITHUB_REPO"));
    public static final String USER = getProperty("user", OS.getEnvironmentVariable("GITHUB_USER"));
    public static final String TOKEN = getProperty("pass", OS.getEnvironmentVariable("GITHUB_TOKEN"));

    private static String getProperty(String property, String defaultValue) {
        try {
            return readProperties().getProperty(property, defaultValue);
        } catch (IOException e) {
            return defaultValue;
        }
    }

    @SuppressWarnings("DataFlowIssue")
    private static Properties readProperties() throws IOException {
        String configFile = OS.getEnvironmentVariable("CONFIG_FILE", "live");

        ClassLoader classLoader = Settings.class.getClassLoader();
        File file = new File(classLoader.getResource(configFile + ".properties").getFile());
        InputStream input = new FileInputStream(file);
        Properties prop = new Properties();
        prop.load(input);
        return prop;
    }
}