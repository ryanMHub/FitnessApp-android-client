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
import androidx.compose.ui.tooling.preview.PreviewParameter
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
import com.ryanmhub.fitnessapp.android_client.features.login.data.LoginResponse
import com.ryanmhub.fitnessapp.android_client.features.login.di.LoginViewModel
import com.ryanmhub.fitnessapp.android_client.features.login.di.LoginViewModelFactory


@Composable
fun LoginView( onNavigateToMain: () -> Unit, onNavigateToRegister: () -> Unit, preUsername: String) {
    val viewModel : LoginViewModel = viewModel(factory = LoginViewModelFactory(LocalContext.current))
    val loginState by viewModel.loginState

    //Todo: remember to remove test values
    //Textfield mutable state
    val (username, setUsername) = remember { mutableStateOf(preUsername)}
    val (password, setPassword) = remember { mutableStateOf("12345678@A")}

    //State Machine Controller
    when(loginState){
        is BaseAPIState.Loading -> {
            CircularProgressIndicator()
        }
        is BaseAPIState.Success -> {
            val data = (loginState as BaseAPIState.Success).data
            val showDialog = remember { mutableStateOf(true)}
            Log.d("LoginView","${data?.success}" + "  " + "${data?.message}" + "${data?.accessToken}" + "${data?.refreshToken}") //Todo: what should I do with all the log statements
            viewModel.setLoginState(BaseAPIState.Loading);
            //NavRouter.navigateTo(Screen.DashboardView)
            onNavigateToMain()
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
            PasswordTextField(labelValue = stringResource(id = R.string.password), painterResource(id = R.drawable.crystal), setPassword, password)

            Spacer(modifier = Modifier.height(40.dp))

            //Forgot Users password
            UnderLinedTextComponent(value = stringResource(id = R.string.forgt_password))

            Spacer(modifier = Modifier.height(40.dp))

            //Call the LoginViewModel to attempt login thru api
            ButtonComponent(value = stringResource(id = R.string.login), true, onButtonClicked = {viewModel.loginUser(username, password)})

            Spacer(modifier = Modifier.height(20.dp))
            DividerTextComponent()

            Spacer(modifier = Modifier.height(40.dp))

            //Don't have an account navigate to register new user
            ClickableTextComponentEnding(initialText = stringResource(id = R.string.no_account), ending = stringResource(id = R.string.register), onTextSelected = {
//                NavRouter.navigateTo(Screen.RegisterView)
                onNavigateToRegister()
            })
        }
    }
}

@Preview
@Composable
fun LoginViewPreview() {
    LoginView(
        { println("Dog")},
        {println("Register")},
        ""
    )
}