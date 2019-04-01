package framework.utils;

import com.google.common.base.Strings;

import java.util.Optional;

/**
 * A static util for retrieving runtime arguments. Each
 * parameter is found in the Config.yml file
 */
public class RuntimeParameters {

    public static Optional<String> getParameter(String key) {

        //We check the config file before app and global Config files
        if(!Strings.isNullOrEmpty(ConfigReader.getGlobalTestConfig().get(key))) {
            return Optional.ofNullable(ConfigReader.getGlobalTestConfig().get(key));
        }
        return Optional.empty();
    }

    public static String requireParameter(String key) {
        return getParameter(key).orElseThrow(() -> new IllegalArgumentException("Unable to locate argument with key: " + key));
    }
}