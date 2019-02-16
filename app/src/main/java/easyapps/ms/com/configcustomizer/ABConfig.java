package easyapps.ms.com.configcustomizer;

import android.os.Parcel;
import android.os.Parcelable;

import easyapps.ms.com.config_annotation.AbElement;
import easyapps.ms.com.config_annotation.ConfigClass;

@ConfigClass
public class ABConfig implements Parcelable {

    @AbElement(lob="ONE",date="20/07/2019")
    private String textFlag;

    @AbElement(lob="TWO",date="20/07/2019")
    private String textFlag2;

    @AbElement(lob="THREE",date="20/07/2019")
    private String textFlag3;

    @AbElement(lob="FOUR",date="20/07/2019")
    private String textFlag4;

    @AbElement(lob="ONE",date="20/07/2019")
    private boolean booleanFlag;

    @AbElement(lob="TWO",date="20/07/2019")
    private boolean booleanFlag2;

    @AbElement(lob="THREE",date="20/07/2019")
    private boolean booleanFlag3;

    @AbElement(lob="FOUR",date="20/07/2019")
    private boolean booleanFlag4;

    @AbElement(lob="ONE",date="20/07/2019")
    private int booleanFlag5;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.textFlag);
        dest.writeString(this.textFlag2);
        dest.writeString(this.textFlag3);
        dest.writeString(this.textFlag4);
        dest.writeByte(this.booleanFlag ? (byte) 1 : (byte) 0);
        dest.writeByte(this.booleanFlag2 ? (byte) 1 : (byte) 0);
        dest.writeByte(this.booleanFlag3 ? (byte) 1 : (byte) 0);
        dest.writeByte(this.booleanFlag4 ? (byte) 1 : (byte) 0);
        dest.writeInt(this.booleanFlag5);
    }

    public ABConfig() {
    }

    protected ABConfig(Parcel in) {
        this.textFlag = in.readString();
        this.textFlag2 = in.readString();
        this.textFlag3 = in.readString();
        this.textFlag4 = in.readString();
        this.booleanFlag = in.readByte() != 0;
        this.booleanFlag2 = in.readByte() != 0;
        this.booleanFlag3 = in.readByte() != 0;
        this.booleanFlag4 = in.readByte() != 0;
        this.booleanFlag5 = in.readInt();
    }

    public static final Parcelable.Creator<ABConfig> CREATOR = new Parcelable.Creator<ABConfig>() {
        @Override
        public ABConfig createFromParcel(Parcel source) {
            return new ABConfig(source);
        }

        @Override
        public ABConfig[] newArray(int size) {
            return new ABConfig[size];
        }
    };

    public String getTextFlag() {
        return textFlag;
    }

    public void setTextFlag(String textFlag) {
        this.textFlag = textFlag;
    }

    public String getTextFlag2() {
        return textFlag2;
    }

    public void setTextFlag2(String textFlag2) {
        this.textFlag2 = textFlag2;
    }

    public String getTextFlag3() {
        return textFlag3;
    }

    public void setTextFlag3(String textFlag3) {
        this.textFlag3 = textFlag3;
    }

    public String getTextFlag4() {
        return textFlag4;
    }

    public void setTextFlag4(String textFlag4) {
        this.textFlag4 = textFlag4;
    }

    public boolean isBooleanFlag() {
        return booleanFlag;
    }

    public void setBooleanFlag(boolean booleanFlag) {
        this.booleanFlag = booleanFlag;
    }

    public boolean isBooleanFlag2() {
        return booleanFlag2;
    }

    public void setBooleanFlag2(boolean booleanFlag2) {
        this.booleanFlag2 = booleanFlag2;
    }

    public boolean isBooleanFlag3() {
        return booleanFlag3;
    }

    public void setBooleanFlag3(boolean booleanFlag3) {
        this.booleanFlag3 = booleanFlag3;
    }

    public boolean isBooleanFlag4() {
        return booleanFlag4;
    }

    public void setBooleanFlag4(boolean booleanFlag4) {
        this.booleanFlag4 = booleanFlag4;
    }

    public int getBooleanFlag5() {
        return booleanFlag5;
    }

    public void setBooleanFlag5(int booleanFlag5) {
        this.booleanFlag5 = booleanFlag5;
    }
}
