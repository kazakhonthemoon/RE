package kz.eugales.re4.dependencies.components;

import javax.inject.Singleton;

import dagger.Component;
import kz.eugales.re4.MainActivity;
import kz.eugales.re4.anotations.PerActivity;
import kz.eugales.re4.dependencies.modules.NetworkModule;
import retrofit2.Retrofit;

/**
 * Created by Adil on 13.08.2016.
 */

@Component(modules = NetworkModule.class)
@PerActivity
public interface NetworkComponent {
    Retrofit retrofit();

}
