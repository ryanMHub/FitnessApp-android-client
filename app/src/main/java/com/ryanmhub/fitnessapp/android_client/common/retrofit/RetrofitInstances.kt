package com.ryanmhub.fitnessapp.android_client.common.retrofit

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ryanmhub.fitnessapp.android_client.common.data_store.EncryptedDataManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//Todo: Delete when finished overhauling Retrofit

//Todo: I may need to do some work here with controlling when JWT Tokens are deleted from the DataStore
//Todo: And when refresh tokens are useful and how the interceptor would work
//Todo: Delete on logout, and when app is closed.
object RetrofitInstances {

    //private val encryptedDataManager: EncryptedDataManager = EncryptedDataManager(dataStore)

    //Todo: I may change this approach. Maybe redo the whole retrofit object
    private lateinit var encryptedDataManager : EncryptedDataManager

    //Set the encryptedDataManager using the associated dataStore
    fun setEncryptedDataManager(dataStore : DataStore<Preferences>) {
        this.encryptedDataManager = EncryptedDataManager(dataStore)
    }

    //Configure logging interceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    //Todo: You will need to create an interceptor that starts normal with no JWT and then adds one after it is created in login
    //Todo: if encryptedDataManager has not be initialized before calling AuthInterceptor() this function will throw a UninitializedPropertyAccessException exception
    //create client adding Authenticator and interceptors
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(AuthInterceptor(encryptedDataManager))
        .authenticator(AuthAuthenticator(encryptedDataManager)) //todo: should I create an instance once for this AuthAuthenticator and AuthInterceptor
        .build()

    //Build a connection with BASE_URL_API
    private val apiConnection by lazy {
        Retrofit.Builder()
            .baseUrl(ApiBaseUrl.BASE_URL_API)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //refresh accessTokens
    val refreshTokenService: RefreshTokenService by lazy {
        Log.d("RetrofitInstances", "refreshTokenService")
        apiConnection.create(RefreshTokenService::class.java)
    }

    //all network calls associated with registration and login
    val userService : UserApiService by lazy {
        Log.d("RetrofitInstances", "userService")
        apiConnection.create(UserApiService::class.java)
    }

    //all network calls to Dashboard related issues
    val dashboardService : DashboardApiService by lazy {
        Log.d("RetrofitInstances", "dashboardService")
        apiConnection.create(DashboardApiService::class.java)
    }


}