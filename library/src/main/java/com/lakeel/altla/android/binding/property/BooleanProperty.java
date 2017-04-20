package com.lakeel.altla.android.binding.property;

import android.os.Parcel;
import android.os.Parcelable;

public final class BooleanProperty extends AbstractBooleanProperty implements Parcelable {

    private boolean value;

    public BooleanProperty() {
    }

    public BooleanProperty(boolean value) {
        this.value = value;
    }

    protected BooleanProperty(Parcel in) {
        value = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (value ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BooleanProperty> CREATOR = new Creator<BooleanProperty>() {
        @Override
        public BooleanProperty createFromParcel(Parcel in) {
            return new BooleanProperty(in);
        }

        @Override
        public BooleanProperty[] newArray(int size) {
            return new BooleanProperty[size];
        }
    };

    @Override
    public boolean get() {
        return value;
    }

    @Override
    public void set(boolean value) {
        if (this.value != value) {
            this.value = value;
            raiseOnValueChanged();
        }
    }
}
