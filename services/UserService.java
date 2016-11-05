package kz.eugales.re4.services;

import kz.eugales.re4.model.Result;
import kz.eugales.re4.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Adil on 22.09.2016.
 */
public interface UserService {

    @GET("createUser")
    Call<Result<Long>> createUser(@Query("phoneNumber") String phoneNumber);//return id

    @GET("confirmUser")
    Call<Result<String>> confirmUser(@Query("id") long id, @Query("smsCode") String smsCode);//return hash

    @GET("updateUser")
    String updateUser(@Query("housingEstate") String housingEstate,
                      @Query("home") long home,
                      @Query("porch") long porch,
                      @Query("flat") long flat);//return ErrorCode SUCCESS =0

    @GET("getUser")
    Call<Result<User>> getUser();//return User
}
