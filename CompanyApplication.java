package kz.eugales.re4;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import kz.eugales.re4.dependencies.components.ApiComponent;
import kz.eugales.re4.dependencies.components.DaggerApiComponent;
import kz.eugales.re4.dependencies.components.DaggerNetworkComponent;
import kz.eugales.re4.dependencies.components.DaggerUserComponent;
import kz.eugales.re4.dependencies.components.NetworkComponent;
import kz.eugales.re4.dependencies.components.UserComponent;
import kz.eugales.re4.dependencies.modules.NetworkModule;
import kz.eugales.re4.helper.Constants.*;
import kz.eugales.re4.helper.Utils;

/**
 * Created by Adil on 13.08.2016.
 */
public class CompanyApplication extends Application {

    private ApiComponent mApiComponent;
    private UserComponent mUserComponent;

    public NetworkComponent mNetworkComponent;

    private RealmConfiguration realmConfiguration;

    @Override
    public void onCreate() {

        resolveDependencies();
        resolveDatabase(getApplicationContext());
        super.onCreate();
    }


    private void resolveDatabase(Context context){
        Realm.init(context);
        realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }



    private void resolveDependencies() {

        mNetworkComponent = getNetworkComponent();




        mApiComponent = DaggerApiComponent.builder()
                .networkComponent(mNetworkComponent)
                .build();

        mUserComponent = DaggerUserComponent.builder()
                .networkComponent(mNetworkComponent)
                .build();
    }

    public NetworkComponent getNetworkComponent() {
        Map<String,String> map = getCredentialPrefs();
        return DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule(
                        HTTP.BASE_URL))
                .build();
    }

    public Map<String, String> getCredentialPrefs() {
        List<String> prefNames = new ArrayList<>();
        prefNames.add(CONFIG.PREF_PHONE_NUMBER);
        prefNames.add(CONFIG.PREF_HASH);
        Map<String, String> prefValues = Utils.getPrefs(getSharedPreferences(CONFIG.PREF_NAME, Context.MODE_PRIVATE), prefNames);
        return prefValues;
    }

    public ApiComponent getApiComponent() {
        return mApiComponent;
    }

    public UserComponent getUserComponent() {
        return mUserComponent;
    }
}
