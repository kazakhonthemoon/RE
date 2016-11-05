package kz.eugales.re4.registration;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kz.eugales.re4.CompanyApplication;
import kz.eugales.re4.MainActivity;
import kz.eugales.re4.R;
import kz.eugales.re4.helper.Constants.*;
import kz.eugales.re4.helper.Utils;
import kz.eugales.re4.model.Result;
import kz.eugales.re4.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ConfirmRegistrationFragment extends Fragment {

    private static final String userId = "userId";
    private EditText etSmsCode;

    @Inject
    UserService userService;

    private long mUserId;

    private OnFragmentInteractionListener mListener;

    public ConfirmRegistrationFragment() {
        // Required empty public constructor
    }

    public static ConfirmRegistrationFragment newInstance(long id) {
        ConfirmRegistrationFragment fragment = new ConfirmRegistrationFragment();
        Bundle args = new Bundle();
        args.putLong(userId, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getLong(userId);// принимает от RegistrationFragment
        }
        ((CompanyApplication) getActivity().getApplication()).getUserComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_confirm_registration, container, false);
        etSmsCode = (EditText) view.findViewById(R.id.etSmsCode);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_next:
                String sms = etSmsCode.getText().toString();
                confirmUser(mUserId, sms);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void confirmUser(long userId, String sms) {

        userService.confirmUser(userId, sms).enqueue(new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                Result<String> result = response.body();
                if (isResultValid(result)) {

                    String hash = result.getValue().get(0);

                    if (hash.length() > 0) {
                        Map<String, String> prefValues = new HashMap<>();
                        prefValues.put(CONFIG.PREF_HASH, hash);
                        List<String> prefs = Utils.setPrefs(getActivity().getSharedPreferences(CONFIG.PREF_NAME, Context.MODE_PRIVATE), prefValues);

                        if(prefs.contains(CONFIG.PREF_HASH)) {

                            getFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container_reg, UserDetailUpdateFragment.newInstance())
                                    .commit();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

    boolean isResultValid(Result<String> result) {
        return result != null && result.getError().equals("0") && !result.getValue().isEmpty();
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
        void onFragmentInteraction(Uri uri);
    }
}
