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

//Todo: What approach should I use to catch exceptions, meaning what should the user see, and what should the application proceed to do.

class RegisterViewModel() : ViewModel() {

    companion object{
        const val MIN_PASSWORD_LENGTH = 8
    }

    //declaration of API call for registration
    private val _registerApiService = RegisterApi.create()
    //registration state values
    private val _registrationState = mutableStateOf<BaseAPIState<RegisterResponse>>(BaseAPIState.Loading)
    val registrationState : State<BaseAPIState<RegisterResponse>> = _registrationState

    //register user call to the API
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

    //todo: delete temporary values once done testing
    private val _password = mutableStateOf("12345678@A")
    val password: State<String> = _password
    private val _verPass = mutableStateOf("12345678@A")
    val verPass: State<String> = _verPass
    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage
    private val _canRegister = mutableStateOf(false)
    val canRegister: State<Boolean> = _canRegister

    //Change value of password
    fun onPasswordChange(newPassword: String){
        _password.value = newPassword
        isValidPassword(newPassword)
    }

    //Change value of verPass
    fun onVerPassChange(newVerPass: String){
        _verPass.value = newVerPass
        hasDuplicate(newVerPass)
    }

    //Check to see if given password passes all the parameters
    fun isValidPassword(password: String){
        val lenFilter = password.length >= MIN_PASSWORD_LENGTH
        val hasNumber = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }
        val hasUpperCase = password.any { it.isUpperCase() }

        _canRegister.value = lenFilter && hasNumber && hasSpecialChar && hasUpperCase

        _errorMessage.value = when {
            password.isEmpty() -> "Password should not be empty"
            !lenFilter -> "Password should contain at least $MIN_PASSWORD_LENGTH chars"
            !hasNumber -> "Password should contain at least 1 number"
            !hasSpecialChar -> "Password should contain at least 1 special char"
            !hasUpperCase -> "Password should contain at least 1 uppercase"
            else -> ""
        }

    }

    //Check that both of the passwords match
    fun hasDuplicate(newVerPass : String){
        if(newVerPass != _password.value) {
            _errorMessage.value = "Passwords do not match"
            _canRegister.value = false
        } else {
            _errorMessage.value = ""
            _canRegister.value = true
        }
    }

}