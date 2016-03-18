package net.mobindustry.rxedu.briteDb;

import com.squareup.sqlbrite.BriteDatabase;

import net.mobindustry.rxedu.model.Address;
import net.mobindustry.rxedu.model.Company;
import net.mobindustry.rxedu.model.Geo;
import net.mobindustry.rxedu.model.User;

public class DbHelper {

    /**
     * User
     **/

    public static void addUser(User user) {
        BriteDatabase db = Db.getInstance();
        long id = db.insert(User.TABLE, new User.Builder().build(user));
        user.set_id(id);
        addAddress(user);
        addCompany(user);
    }

    public static void addAddress(User user) {
        BriteDatabase db = Db.getInstance();
        long id = db.insert(Address.TABLE, new Address.Builder().build(user));
        user.getAddress().set_id(id);
        addGeo(user.getAddress());
    }

    public static void addGeo(Address address) {
        BriteDatabase db = Db.getInstance();
        long id = db.insert(Geo.TABLE, new Geo.Builder().build(address));
        address.getGeo().set_id(id);
    }

    public static void addCompany(User user) {
        BriteDatabase db = Db.getInstance();
        long id = db.insert(Company.TABLE, new Company.Builder().build(user));
        user.getCompany().set_id(id);
    }
}
