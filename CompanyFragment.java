package kz.eugales.re4;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import io.realm.Realm;
import kz.eugales.re4.helper.Constants.*;
import kz.eugales.re4.model.Company;
import kz.eugales.re4.adapter.CompanyAdapter;
import kz.eugales.re4.model.Result;
import kz.eugales.re4.presenter.CompanyPresenter;
import kz.eugales.re4.presenter.CompanyViewListener;
import kz.eugales.re4.services.CompanyService;
import rx.Observable;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CompanyFragment extends Fragment implements CompanyViewListener {

    @Inject
    CompanyService companyService;

    Realm realm;

    private String phoneNumber;
    private String hash;

    private OnListFragmentInteractionListener mListener;
    private CompanyAdapter mCompanyAdapter;
    private CompanyPresenter mCompanyPresenter;

    @BindView(R.id.fragment_container)
    private RecyclerView mRecyclerView;

    private ProgressDialog mProgressDialog;

    public CompanyFragment() {

    }


    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CompanyFragment newInstance() {

        CompanyFragment fragment = new CompanyFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        phoneNumber = bundle.getString(CONFIG.PREF_PHONE_NUMBER);
        hash = bundle.getString(CONFIG.PREF_HASH);

        mCompanyAdapter = new CompanyAdapter(mListener);

        ((CompanyApplication)getActivity().getApplication()).getApiComponent().inject(CompanyFragment.this);

        mCompanyPresenter = new CompanyPresenter(this);
        mCompanyPresenter.onCreate();


        //mDatabase = new DatabaseHelper(getContext());

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("Загрузка...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);

        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onResume() {
        super.onResume();
        //if(Utils.isNetworkAvailable(getContext())) {
            mCompanyPresenter.onResume();
            mCompanyPresenter.fetchCompanies(getContext());
            mProgressDialog.show();
        //}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerView.setAdapter(mCompanyAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Company company);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable throwable) {
        Log.d("d", throwable.getMessage());
        throwable.printStackTrace();
    }

    @Override
    public void onCompanies(Result<Company> companies) {
        realm.beginTransaction();
        realm.insertOrUpdate(companies.getValue());
        realm.commitTransaction();

        mCompanyAdapter.addCompanies(realm.where(Company.class).findAll());
        mProgressDialog.dismiss();
    }

    @Override
    public Observable<Result<Company>> getCompanies() {

        Observable<Result<Company>> companyObservable = companyService.getCompanies(/*phoneNumber, hash*/);
        return companyObservable;
    }

}
