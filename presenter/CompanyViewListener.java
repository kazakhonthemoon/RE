package kz.eugales.re4.presenter;

import kz.eugales.re4.model.Company;
import kz.eugales.re4.model.Result;
import rx.Observable;

/**
 * Created by Adil on 13.08.2016.
 */
public interface CompanyViewListener {

    void onCompleted();
    void onError(Throwable throwable);
    void onCompanies(Result<Company> companies);
    Observable<Result<Company>> getCompanies();

}
