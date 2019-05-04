package github.api.envConfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {

    private final String environment;
    private final Properties properties = new Properties();

    public PropertiesManager(String environment) throws IOException {
        this.environment = environment;
        loadProperties();
    }


    private void loadProperties() throws IOException {
        String fileName = environment + ".properties";
        properties.load(new FileInputStream("src/test/resources/"+fileName));
    }

    public String getStringValue(String name) {
        return (String) properties.get(name);
    }


    public <T> T getValue(PropNames name, Class<T> clazz) {
        String value = (String) properties.get(name.getPropName());

        if (clazz.equals(Integer.class)) {
            return clazz.cast(Integer.valueOf(value));
        } else {
            return clazz.cast(value);
        }
    }




    public enum PropNames {
        Host("github.host"),
        Username("github.username"),
        Repository("github.repo"),
        AccessToken("github.accessToken");

        private final String propName;
        PropNames(String propName) {
            this.propName = propName;
        }
        public String getPropName() {
            return propName;
        }
    }

}
