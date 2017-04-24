package com.lakeel.altla.android.property;

import android.os.Parcel;
import android.os.Parcelable;

public final class DoubleProperty extends AbstractDoubleProperty implements Parcelable {

    private double value;

    public DoubleProperty() {
    }

    public DoubleProperty(double value) {
        this.value = value;
    }

    protected DoubleProperty(Parcel in) {
        value = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DoubleProperty> CREATOR = new Creator<DoubleProperty>() {
        @Override
        public DoubleProperty createFromParcel(Parcel in) {
            return new DoubleProperty(in);
        }

        @Override
        public DoubleProperty[] newArray(int size) {
            return new DoubleProperty[size];
        }
    };

    @Override
    public double get() {
        return value;
    }

    @Override
    public void set(double value) {
        if (this.value != value) {
            this.value = value;
            raiseOnValueChanged();
        }
    }
}
