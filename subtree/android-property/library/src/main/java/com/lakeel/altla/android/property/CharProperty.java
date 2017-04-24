package com.lakeel.altla.android.property;

import android.os.Parcel;
import android.os.Parcelable;

public final class CharProperty extends AbstractCharProperty implements Parcelable {

    private char value;

    public CharProperty() {
    }

    public CharProperty(char value) {
        this.value = value;
    }

    protected CharProperty(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CharProperty> CREATOR = new Creator<CharProperty>() {
        @Override
        public CharProperty createFromParcel(Parcel in) {
            return new CharProperty(in);
        }

        @Override
        public CharProperty[] newArray(int size) {
            return new CharProperty[size];
        }
    };

    @Override
    public char get() {
        return value;
    }

    @Override
    public void set(char value) {
        if (this.value != value) {
            this.value = value;
            raiseOnValueChanged();
        }
    }
}
