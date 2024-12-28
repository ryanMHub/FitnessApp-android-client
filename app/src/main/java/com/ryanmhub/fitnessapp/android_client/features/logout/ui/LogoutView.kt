package com.ryanmhub.fitnessapp.android_client.features.logout.ui

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.ryanmhub.fitnessapp.android_client.R
import com.ryanmhub.fitnessapp.android_client.common.data_store.EncryptedDataManager
import com.ryanmhub.fitnessapp.android_client.common.data_store.authDataStore
import com.ryanmhub.fitnessapp.android_client.common.state.BaseAPIState
import com.ryanmhub.fitnessapp.android_client.features.logout.di.LogoutViewModel

@Composable
fun LogoutView(viewModel: LogoutViewModel, onLogout: () -> Unit){
    Log.d("LogoutView", "View is executed")
    //declare data Manager Todo: remove when logout relocation has occured
    val encryptedDataManager = EncryptedDataManager(LocalContext.current.authDataStore)
    //Todo: temporary logout for testing remove when completed
    val logoutState by viewModel.logoutState
    val goToLogin = remember { mutableStateOf(false) }

    LaunchedEffect(goToLogin.value){
        onLogout()
    }
    when(logoutState){
        is BaseAPIState.Loading -> {
            CircularProgressIndicator()
            Log.d("LogoutView", "After viewModel.logout()")

            Log.d("LogoutView", "After viewModel.logout()")
        }
        is BaseAPIState.Success -> {
            Log.d("LogoutView", "Right before Token removal")
            viewModel.removeTokenDataStore(encryptedDataManager, stringResource(R.string.access_token))
            viewModel.removeTokenDataStore(encryptedDataManager, stringResource(R.string.refresh_token))
            goToLogin.value = true
        }
        is BaseAPIState.Failed -> {
            //Todo: What goes here
        }
        is BaseAPIState.Error -> {
            //Todo: is this needed
        }
    }

    if (!goToLogin.value) {
        viewModel.logout()
    }
}