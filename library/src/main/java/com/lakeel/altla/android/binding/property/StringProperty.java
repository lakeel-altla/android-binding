package com.lakeel.altla.android.binding.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.Objects;

public final class StringProperty extends AbstractObjectProperty<String> implements Parcelable {

    private String value;

    public StringProperty() {
    }

    public StringProperty(String value) {
        this.value = value;
    }

    protected StringProperty(Parcel in) {
        value = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StringProperty> CREATOR = new Creator<StringProperty>() {
        @Override
        public StringProperty createFromParcel(Parcel in) {
            return new StringProperty(in);
        }

        @Override
        public StringProperty[] newArray(int size) {
            return new StringProperty[size];
        }
    };

    @Nullable
    @Override
    public String get() {
        return value;
    }

    @Override
    public void set(@Nullable String value) {
        if (!Objects.equals(this.value, value)) {
            this.value = value;
            raiseOnValueChanged();
        }
    }
}
