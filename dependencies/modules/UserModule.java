package kz.eugales.re4.dependencies.modules;

/**
 * Created by Adil on 22.09.2016.
 */

import dagger.Module;
import dagger.Provides;
import kz.eugales.re4.anotations.PerApplication;
import kz.eugales.re4.services.UserService;
import retrofit2.Retrofit;

@Module
public class UserModule {

    @Provides
    @PerApplication
    UserService provideUserService(Retrofit retrofit){
        return retrofit.create(UserService.class);
    }
}
