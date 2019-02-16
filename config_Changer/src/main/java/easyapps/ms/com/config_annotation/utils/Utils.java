package easyapps.ms.com.config_annotation.utils;


import static easyapps.ms.com.config_annotation.utils.Constants.FIELD_BOOLEAN;
import static easyapps.ms.com.config_annotation.utils.Constants.FIELD_INT;
import static easyapps.ms.com.config_annotation.utils.Constants.FIELD_STRING;

public class Utils {
    public static boolean isFieldLong(String className) {
        if (className.equals("long") || className.equals("java.lang.Long")) {
            return true;
        }
        return false;
    }

    public static boolean isFieldInt(String className) {
        if (className.equals("int") || className.equals("java.lang.Integer")) {
            return true;
        }
        return false;
    }

    public static boolean isFieldBoolean(String className) {
        if (className.equals("boolean") || className.equals("java.lang.Boolean")) {
            return true;
        }
        return false;
    }

    public  static  @Constants.FIELDS
    int getFieldFromClassName(Class classOfField) {
        if (Boolean.class.isAssignableFrom(classOfField) || boolean.class.isAssignableFrom(classOfField)) {
            return FIELD_BOOLEAN;
        } else if (Integer.class.isAssignableFrom(classOfField) || int.class.isAssignableFrom(classOfField)) {
            return FIELD_INT;
        } else if (String.class.isAssignableFrom(classOfField)) {
            return FIELD_STRING;
        }
        throw new IllegalStateException("type not supported " + classOfField.getName());
    }

    /* Function to check if support fragment UI is active*/
    public static boolean isFragmentUIActive(android.support.v4.app.Fragment frag) {
        return frag != null && frag.getActivity() != null && frag.isAdded() && !frag.isDetached() && !frag.isRemoving();
    }

}
