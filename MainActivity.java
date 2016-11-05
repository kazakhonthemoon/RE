package kz.eugales.re4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kz.eugales.re4.dependencies.components.NetworkComponent;
import kz.eugales.re4.helper.Constants.*;
import kz.eugales.re4.helper.Utils;
import kz.eugales.re4.model.Company;
import kz.eugales.re4.model.Result;
import kz.eugales.re4.model.User;
import kz.eugales.re4.registration.Registration;
import kz.eugales.re4.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CompanyFragment.OnListFragmentInteractionListener {

    private String phoneNumber;
    private String hash;

    private User user;

    @Inject
    UserService userService;

    TextView tvPhoneNumber;
    TextView tvFlatDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ((CompanyApplication) getApplication()).getUserComponent().inject(this);

        //получает телефон и хеш из shared prefs
        List<String> prefNames = new ArrayList<>();
        prefNames.add(CONFIG.PREF_PHONE_NUMBER);
        prefNames.add(CONFIG.PREF_HASH);
        Map<String, String> prefValues = Utils.getPrefs(getSharedPreferences(CONFIG.PREF_NAME, MODE_PRIVATE), prefNames);

        //проверка валидности результата и их извлечение
        if (prefValues.containsKey(CONFIG.PREF_PHONE_NUMBER)
                && prefValues.containsKey(CONFIG.PREF_HASH)
                && !prefValues.containsValue(CONFIG.PREF_NOT_EXIST)) {

            phoneNumber = prefValues.get(CONFIG.PREF_PHONE_NUMBER);
            hash = prefValues.get(CONFIG.PREF_HASH);

            if (Utils.isNetworkAvailable(getApplicationContext())) {

                userService.getUser().enqueue(new Callback<Result<User>>() {
                    @Override
                    public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                        Result<User> result = response.body();

                        if (isResultValid(result)) {
                            List<User> m = result.getValue();
                            user = m.get(0);
                            tvPhoneNumber.setText(user.getPhoneNumber());
                            String userDetails = String.format("ЖК - %s \nДом - %s \nПодъезд - %s \nКвартира - %s",
                                    user.getHousingEstate(), user.getHome(), user.getPorch(), user.getFlat());
                            tvFlatDetails.setText(userDetails);

                        } else {
                            //Пользовательские данные устарели
                            Intent intent = new Intent(getApplicationContext(), Registration.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Result<User>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            } else {

                //db
            }

        } else {
            //Пользовательские данные неверны
            Intent intent = new Intent(getApplicationContext(), Registration.class);
            startActivity(intent);
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        View navigationHeaderMain = navigationView.getHeaderView(0);
        tvPhoneNumber = (TextView) navigationHeaderMain.findViewById(R.id.headerPhoneNumber);
        tvFlatDetails = (TextView) navigationHeaderMain.findViewById(R.id.headerFlatDetails);

    }

    Map<String, String> getPrefs(List<String> prefs) {
        Map<String, String> result = new HashMap<>();
        SharedPreferences preferences = getSharedPreferences(CONFIG.PREF_NAME, MODE_PRIVATE);
        for (String pref : prefs) {
            result.put(pref, preferences.getString(pref, CONFIG.PREF_NOT_EXIST));
        }
        return result;
    }


    boolean isResultValid(Result<User> result) {
        return result != null && result.getError().equals("0") && !result.getValue().isEmpty();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.jk) {

        } else if (id == R.id.helper) {

            Bundle bundle = new Bundle();
            bundle.putString(CONFIG.PREF_PHONE_NUMBER, phoneNumber);
            bundle.putString(CONFIG.PREF_HASH, hash);

            CompanyFragment companyFragment = CompanyFragment.newInstance();
            companyFragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, companyFragment)
                    .commit();

        } else if (id == R.id.messages) {

        } else if (id == R.id.votes) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Company company) {

        int id = company.getCompanyId();
        String phone = company.getPhones().get(0).getPhoneNumber();

        Toast.makeText(this, String.valueOf(phone), Toast.LENGTH_SHORT).show();
    }
}
