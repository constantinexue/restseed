package com.constx;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class Configuration {

    private Properties properties;

    private Configuration(Properties properties) {
        this.properties = properties;
    }

    public Properties getProperties() {
        return properties;
    }

    public static Configuration load(String propertiesFilePath) throws Exception {
        // Find at current folder
        File configFile = new File(propertiesFilePath);
        if (!configFile.exists()) {
            // Find class folder
            URL url = Configuration.class.getClassLoader().getResource(propertiesFilePath);
            if (url == null) {
                throw new FileNotFoundException(propertiesFilePath);
            }
            String filePath = url.getFile();
            configFile = new File(filePath);
        }
        if (!configFile.exists()) {
            throw new FileNotFoundException(propertiesFilePath);
        }
        Properties properties = new Properties();
        InputStream is = null;
        try {
            is = FileUtils.openInputStream(configFile);
            properties.load(is);
        }
        finally {
            IOUtils.closeQuietly(is);
        }
        return new Configuration(properties);
    }

    public Integer getInteger(String name) {
        String valueString = getString(name);

        return StringUtils.isEmpty(valueString) ? null : Integer.parseInt(valueString);
    }

    public String getString(String name) {
        String valueString = properties.getProperty(name);

        return valueString;
    }
}
