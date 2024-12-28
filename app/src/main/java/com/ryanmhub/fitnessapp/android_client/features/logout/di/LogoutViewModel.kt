package com.ryanmhub.fitnessapp.android_client.features.logout.di

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryanmhub.fitnessapp.android_client.common.data_store.EncryptedDataManager
import com.ryanmhub.fitnessapp.android_client.common.state.BaseAPIState
import com.ryanmhub.fitnessapp.android_client.features.logout.data.LogoutApi
import com.ryanmhub.fitnessapp.android_client.features.logout.data.LogoutResponse
import kotlinx.coroutines.launch

class LogoutViewModel : ViewModel() {
    private val _logoutApiService = LogoutApi.create()
    private val _logoutState = mutableStateOf<BaseAPIState<LogoutResponse>>(BaseAPIState.Loading)
    val logoutState: State<BaseAPIState<LogoutResponse>> = _logoutState

    //Todo: remove and relocate
    //Used to invalidate tokens on the server side
    fun logout() {
        viewModelScope.launch {
            _logoutState.value = BaseAPIState.Loading

            if(_logoutApiService==null){
                Log.d("DashboardViewModel","_dashboardApiService is null")
                return@launch
            }

            try {
                val response = _logoutApiService.logoutUser()

                if(response.isSuccessful){
                    if(response.body()?.success == true){
                        _logoutState.value = BaseAPIState.Success(response.body())
                    } else {
                        _logoutState.value = BaseAPIState.Failed(response.body())
                    }
                } else {
                    _logoutState.value = BaseAPIState.Error(response.message())
                }
            } catch (e: Exception){
                _logoutState.value = BaseAPIState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    //Todo: remove and relocate, add a token service
    //Used in logout process to delete the tokens in the data store
    fun removeTokenDataStore(dataStore : EncryptedDataManager, key : String){
        viewModelScope.launch {
            dataStore.removeEncryptedData(key);
        }
    }
}