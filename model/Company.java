package kz.eugales.re4.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Adil on 05.08.2016.
 */

public class Company extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    private int companyId;

    @Expose
    private String companyName;

    @Expose
    private String companySubject;

    @Expose
    private String photoUrl;

    @Expose
    private RealmList<Phone> phones = new RealmList<>();


    //private Bitmap photoBitmap;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanySubject() {
        return companySubject;
    }

    public void setCompanySubject(String companySubject) {
        this.companySubject = companySubject;
    }

    public RealmList<Phone> getPhones() {
        return phones;
    }

    public void setPhones(RealmList<Phone> phones) {
        this.phones = phones;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

   /* public Bitmap getPhotoBitmap() {
        return photoBitmap;
    }

    public void setPhotoBitmap(Bitmap photoBitmap) {
        this.photoBitmap = photoBitmap;
    }*/
}
