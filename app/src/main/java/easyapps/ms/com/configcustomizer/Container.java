package easyapps.ms.com.configcustomizer;

import easyapps.ms.com.config_annotation.ConfigContainer;
import easyapps.ms.com.config_annotation.ConfigProvider;

@ConfigContainer
public class Container {
    private static final Container ourInstance = new Container();

    public static ABConfig abConfig = new ABConfig();
    public static Container getInstance() {
        return ourInstance;
    }

    private Container() {
    }

    @ConfigProvider
    public  ABConfig getAbConfig() {
        setDefaults();
        return abConfig;
    }

    private static void setDefaults() {
        abConfig.setBooleanFlag(true);
        abConfig.setBooleanFlag2(false);
        abConfig.setBooleanFlag3(true);
        abConfig.setTextFlag("Flag1");
        abConfig.setTextFlag2("Flag2");
        abConfig.setTextFlag3("Flag3");
        abConfig.setTextFlag4("Flag4");
    }
}
