package kz.eugales.re4.dependencies.modules;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kz.eugales.re4.anotations.PerActivity;
import kz.eugales.re4.helper.Constants;
import kz.eugales.re4.helper.Utils;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Adil on 13.08.2016.
 */

@Module
public class NetworkModule {

    String baseUrl;
    String apiKey;

    String phoneNumber = "";
    String access_token = "";

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @PerActivity
    RxJavaCallAdapterFactory provideRxJavaCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    @Provides
    @PerActivity
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @PerActivity
    OkHttpClient provideOkHttpClient() {


        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Client-Id", Constants.CONFIG.APP_NAME)
                        .addHeader("RE-Phone-Number", phoneNumber)
                        .addHeader("RE-Access-Token", access_token);
                Request request = builder.build();
                return chain.proceed(request);
            }
        }).build();
    }

    @Provides
    @PerActivity
    Retrofit provideRetrofit(GsonConverterFactory gsonConverterFactory,
                             RxJavaCallAdapterFactory rxJavaCallAdapterFactory,
                             OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
    }
}
