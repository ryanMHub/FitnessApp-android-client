package com.ryanmhub.fitnessapp.android_client.features.register.di

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryanmhub.fitnessapp.android_client.common.state.BaseAPIState
import com.ryanmhub.fitnessapp.android_client.common.data.UserDTO
import com.ryanmhub.fitnessapp.android_client.features.register.data.RegisterApi
import com.ryanmhub.fitnessapp.android_client.features.register.data.RegisterResponse
import kotlinx.coroutines.launch

//Todo: What approach should I use to catch exceptions
//Todo: update state machine to universal model
class RegisterViewModel() : ViewModel() {
    private val _registerApiService = RegisterApi.create()
    private val _registrationState = mutableStateOf<BaseAPIState<RegisterResponse>>(BaseAPIState.Loading)
    val registrationState : State<BaseAPIState<RegisterResponse>> = _registrationState

    fun registerUser(firstName : String, lastName : String, email : String, username : String, password : String) {
        viewModelScope.launch {
            _registrationState.value = BaseAPIState.Loading

            if(_registerApiService==null) {
                Log.d("RegisterViewModel","registerApiService is null")
                return@launch
            }

            try{
                Log.d("RegisterViewModel", "Starting register")
                val request = UserDTO(firstName, lastName, username,email, password)
                Log.d("RegisterViewModel", request.toString())
                val response = _registerApiService.registerUser(request)
                Log.d("RegisterViewModel", response.headers().toString())
                Log.d("RegisterViewModel", response.body().toString())
                if(response.isSuccessful){
                    if(response.body()?.success == true){
                        _registrationState.value = BaseAPIState.Success(response.body())
                    } else {
                        _registrationState.value = BaseAPIState.Failed(response.body())
                    }
                } else {
                    _registrationState.value = BaseAPIState.Error(response.message())
                }
            } catch (e: Exception){
                Log.d("RegisterViewModel", e.message.toString())
                _registrationState.value = BaseAPIState.Error(e.message ?: "Unknown error")
            }
        }
    }

}