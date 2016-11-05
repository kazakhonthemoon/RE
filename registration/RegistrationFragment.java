package kz.eugales.re4.registration;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kz.eugales.re4.CompanyApplication;
import kz.eugales.re4.R;
import kz.eugales.re4.helper.Constants.*;
import kz.eugales.re4.helper.Utils;
import kz.eugales.re4.model.Result;
import kz.eugales.re4.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistrationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private EditText etPhoneNumber;
    private TextView tvError;
    @Inject
    UserService userService;

    public RegistrationFragment() {
    }

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        ((CompanyApplication) getActivity().getApplication()).getUserComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        etPhoneNumber = (EditText) view.findViewById(R.id.etPhoneNumber);
        tvError = (TextView) view.findViewById(R.id.tvError);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_next:
                final Editable phoneNumber = etPhoneNumber.getText();
                if (phoneNumber.length() > 10) {

                    createUser(phoneNumber.toString());

                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createUser(String phoneNumber) {

        //сохраняет телефон в shared prefs
        SharedPreferences preferences = getActivity().getSharedPreferences(CONFIG.PREF_NAME, Context.MODE_PRIVATE);
        Map<String, String> prefValues = new HashMap<>();
        prefValues.put(CONFIG.PREF_PHONE_NUMBER, phoneNumber);
        List<String> result = Utils.setPrefs(preferences, prefValues);

        userService.createUser(phoneNumber).enqueue(new Callback<Result<Long>>() {
            @Override
            public void onResponse(Call<Result<Long>> call, Response<Result<Long>> response) {
                Result<Long> result = response.body();
                if (isResultValid(result)) {

                    long id = result.getValue().get(0);

                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container_reg, ConfirmRegistrationFragment.
                                    newInstance(id))//отправляет userId
                            .commit();

                } else {
                    tvError.setText("Возникла ошибка. " + ((result != null) ? result.getError() : "null"));
                    tvError.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Result<Long>> call, Throwable t) {

            }
        });

    }

    boolean isResultValid(Result<Long> result) {
        return result != null && result.getError().equals("0") && !result.getValue().isEmpty();
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
}
