package easyapps.ms.com.config_annotation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import easyapps.ms.com.config_annotation.utils.Constants;
import easyapps.ms.com.config_annotation.utils.Utils;

@RunWith(JUnit4.class)
public class UtilsTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Test
    public void isFieldLongTest() {
        assertTrue(Utils.isFieldLong("long"));
        assertTrue(Utils.isFieldLong("java.lang.Long"));
        assertFalse(Utils.isFieldLong("Long"));
        assertFalse(Utils.isFieldLong("Boolean"));
        assertFalse(Utils.isFieldLong("int"));
    }

    @Test
    public void isFieldIntTest() {
        assertTrue(Utils.isFieldInt("int"));
        assertTrue(Utils.isFieldInt("java.lang.Integer"));
        assertFalse(Utils.isFieldInt("Long"));
        assertFalse(Utils.isFieldInt("Boolean"));
        assertFalse(Utils.isFieldInt("java.lang.Long"));
    }

    @Test
    public void isFieldBooleanTest() {
        assertTrue(Utils.isFieldBoolean("boolean"));
        assertTrue(Utils.isFieldBoolean("java.lang.Boolean"));
        assertFalse(Utils.isFieldBoolean("bool"));
        assertFalse(Utils.isFieldBoolean("Boolean"));
        assertFalse(Utils.isFieldBoolean("int"));
    }

    @Test
    public void getFieldFromClassNameTest() {
        assertEquals(Constants.FIELD_BOOLEAN, Utils.getFieldFromClassName(Boolean.class));
        assertEquals(Constants.FIELD_BOOLEAN, Utils.getFieldFromClassName(boolean.class));
        assertNotEquals(Constants.FIELD_BOOLEAN, Utils.getFieldFromClassName(Integer.class));
        assertEquals(Constants.FIELD_INT, Utils.getFieldFromClassName(int.class));
        assertEquals(Constants.FIELD_STRING, Utils.getFieldFromClassName("abc".getClass()));
        assertNotEquals(Constants.FIELD_INT, Utils.getFieldFromClassName(Boolean.class));
        assertNotEquals(Constants.FIELD_STRING, Utils.getFieldFromClassName(boolean.class));
        assertEquals(Constants.FIELD_STRING, Utils.getFieldFromClassName(String.class));
        expectedException.expect(IllegalStateException.class);
        Utils.getFieldFromClassName(StringBuilder.class);
    }


}
