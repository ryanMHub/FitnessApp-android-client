package com.ryanmhub.fitnessapp.android_client.features.login.di

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryanmhub.fitnessapp.android_client.common.data_store.EncryptedDataManager
import com.ryanmhub.fitnessapp.android_client.common.state.BaseAPIState
import com.ryanmhub.fitnessapp.android_client.features.login.data.LoginApi
import com.ryanmhub.fitnessapp.android_client.features.login.data.LoginDTO
import com.ryanmhub.fitnessapp.android_client.features.login.data.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel(){
    private val _loginApiService = LoginApi.create()
    private val _loginState = mutableStateOf<BaseAPIState<LoginResponse>>(BaseAPIState.Loading)
    val loginState: State<BaseAPIState<LoginResponse>> = _loginState


    //Call api to login user. Control state based on result from api call
    fun loginUser(username : String, password : String){
        viewModelScope.launch {
            _loginState.value = BaseAPIState.Loading

            if(_loginApiService==null) {
                Log.d("LoginViewModel", "_loginApi is null")
                return@launch
            }

            try{
                Log.d("LoginViewModel","Starting Login Request")
                val request = LoginDTO(username, password)
                Log.d("LoginViewModel",request.toString())
                val response = _loginApiService.loginUser(request)
                Log.d("LoginViewModel",response.toString())
                if(response.isSuccessful){
                    if(response.body()?.success == true){
                        _loginState.value = BaseAPIState.Success(response.body())
                    } else {
                        _loginState.value = BaseAPIState.Failed(response.body())
                    }
                } else {
                    _loginState.value = BaseAPIState.Error(response.message())
                }
            } catch(e:Exception){
                Log.d("LoginViewModel",e.message.toString())
                _loginState.value = BaseAPIState.Error(e.message ?: "Unknown Error")
            }

        }
    }

    //Encrypt and store Access and Refresh tokens
    fun saveTokenDataStore(dataStore : EncryptedDataManager, key : String, data : String){
        viewModelScope.launch {
            dataStore.saveEncryptedData(key, data)
        }
    }
}