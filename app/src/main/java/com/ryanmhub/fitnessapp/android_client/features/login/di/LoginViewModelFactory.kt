package com.ryanmhub.fitnessapp.android_client.features.login.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ryanmhub.fitnessapp.android_client.common.data_store.EncryptedDataManager
import com.ryanmhub.fitnessapp.android_client.common.data_store.authDataStore

class LoginViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun<T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            val encryptedDataManager = EncryptedDataManager(context.authDataStore)
            return LoginViewModel(encryptedDataManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}