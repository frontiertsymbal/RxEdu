package net.mobindustry.rxedu.model;

import android.content.ContentValues;

import java.io.Serializable;

public class Company implements Serializable {

    public static final String TABLE = "company";

    public static final String COL_ID = "_id";
    public static final String COL_USER_ID = "users_id";
    public static final String COL_NAME = "name";
    public static final String COL_CATCH_PHRASE = "catch_phrase";
    public static final String COL_BS = "bs";

    private long _id;
    private long userId;

    private String name;
    private String catchPhrase;
    private String bs;

    public Company() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    public String getBs() {
        return bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }

    @Override
    public String toString() {
        return "Company{" +
                "_id=" + _id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", catchPhrase='" + catchPhrase + '\'' +
                ", bs='" + bs + '\'' +
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

        public Builder name(String name) {
            values.put(COL_NAME, name);
            return this;
        }

        public Builder catchPhrase(String catchPhrase) {
            values.put(COL_CATCH_PHRASE, catchPhrase);
            return this;
        }

        public Builder bs(String bs) {
            values.put(COL_BS, bs);
            return this;
        }

        public ContentValues build() {
            return values;
        }

        public ContentValues build(User user) {
            Company company = user.getCompany();
            return new Company.Builder()
                    .userId(user.get_id())
                    .name(company.getName())
                    .catchPhrase(company.getCatchPhrase())
                    .bs(company.getBs())
                    .build();
        }
    }
}
