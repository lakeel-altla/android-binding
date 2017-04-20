package com.lakeel.altla.android.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.Objects;

public final class ParcelableProperty<T extends Parcelable> extends AbstractObjectProperty<T> implements Parcelable {

    private T value;

    public ParcelableProperty() {
    }

    public ParcelableProperty(T value) {
        this.value = value;
    }

    protected ParcelableProperty(Parcel in) {
        value = in.readParcelable(getClass().getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(value, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ParcelableProperty> CREATOR = new Creator<ParcelableProperty>() {
        @Override
        public ParcelableProperty createFromParcel(Parcel in) {
            return new ParcelableProperty(in);
        }

        @Override
        public ParcelableProperty[] newArray(int size) {
            return new ParcelableProperty[size];
        }
    };

    @Nullable
    @Override
    public T get() {
        return value;
    }

    @Override
    public void set(@Nullable T value) {
        if (!Objects.equals(this.value, value)) {
            this.value = value;
            raiseOnValueChanged();
        }
    }
}
