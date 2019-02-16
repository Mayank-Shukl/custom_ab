package easyapps.ms.com.config_annotation.repo;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import easyapps.ms.com.config_annotation.TestConfig;
import easyapps.ms.com.config_annotation.model.AbModel;

@RunWith(JUnit4.class)
public class LocalMapRespositoryTest {


    @Test
    public void testGetDataFromReflection() {
        TestConfig config = new TestConfig();
        LocalMapRepository repository = new LocalMapRepository();
        Map<String, List<AbModel>> map = repository.getDataFromReflection(config);
        Assert.assertNotNull(map);
        Assert.assertEquals(2, map.size());
        Assert.assertEquals(3, map.get("ONE").size());
        Assert.assertEquals(2, map.get("TWO").size());
    }

    @Test
    public void testgetAbModelFromField() throws NoSuchFieldException {
        TestConfig config = new TestConfig();
        LocalMapRepository repository = new LocalMapRepository();
        Field field = config.getClass().getDeclaredField("textFlag");
        AbModel abModel = repository.getAbModelForField(field, config);
        Assert.assertNotNull(abModel);
        Assert.assertEquals("textFlag", abModel.getFieldName());
        Assert.assertEquals("test", abModel.getValue());
        Assert.assertEquals(String.class.getName(), abModel.getClassName());



        field = config.getClass().getDeclaredField("booleanFlag2");
        abModel = repository.getAbModelForField(field, config);
        Assert.assertNotNull(abModel);
        Assert.assertEquals("booleanFlag2", abModel.getFieldName());
        Assert.assertEquals(false, abModel.getData());
        Assert.assertEquals(boolean.class.getName(), abModel.getClassName());


        field = config.getClass().getDeclaredField("booleanFlag");
        abModel = repository.getAbModelForField(field, config);
        Assert.assertNotNull(abModel);
        Assert.assertEquals("booleanFlag", abModel.getFieldName());
        Assert.assertEquals(false, abModel.getData());
        Assert.assertEquals(boolean.class.getName(), abModel.getClassName());
        Assert.assertEquals("Dated 20/07/2019", abModel.getDate());
    }
}
