package easyapps.ms.com.config_annotation;

@ConfigClass
public class TestConfig {

    @AbElement(lob = "ONE", date = "20/07/2019")
    private String textFlag = "test";

    @AbElement(lob = "TWO", date = "20/07/2019")
    private String textFlag2;

    @AbElement(lob = "ONE", date = "20/07/2019")
    private boolean booleanFlag;

    @AbElement(lob = "TWO", date = "20/07/2019")
    private boolean booleanFlag2;

    @AbElement(lob = "ONE", date = "20/07/2019")
    private int booleanFlag5;

}
