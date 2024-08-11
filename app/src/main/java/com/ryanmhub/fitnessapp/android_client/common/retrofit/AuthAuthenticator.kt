package com.ryanmhub.fitnessapp.android_client.common.retrofit

import android.util.Log
import androidx.compose.ui.res.stringResource
import com.ryanmhub.fitnessapp.android_client.R
import com.ryanmhub.fitnessapp.android_client.common.data_store.EncryptedDataManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.internal.synchronized
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AuthAuthenticator(private val encryptedDataManager: EncryptedDataManager) : Authenticator {
    private val mutex = Mutex()
    private val refreshTokenApiService = RefreshTokenApi.create()

    //Todo: have a better solution for these constants
    companion object {
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val AUTHORIZATION = "Authorization"
        const val TOKEN_TYPE = "Bearer"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val currentToken = runBlocking {
            encryptedDataManager.getEncryptedData(ACCESS_TOKEN).first()
        }

        //Todo: I'm going to need a different solution here
        if(refreshTokenApiService==null) return null

        //Todo: finish AuthAuthenticator. Fix the body.accessToken and body.refreshToken. Add interceptor build client add to retrofit
        return runBlocking {
            mutex.withLock {
                Log.d("AuthAuthenticator", "Starting authentication")
                val updatedToken = encryptedDataManager.getEncryptedData(ACCESS_TOKEN).first()
                val token = if(currentToken != updatedToken){
                    updatedToken
                } else {
                    val newSessionResponse = refreshTokenApiService.getNewAccessToken()
                    if(newSessionResponse.isSuccessful && newSessionResponse.body() != null){
                        newSessionResponse.body()?.let { body ->
                            encryptedDataManager.saveEncryptedData(ACCESS_TOKEN, body.accessToken)
                            encryptedDataManager.saveEncryptedData(REFRESH_TOKEN, body.refreshToken)
                            body.accessToken
                        }
                    } else null
                }
                if(token != null){
                    response.request.newBuilder()
                        .header(AUTHORIZATION, "$TOKEN_TYPE $token")
                        .build()
                } else {
                    null
                }
            }
        }
    }
}