package com.lakeel.altla.android.property;

import android.os.Parcel;
import android.os.Parcelable;

public final class ShortProperty extends AbstractShortProperty implements Parcelable {

    private short value;

    public ShortProperty() {
    }

    public ShortProperty(short value) {
        this.value = value;
    }

    protected ShortProperty(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShortProperty> CREATOR = new Creator<ShortProperty>() {
        @Override
        public ShortProperty createFromParcel(Parcel in) {
            return new ShortProperty(in);
        }

        @Override
        public ShortProperty[] newArray(int size) {
            return new ShortProperty[size];
        }
    };

    @Override
    public short get() {
        return value;
    }

    @Override
    public void set(short value) {
        if (this.value != value) {
            this.value = value;
            raiseOnValueChanged();
        }
    }
}

