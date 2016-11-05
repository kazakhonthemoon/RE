package kz.eugales.re4.model;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;

/**
 * Created by Adil on 05.08.2016.
 */
public class Phone extends RealmObject {

    @Expose
    private String phoneId;

    @Expose
    private String phoneName;

    @Expose
    private String phoneNumber;

    @Expose
    private int companyId;

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
