package com.solidict.meraklysdk;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by emrahkucuk on 01/12/2017.
 */

public class NetworkHandler {

    private static NetworkService networkService;

    public static NetworkService getInstance() {

        if (networkService == null) {


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://merakly.sickthread.com/device/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            networkService = retrofit.create(NetworkService.class);
        }
        return networkService;
    }
}
