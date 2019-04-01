package framework.utils;

import com.google.common.collect.ImmutableMap;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * Reads configuration parameters stored in Config.yml file
 */
public class ConfigReader {
    private static final String CONFIG_PATH = "config/Config.yml";
    private static final Yaml YAML = new Yaml();
    private static ThreadLocal<Map<String, String>> _configData = new ThreadLocal<>();

    /**
     * Package-local methods that are used to read the Config.yml classes into Maps that can be accessed by the
     * {@link RuntimeParameters} class.
     */
    static Map<String, String> getGlobalTestConfig() {
        if(_configData.get() == null) {
            _configData.set(readGlobalConfig());
        }
        return _configData.get();
    }

    private static Map<String, String> readGlobalConfig() {
        return readConfigFile(CONFIG_PATH, true);
    }

    private static Map<String, String> readConfigFile(String configFilePath, boolean required) {
        try {
            InputStream configStream = ConfigReader.class.getClassLoader().getResourceAsStream(configFilePath);
            if(configStream == null) {
                if (required) {
                    throw new IllegalArgumentException("Unable to find configs file: " + configFilePath);
                } else {
                    return ImmutableMap.of();
                }
            }
            //noinspection unchecked
            return ImmutableMap.copyOf((Map < String, String >) YAML.load(configStream));
        } catch (Exception e) {
            String errorMessage = String.format("Problem in reading configs file at '%s'. Exception: '%s'.", configFilePath, e.getMessage());
            throw new RuntimeException(errorMessage);
        }
    }
}
