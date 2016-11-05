package kz.eugales.re4.model;

import com.google.gson.annotations.Expose;

import java.util.Collection;

/**
 * Created by Adil on 22.09.2016.
 */
public class User {
    @Expose
    private long id;
    @Expose
    private String name;
    @Expose
    private String phoneNumber;
    @Expose
    private String housingEstate;
    @Expose
    private String home;
    @Expose
    private String porch;
    @Expose
    private String flat;
    /*@Expose
    private String smsCode;
    @Expose
    private long smsCodeDate;
    @Expose
    private String hash;*/
    @Expose
    private Collection<Role> roles;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHousingEstate() {
        return housingEstate;
    }

    public void setHousingEstate(String housingEstate) {
        this.housingEstate = housingEstate;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getPorch() {
        return porch;
    }

    public void setPorch(String porch) {
        this.porch = porch;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    /*public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public long getSmsCodeDate() {
        return smsCodeDate;
    }

    public void setSmsCodeDate(long smsCodeDate) {
        this.smsCodeDate = smsCodeDate;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
*/
    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
