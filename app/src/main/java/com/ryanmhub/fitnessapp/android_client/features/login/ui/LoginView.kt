package com.ryanmhub.fitnessapp.android_client.features.login.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ryanmhub.fitnessapp.android_client.R
import com.ryanmhub.fitnessapp.android_client.common.nav.NavRouter
import com.ryanmhub.fitnessapp.android_client.common.nav.Screen
import com.ryanmhub.fitnessapp.android_client.common.components.*
import com.ryanmhub.fitnessapp.android_client.common.data_store.EncryptedDataManager
import com.ryanmhub.fitnessapp.android_client.common.data_store.authDataStore
import com.ryanmhub.fitnessapp.android_client.common.state.BaseAPIState
import com.ryanmhub.fitnessapp.android_client.features.dashboard.ui.DashboardView
import com.ryanmhub.fitnessapp.android_client.features.login.di.LoginViewModel


@Composable
fun LoginView(viewModel: LoginViewModel){

    val encryptedDataManager = EncryptedDataManager(LocalContext.current.authDataStore)
    val loginState by viewModel.loginState

    //Todo: remember to remove test values
    //Textfield mutable state
    val (username, setUsername) = remember { mutableStateOf("rmosk")}
    val (password, setPassword) = remember { mutableStateOf("123abc")}

    //State Machine Controller
    when(loginState){
        is BaseAPIState.Loading -> {
            CircularProgressIndicator()
        }
        is BaseAPIState.Success -> {
            val data = (loginState as BaseAPIState.Success).data
            val showDialog = remember { mutableStateOf(true)}
            //Todo: You only need the popup if the login is not successful, otherwise the app should got to dashboard
            //PopUpComponent(stringResource(R.string.success),"${data?.success} ${data?.message} ${data?.accessToken} ${data?.refreshToken}", showDialog)
            Log.d("LoginView","${data?.success}" + "  " + "${data?.message}" + "${data?.accessToken}" + "${data?.refreshToken}")

            viewModel.saveTokenDataStore(encryptedDataManager,
                stringResource(R.string.access_token), data?.accessToken ?: "NA")
            viewModel.saveTokenDataStore(encryptedDataManager,
                stringResource(R.string.refresh_token), data?.refreshToken ?: "NA")

            NavRouter.navigateTo(Screen.DashboardView)

            //Todo: This is a Test to see if the dataSource is working remove when done
//            val accessFlow = viewModel.testGetTokenData(LocalContext.current.authDataStore, stringResource(R.string.access_token))
//            val refreshFlow = viewModel.testGetTokenData(LocalContext.current.authDataStore, stringResource(R.string.refresh_token))
//            var accessToken by remember { mutableStateOf<String?>(null)}
//            var refreshToken by remember { mutableStateOf<String?>(null)}
//            LaunchedEffect(accessToken){
//                accessFlow.collect { value ->
//                    accessToken = value
//                }
//            }
//            LaunchedEffect(refreshToken){
//                refreshFlow.collect { value ->
//                    refreshToken = value
//                }
//            }
//            PopUpComponent(stringResource(R.string.success), "$accessToken put a lot of stuff here $refreshToken", showDialog)
            //Todo: How should I appropriately handle navigation to Home Dashboard, and adding access token to the header.
        }
        is BaseAPIState.Failed -> {
            val data = (loginState as BaseAPIState.Failed).data
            val showDialog = remember { mutableStateOf(true)}
            PopUpComponent(stringResource(R.string.failed), data?.message, showDialog)
            Log.d("LoginView","${data?.message}")
        }
        is BaseAPIState.Error -> {
            val errorMessage = (loginState as BaseAPIState.Error).message
            val showDialog = remember { mutableStateOf(true)}
            PopUpComponent(stringResource(R.string.error), errorMessage.toString(), showDialog)
            Log.d("LoginView", errorMessage.toString())
        }
    }

    //UI
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()
            .padding(30.dp)) {

            //Header
            NormalTextComponent(value = stringResource(id = R.string.login))
            HeaderTextComponent(value = stringResource(id = R.string.welcome))

            Spacer(modifier = Modifier.height(20.dp))

            //Enter data points for LoginDTO
            StandTextField(labelValue = stringResource(id = R.string.username), painterResource(id = R.drawable.crystal), setUsername, username)
            PasswordTextField(labelValue = stringResource(id = R.string.password), painterResource(id = R.drawable.crystal), setPassword, password) //Todo: should I check some textfields by having user reenter the same value.

            Spacer(modifier = Modifier.height(40.dp))

            //Forgot Users password
            UnderLinedTextComponent(value = stringResource(id = R.string.forgt_password))

            Spacer(modifier = Modifier.height(40.dp))

            //Call the LoginViewModel to attempt login thru api
            ButtonComponent(value = stringResource(id = R.string.login), onButtonClicked = {viewModel.loginUser(username, password)})

            Spacer(modifier = Modifier.height(20.dp))
            DividerTextComponent()

            Spacer(modifier = Modifier.height(40.dp))

            //Don't have an account navigate to register new user
            ClickableTextComponentEnding(initialText = stringResource(id = R.string.no_account), ending = stringResource(id = R.string.register), onTextSelected = {
                NavRouter.navigateTo(Screen.RegisterView)
            })
        }
    }
}

@Preview
@Composable
fun LoginViewPreview() {
    LoginView(viewModel = viewModel())
}