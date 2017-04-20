package com.lakeel.altla.android.property;

import android.os.Parcel;
import android.os.Parcelable;

public final class FloatProperty extends AbstractFloatProperty implements Parcelable {

    private float value;

    public FloatProperty() {
    }

    public FloatProperty(float value) {
        this.value = value;
    }

    protected FloatProperty(Parcel in) {
        value = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FloatProperty> CREATOR = new Creator<FloatProperty>() {
        @Override
        public FloatProperty createFromParcel(Parcel in) {
            return new FloatProperty(in);
        }

        @Override
        public FloatProperty[] newArray(int size) {
            return new FloatProperty[size];
        }
    };

    @Override
    public float get() {
        return value;
    }

    @Override
    public void set(float value) {
        if (this.value != value) {
            this.value = value;
            raiseOnValueChanged();
        }
    }
}
