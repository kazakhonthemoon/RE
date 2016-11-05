package kz.eugales.re4.services;

import java.util.List;

import kz.eugales.re4.model.Company;
import kz.eugales.re4.model.Result;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Adil on 11.08.2016.
 */
public interface CompanyService {

    @GET("getCompany")
    Observable<Result<Company>> getCompany(@Query("companyId") long id);

    @GET("getCompanies")
    Observable<Result<Company>> getCompanies();
}
