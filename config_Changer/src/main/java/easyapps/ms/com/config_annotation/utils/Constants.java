package easyapps.ms.com.config_annotation.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Constants {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            FIELD_BOOLEAN, FIELD_INT, FIELD_STRING
    })
    public @interface FIELDS {
    }

    public static final int FIELD_BOOLEAN = 0;
    public static final int FIELD_INT = 1;
    public static final int FIELD_DOUBLE = 2;
    public static final int FIELD_STRING = 3;

    public static final int SAVE = 1;
    public static final int DISCARD = -1;
}
