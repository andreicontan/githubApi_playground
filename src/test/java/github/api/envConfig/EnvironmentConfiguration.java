package github.api.envConfig;

import java.io.IOException;
import org.junit.BeforeClass;

public class EnvironmentConfiguration {

    static PropertiesManager propertiesManager;

    @BeforeClass
    public static void align_all_planets() throws IOException {
        String envValue = System.getenv("env");
        if (envValue == null) {
            envValue = "local";
        }

        propertiesManager = new PropertiesManager(envValue);
    }

}
