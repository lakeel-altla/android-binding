package com.lakeel.altla.android.property;

import android.os.Parcel;
import android.os.Parcelable;

public final class ByteProperty extends AbstractByteProperty implements Parcelable {

    private byte value;

    public ByteProperty() {
    }

    public ByteProperty(byte value) {
        this.value = value;
    }

    protected ByteProperty(Parcel in) {
        value = in.readByte();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ByteProperty> CREATOR = new Creator<ByteProperty>() {
        @Override
        public ByteProperty createFromParcel(Parcel in) {
            return new ByteProperty(in);
        }

        @Override
        public ByteProperty[] newArray(int size) {
            return new ByteProperty[size];
        }
    };

    @Override
    public byte get() {
        return value;
    }

    @Override
    public void set(byte value) {
        if (this.value != value) {
            this.value = value;
            raiseOnValueChanged();
        }
    }
}
