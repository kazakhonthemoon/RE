package kz.eugales.re4.database;

import io.realm.Realm;

/**
 * Created by Adil on 28.09.2016.
 */
public class RealmRepository {

    private Realm realm;

    public RealmRepository(){
        if(realm == null){
            realm = Realm.getDefaultInstance();
        }
    }


}
