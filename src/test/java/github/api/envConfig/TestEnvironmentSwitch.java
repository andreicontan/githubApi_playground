package github.api.envConfig;

import org.junit.Test;

public class TestEnvironmentSwitch extends EnvironmentConfiguration{

    @Test
    public void should_read_from_env_specific_file(){
        //set system variable as export env=staging
        System.out.println(propertiesManager.getStringValue("github.host"));
    }

}
