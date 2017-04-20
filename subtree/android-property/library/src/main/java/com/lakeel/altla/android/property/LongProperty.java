package com.lakeel.altla.android.property;

import android.os.Parcel;
import android.os.Parcelable;

public final class LongProperty extends AbstractLongProperty implements Parcelable {

    private long value;

    public LongProperty() {
    }

    public LongProperty(long value) {
        this.value = value;
    }

    protected LongProperty(Parcel in) {
        value = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LongProperty> CREATOR = new Creator<LongProperty>() {
        @Override
        public LongProperty createFromParcel(Parcel in) {
            return new LongProperty(in);
        }

        @Override
        public LongProperty[] newArray(int size) {
            return new LongProperty[size];
        }
    };

    @Override
    public long get() {
        return value;
    }

    @Override
    public void set(long value) {
        if (this.value != value) {
            this.value = value;
            raiseOnValueChanged();
        }
    }
}
