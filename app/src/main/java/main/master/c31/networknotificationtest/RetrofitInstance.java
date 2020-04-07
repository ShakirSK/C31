package main.master.c31.networknotificationtest;

import main.master.c31.Network.UserService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import static main.master.c31.Network.ApiUtils.BASE_URL;


public class RetrofitInstance {
    private static Retrofit retrofit = null;
    public static UserService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit.create(UserService.class);
    }
}