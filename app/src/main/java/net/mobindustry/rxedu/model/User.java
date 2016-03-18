package net.mobindustry.rxedu.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import net.mobindustry.rxedu.briteDb.Db;

import java.io.Serializable;

import rx.functions.Func1;

public class User implements Serializable {

    public static final String TABLE = "users";

    public static final String COL_ID = "_id";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_NAME = "name";
    public static final String COL_USER_NAME = "user_name";
    public static final String COL_EMAIL = "email";
    public static final String COL_PHONE = "phone";
    public static final String COL_WEB_SITE = "web_site";
    public static final Func1<Cursor, User> MAPPER = new Func1<Cursor, User>() {
        @Override
        public User call(Cursor cursor) {
            User user = new User();
            user.set_id(Db.getLong(cursor, COL_ID));
            user.setUserId(Db.getInt(cursor, COL_USER_ID));
            user.setName(Db.getString(cursor, COL_NAME));
            user.setUserName(Db.getString(cursor, COL_USER_NAME));
            user.setEmail(Db.getString(cursor, COL_EMAIL));
            user.setPhone(Db.getString(cursor, COL_PHONE));
            user.setWebSite(Db.getString(cursor, COL_WEB_SITE));
            return user;
        }
    };
    private long _id;
    @SerializedName("id")
    private int userId;
    private String name;
    @SerializedName("username")
    private String userName;
    private String email;
    private String phone;
    @SerializedName("website")
    private String webSite;
    private Address address;
    private Company company;

    public User() {
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", webSite='" + webSite + '\'' +
                ", userName='" + userName + '\'' +
                ", _id=" + _id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                ", company=" + company +
                '}';
    }

    public static final class Builder {
        private final ContentValues values = new ContentValues();

        public Builder id(long _id) {
            values.put(COL_ID, _id);
            return this;
        }

        public Builder userId(int userId) {
            values.put(COL_USER_ID, userId);
            return this;
        }

        public Builder name(String name) {
            values.put(COL_NAME, name);
            return this;
        }

        public Builder userName(String userName) {
            values.put(COL_USER_NAME, userName);
            return this;
        }

        public Builder email(String email) {
            values.put(COL_EMAIL, email);
            return this;
        }

        public Builder phone(String phone) {
            values.put(COL_PHONE, phone);
            return this;
        }

        public Builder webSite(String webSite) {
            values.put(COL_WEB_SITE, webSite);
            return this;
        }

        public ContentValues build() {
            return values;
        }

        public ContentValues build(User user) {
            return new User.Builder()
                    .userId(user.getUserId())
                    .name(user.getName())
                    .userName(user.getUserName())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .webSite(user.getWebSite())
                    .build();
        }
    }
}
