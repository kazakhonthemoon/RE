package kz.eugales.re4.dependencies.modules;

import dagger.Module;
import dagger.Provides;
import kz.eugales.re4.anotations.PerApplication;
import kz.eugales.re4.services.CompanyService;
import retrofit2.Retrofit;

/**
 * Created by Adil on 11.08.2016.
 */

@Module
public class ApiModule {

    @Provides
    @PerApplication
    CompanyService provideCompanyService(Retrofit retrofit){
        return retrofit.create(CompanyService.class);
    }

}
