package kz.eugales.re4.registration;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import kz.eugales.re4.R;
import kz.eugales.re4.helper.Constants.*;

public class Registration extends AppCompatActivity
        implements RegistrationFragment.OnFragmentInteractionListener,
        ConfirmRegistrationFragment.OnFragmentInteractionListener,
        UserDetailUpdateFragment.OnFragmentInteractionListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        SharedPreferences.Editor ed = getSharedPreferences(CONFIG.PREF_NAME,MODE_PRIVATE).edit();
        ed.remove(CONFIG.PREF_PHONE_NUMBER);
        ed.remove(CONFIG.PREF_HASH);
        ed.apply();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container_reg, RegistrationFragment.newInstance())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.fragment_registration_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
