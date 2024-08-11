package com.ryanmhub.fitnessapp.android_client.common.retrofit

import android.util.Log
import androidx.compose.ui.res.stringResource
import com.ryanmhub.fitnessapp.android_client.R
import com.ryanmhub.fitnessapp.android_client.common.data_store.EncryptedDataManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val encryptedDataManager: EncryptedDataManager) : Interceptor {

    //Todo: Create a const string file for all common literals outside of composables
    companion object {
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val AUTHORIZATION = "Authorization"
        const val TOKEN_TYPE = "Bearer"
    }

    private val whiteList = listOf(
        "/api/auth/register",
        "/api/auth/login"
    )

    private val refreshList = listOf(
        "/api/auth/refresh-token"
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.toString()
        Log.d("AuthInterceptor", "Starting interceptor")
        //check to see if the address is whitelisted
        if(whiteList.any { url.contains(it) }) {
            return chain.proceed(request)
        }

        //add Access Token to header if url is not whitelisted
        val token: String? = runBlocking {
            if(refreshList.any { url.contains(it) }) {
                encryptedDataManager.getEncryptedData(REFRESH_TOKEN).first()
            } else {
                encryptedDataManager.getEncryptedData(ACCESS_TOKEN).first()
            }
        }

        val authenticatedRequest = request.newBuilder()
            .addHeader(AUTHORIZATION, "$TOKEN_TYPE $token")
            .build()

        Log.d("AuthInterceptor", "Returing authenticated request: $authenticatedRequest")
        return chain.proceed(authenticatedRequest)
    }
}