package net.mobindustry.rxedu.model;

import android.content.ContentValues;

import java.io.Serializable;

public class Geo implements Serializable {

    public static final String TABLE = "geo";

    public static final String COL_ID = "_id";
    public static final String COL_ADDRESS_ID = "address_id";
    public static final String COL_LAT = "lat";
    public static final String COL_LNG = "lng";

    private long _id;
    private long addressId;
    private double lat;
    private double lng;

    public Geo() {
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Geo{" +
                "_id=" + _id +
                ", addressId=" + addressId +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }

    public static final class Builder {
        private final ContentValues values = new ContentValues();

        public Builder id(long _id) {
            values.put(COL_ID, _id);
            return this;
        }

        public Builder addressId(long addressId) {
            values.put(COL_ADDRESS_ID, addressId);
            return this;
        }

        public Builder lat(double lat) {
            values.put(COL_LAT, lat);
            return this;
        }

        public Builder lng(double lng) {
            values.put(COL_LNG, lng);
            return this;
        }

        public ContentValues build() {
            return values;
        }

        public ContentValues build(Address address) {
            Geo geo = address.getGeo();
            return new Geo.Builder()
                    .addressId(address.get_id())
                    .lat(geo.getLat())
                    .lng(geo.getLng())
                    .build();
        }

    }
}
