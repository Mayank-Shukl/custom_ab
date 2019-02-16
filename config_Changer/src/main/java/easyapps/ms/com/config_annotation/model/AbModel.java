package easyapps.ms.com.config_annotation.model;

import android.databinding.Bindable;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import java.io.Serializable;

import easyapps.ms.com.config_annotation.utils.Utils;


public class AbModel extends BaseBindingDataItem implements Serializable {

    private String lobName;
    private int fieldType;
    private String fieldName;
    private String date;
    private String className;
    private Object data;


    public AbModel() {
        // not required
    }


    public String getLobName() {
        return lobName;
    }

    public void setLobName(String lobName) {
        this.lobName = lobName;
    }

    public int getFieldType() {
        return fieldType;
    }

    public void setFieldType(int fieldType) {
        this.fieldType = fieldType;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Bindable
    public String getFieldName() {
        return fieldName;
    }

    @Bindable
    public String getValue() {
        return String.valueOf(getData());
    }

    @Bindable
    public void setValue(String value) {
        data = value;
        Log.i("ABCONFIG", value);
    }

    @Bindable
    public String getDate() {
        return "Dated " + date;
    }

    @Bindable
    public boolean getSwitchValue() {
        return (boolean) data;
    }


    public void onCheckChanged(View switchCompat, boolean checked) {
        data = checked;
    }

    @Bindable
    public int getInputType() {
        if (Utils.isFieldLong(className) || Utils.isFieldInt(className)) {
            return InputType.TYPE_CLASS_NUMBER;
        } else {
            return InputType.TYPE_CLASS_TEXT;
        }
    }
}
