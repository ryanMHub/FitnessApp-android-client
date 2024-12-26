package com.ryanmhub.fitnessapp.android_client.common.retrofit

import android.util.Log
import androidx.compose.ui.res.stringResource
import com.ryanmhub.fitnessapp.android_client.R
import com.ryanmhub.fitnessapp.android_client.common.constants.StringConstants
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

    override fun authenticate(route: Route?, response: Response): Request? {
        val currentToken = runBlocking {
            encryptedDataManager.getEncryptedData(StringConstants.ACCESS_TOKEN).first()
        }

        if(refreshTokenApiService==null) return null

        //Todo: finish AuthAuthenticator. Fix the body.accessToken and body.refreshToken. Add interceptor build client add to retrofit?? Not sure why I put this here
        return runBlocking {
            mutex.withLock {
                Log.d("AuthAuthenticator", "Starting authentication")
                val updatedToken = encryptedDataManager.getEncryptedData(StringConstants.ACCESS_TOKEN).first()
                val token = if(currentToken != updatedToken){
                    updatedToken
                } else {
                    val newSessionResponse = refreshTokenApiService.getNewAccessToken()
                    if(newSessionResponse.isSuccessful && newSessionResponse.body() != null){
                        newSessionResponse.body()?.let { body ->
                            encryptedDataManager.saveEncryptedData(StringConstants.ACCESS_TOKEN, body.accessToken)
                            encryptedDataManager.saveEncryptedData(StringConstants.REFRESH_TOKEN, body.refreshToken)
                            body.accessToken
                        }
                    } else null
                }
                if(token != null){
                    response.request.newBuilder()
                        .header(StringConstants.AUTHORIZATION, "${StringConstants.TOKEN_TYPE} $token")
                        .build()
                } else {
                    null
                }
            }
        }
    }
}