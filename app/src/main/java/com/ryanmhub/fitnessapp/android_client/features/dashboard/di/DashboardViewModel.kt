package com.ryanmhub.fitnessapp.android_client.features.dashboard.di

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryanmhub.fitnessapp.android_client.common.data.UserDTO
import com.ryanmhub.fitnessapp.android_client.common.retrofit.RetrofitInstances
import com.ryanmhub.fitnessapp.android_client.common.state.BaseAPIState
import com.ryanmhub.fitnessapp.android_client.features.dashboard.data.DashboardApi
import com.ryanmhub.fitnessapp.android_client.features.dashboard.data.UserDataResponse
import com.ryanmhub.fitnessapp.android_client.features.login.data.LoginDTO
import com.ryanmhub.fitnessapp.android_client.features.login.data.LoginResponse
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private val _dashboardApiService = DashboardApi.create()
    private val _dataState = mutableStateOf<BaseAPIState<UserDataResponse>>(BaseAPIState.Loading)
    val dataState: State<BaseAPIState<UserDataResponse>> = _dataState

    fun getUserData() {
        viewModelScope.launch {
            _dataState.value = BaseAPIState.Loading

            if(_dashboardApiService==null){
                Log.d("DashboardViewModel","_dashboardApiService is null")
                return@launch
            }

            try{
                val response = _dashboardApiService.getUserData()

                if(response.isSuccessful){
                    if(response.body()?.success == true){
                        _dataState.value = BaseAPIState.Success(response.body())
                    } else {
                        _dataState.value = BaseAPIState.Failed(response.body())
                    }
                } else {
                    _dataState.value = BaseAPIState.Error(response.message())
                }
            } catch(e:Exception) {
                _dataState.value = BaseAPIState.Error(e.message ?: "Unknown Error")
            }
        }


    }

}