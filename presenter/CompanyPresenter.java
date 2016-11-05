package kz.eugales.re4.presenter;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmResults;
import kz.eugales.re4.helper.Utils;
import kz.eugales.re4.model.Company;
import kz.eugales.re4.model.Result;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Adil on 13.08.2016.
 */
public class CompanyPresenter extends BasePresenter implements Observer<Result<Company>> {

    CompanyViewListener mCompanyViewListener;

    public CompanyPresenter(CompanyViewListener mCompanyViewListener) {
        this.mCompanyViewListener = mCompanyViewListener;
    }

    @Override
    public void onCompleted() {
        mCompanyViewListener.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        mCompanyViewListener.onError(e);
    }

    @Override
    public void onNext(Result<Company> companies) {
        mCompanyViewListener.onCompanies(companies);
    }

    public void fetchCompanies(Context context) {

        unsubscribeAll();
        if (Utils.isNetworkAvailable(context)) {
            subscribe(mCompanyViewListener.getCompanies(), this);
        } else {
            Realm realm = Realm.getDefaultInstance();


            realm.where(Company.class).findAll().asObservable()
                    .map(new Func1<RealmResults<Company>, Observable<Result<Company>>>() {
                        @Override
                        public Observable<Result<Company>> call(RealmResults<Company> companies) {
                            Result<Company> companyResult = new Result<>();
                            companyResult.setValue(companies);
                            return Observable.just(companyResult);
                        }
                    }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Observable<Result<Company>>>() {
                @Override
                public void call(Observable<Result<Company>> companyResult) {
                    subscribe(companyResult, CompanyPresenter.this);
                }
            });
        }
    }
}
