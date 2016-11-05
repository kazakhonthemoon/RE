package kz.eugales.re4.registration;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kz.eugales.re4.MainActivity;
import kz.eugales.re4.R;
import kz.eugales.re4.helper.Constants.*;
import kz.eugales.re4.helper.Utils;


public class UserDetailUpdateFragment extends Fragment {


    String[] data = {"Выберите значение", "one", "two", "three", "four", "five"};

    List<String> realEstate;
    List<Integer> home;
    List<Integer> porch;
    List<Integer> flat;

    private OnFragmentInteractionListener mListener;

    public UserDetailUpdateFragment() {
        // Required empty public constructor
    }

    public static UserDetailUpdateFragment newInstance() {
        UserDetailUpdateFragment fragment = new UserDetailUpdateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

        //получает телефон и хеш из shared prefs
        List<String> prefNames = new ArrayList<>();
        prefNames.add(CONFIG.PREF_PHONE_NUMBER);
        prefNames.add(CONFIG.PREF_HASH);
        Map<String, String> prefValues = Utils.getPrefs(getActivity().getSharedPreferences(CONFIG.PREF_NAME, Context.MODE_PRIVATE), prefNames);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_detail_update, container, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SpinnerHolder viewHolder = new SpinnerHolder(view);
        viewHolder.setAdapters(adapter);
        viewHolder.setOnItemSelectedListeners();

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_next:
                updateUserDetails();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUserDetails(){
        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class SpinnerHolder implements Spinner.OnItemSelectedListener {

        private View view;

        Spinner spinnerHousingEstate;
        Spinner spinnerHome;
        Spinner spinnerPorch;
        Spinner spinnerFlat;

        public SpinnerHolder(View v) {
            view = v;
            spinnerHousingEstate = (Spinner) view.findViewById(R.id.spinnerHousingEstate);
            spinnerHome = (Spinner) view.findViewById(R.id.spinnerHome);
            spinnerPorch = (Spinner) view.findViewById(R.id.spinnerPorch);
            spinnerFlat = (Spinner) view.findViewById(R.id.spinnerFlat);

        }

        public void setAdapters(ArrayAdapter<String> adapter) {
            spinnerHousingEstate.setAdapter(adapter);
            spinnerHome.setAdapter(adapter);
            spinnerPorch.setAdapter(adapter);
            spinnerFlat.setAdapter(adapter);
        }

        public void setOnItemSelectedListeners() {
            spinnerHousingEstate.setOnItemSelectedListener(this);
            spinnerHome.setOnItemSelectedListener(this);
            spinnerPorch.setOnItemSelectedListener(this);
            spinnerFlat.setOnItemSelectedListener(this);
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(view.getContext(), "Position" + i, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}
