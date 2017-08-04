package com.yhrjkf.woyundong.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class LatLng implements Cloneable,Parcelable{

    private static DecimalFormat df;
    private final double longitude;
    private final double latitude;

    public LatLng(double var1, double var3) {
        if(-180.0D <= var3 && var3 < 180.0D) {
            this.longitude = parseDouble(var3);
        } else {
            this.longitude = parseDouble(((var3 - 180.0D) % 360.0D + 360.0D) % 360.0D - 180.0D);
        }

        this.latitude = parseDouble(Math.max(-90.0D, Math.min(90.0D, var1)));
    }

    private static double parseDouble(double var0) {
        return Double.parseDouble(df.format(var0));
    }

    public final LatLng clone() {
        return new LatLng(this.latitude, this.longitude);
    }

    public final double getLatitude() {
        return this.latitude;
    }

    public final double getLongitude() {
        return this.longitude;
    }

    public final int hashCode() {
        long var2 = Double.doubleToLongBits(this.latitude);
        int var1 = 31 + (int)(var2 ^ var2 >>> 32);
        var2 = Double.doubleToLongBits(this.longitude);
        return 31 * var1 + (int)(var2 ^ var2 >>> 32);
    }

    public final boolean equals(Object var1) {
        if(this == var1) {
            return true;
        } else if(!(var1 instanceof LatLng)) {
            return false;
        } else {
            LatLng var2 = (LatLng)var1;
            return Double.doubleToLongBits(this.latitude) == Double.doubleToLongBits(var2.latitude) && Double.doubleToLongBits(this.longitude) == Double.doubleToLongBits(var2.longitude);
        }
    }

    public final String toString() {
        return "lat/lng: (" + this.latitude + "," + this.longitude + ")";
    }

    static {
        df = new DecimalFormat("0.000000", new DecimalFormatSymbols(Locale.US));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.latitude);
    }

    protected LatLng(Parcel in) {
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
    }

    public static final Creator<LatLng> CREATOR = new Creator<LatLng>() {
        @Override
        public LatLng createFromParcel(Parcel source) {
            return new LatLng(source);
        }

        @Override
        public LatLng[] newArray(int size) {
            return new LatLng[size];
        }
    };
}