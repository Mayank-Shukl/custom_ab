package easyapps.ms.com.config_annotation.model;

import android.databinding.BaseObservable;

public class BaseBindingDataItem extends BaseObservable {

    protected int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
