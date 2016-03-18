package net.mobindustry.rxedu.model;

import android.content.ContentValues;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address implements Serializable {

    public static final String TABLE = "address";

    public static final String COL_ID = "_id";
    public static final String COL_USER_ID = "users_id";
    public static final String COL_STREET = "street";
    public static final String COL_SUITE = "suite";
    public static final String COL_CITY = "city";
    public static final String COL_ZIP_CODE = "zip_code";

    private long _id;
    private long userId;
    private String street;
    private String suite;
    private String city;

    @SerializedName("zipcode")
    private String zipCode;
    private Geo geo;

    public Address() {
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    @Override
    public String toString() {
        return "Address{" +
                "_id=" + _id +
                ", userId=" + userId +
                ", street='" + street + '\'' +
                ", suite='" + suite + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", geo=" + geo +
                '}';
    }

    public static final class Builder {
        private final ContentValues values = new ContentValues();

        public Builder id(long _id) {
            values.put(COL_ID, _id);
            return this;
        }

        public Builder userId(long userId) {
            values.put(COL_USER_ID, userId);
            return this;
        }

        public Builder street(String street) {
            values.put(COL_STREET, street);
            return this;
        }

        public Builder suite(String suite) {
            values.put(COL_SUITE, suite);
            return this;
        }

        public Builder city(String city) {
            values.put(COL_CITY, city);
            return this;
        }

        public Builder zipCode(String zipCode) {
            values.put(COL_ZIP_CODE, zipCode);
            return this;
        }

        public ContentValues build() {
            return values;
        }

        public ContentValues build(User user) {
            Address address = user.getAddress();
            return new Address.Builder()
                    .userId(user.get_id())
                    .street(address.getStreet())
                    .suite(address.getSuite())
                    .city(address.getCity())
                    .zipCode(address.getZipCode())
                    .build();
        }
    }
}
