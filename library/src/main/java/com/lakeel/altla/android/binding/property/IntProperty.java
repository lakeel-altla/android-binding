package com.lakeel.altla.android.binding.property;

import android.os.Parcel;
import android.os.Parcelable;

public final class IntProperty extends AbstractIntProperty implements Parcelable {

    private int value;

    public IntProperty() {
    }

    public IntProperty(int value) {
        this.value = value;
    }

    protected IntProperty(Parcel in) {
        value = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IntProperty> CREATOR = new Creator<IntProperty>() {
        @Override
        public IntProperty createFromParcel(Parcel in) {
            return new IntProperty(in);
        }

        @Override
        public IntProperty[] newArray(int size) {
            return new IntProperty[size];
        }
    };

    @Override
    public int get() {
        return value;
    }

    @Override
    public void set(int value) {
        if (this.value != value) {
            this.value = value;
            raiseOnValueChanged();
        }
    }
}
