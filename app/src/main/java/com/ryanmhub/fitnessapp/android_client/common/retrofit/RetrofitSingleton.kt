package com.ryanmhub.fitnessapp.android_client.common.retrofit

import android.content.Context
import android.util.Log
import com.ryanmhub.fitnessapp.android_client.common.data_store.EncryptedDataManager
import com.ryanmhub.fitnessapp.android_client.common.data_store.authDataStore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitSingleton {

    private lateinit var retrofit: Retrofit
    private lateinit var encryptedDataManager: EncryptedDataManager

    //Initialize the Retrofit singleton
    fun init(context: Context){
        Log.d("RetrofitSingleton","init")
        encryptedDataManager = EncryptedDataManager(context.authDataStore)

        retrofit = Retrofit.Builder()
            .baseUrl(ApiBaseUrl.BASE_URL_API)
            .client(
                OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor(AuthInterceptor(encryptedDataManager))
                .authenticator(AuthAuthenticator(encryptedDataManager))
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //return the singleton retrofit instance
    fun getRetrofit(): Retrofit? {
        if(!::retrofit.isInitialized) {
            Log.d("RetrofitSingleton", "Retrofit not initialized")
            //throw IllegalStateException("Retrofit not initialized")
            return null
        }
        return retrofit
    }
}