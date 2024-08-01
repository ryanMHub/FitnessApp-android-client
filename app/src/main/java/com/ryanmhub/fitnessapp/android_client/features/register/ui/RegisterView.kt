package com.ryanmhub.fitnessapp.android_client.features.register.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ryanmhub.fitnessapp.android_client.R
import com.ryanmhub.fitnessapp.android_client.app.NavRouter
import com.ryanmhub.fitnessapp.android_client.app.Screen
import com.ryanmhub.fitnessapp.android_client.common.components.*
import com.ryanmhub.fitnessapp.android_client.common.state.BaseAPIState
import com.ryanmhub.fitnessapp.android_client.features.register.di.RegisterViewModel

@Composable
fun RegisterView(viewModel: RegisterViewModel){
    //connect to state machine in viewModel
    val registrationState by viewModel.registrationState //Todo: Should this be remember by

    //Todo: remember to remove test values
    //Textfields mutable states
    val (firstName, setFname) = remember {mutableStateOf("Ryan")}
    val (lastName, setLname) = remember {mutableStateOf("Moskovciak")}
    val (email, setEmail) = remember {mutableStateOf("rmoskovciak@gmail.com")}
    val (username, setUsername) = remember {mutableStateOf("rmosk")}
    val (password, setPassword) = remember {mutableStateOf("123abc")}

    //State Machine action controller
    when(registrationState){
        is BaseAPIState.Loading -> {
            CircularProgressIndicator()
        }
        is BaseAPIState.Success -> {
            val data = (registrationState as BaseAPIState.Success).data
            val showDialog = remember { mutableStateOf(true)}
            PopUpComponent(stringResource(R.string.success), data?.message, showDialog)
            Log.d("RegisterView","${data?.success}" + "  " + "${data?.message}")
            //Todo: How should I appropriately handle navigation to login, or should user automatically be logged in
            if(!showDialog.value) NavRouter.navigateTo(Screen.LoginView)
        }
        is BaseAPIState.Failed -> {
            val data = (registrationState as BaseAPIState.Failed).data
            val showDialog = remember { mutableStateOf(true)}
            PopUpComponent(stringResource(R.string.failed), data?.message, showDialog)
            Log.d("RegisterView","${data?.message}")
        }
        is BaseAPIState.Error -> {
            val errorMessage = (registrationState as BaseAPIState.Error).message
            val showDialog = remember { mutableStateOf(true)}
            PopUpComponent(stringResource(R.string.error), errorMessage.toString(), showDialog)
            Log.d("RegisterView", errorMessage.toString())
        }
    }


    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    ){
        Log.d("RegisterView", "Start the View")
        Column(modifier = Modifier.fillMaxSize()
            .padding(30.dp)) {

            //Header
            NormalTextComponent(value = stringResource(id = R.string.hello))
            HeaderTextComponent(value = stringResource(id = R.string.createAcc))

            Spacer(modifier = Modifier.height(20.dp))

            //Textfields to enter data points for new user
            StandTextField(labelValue = stringResource(id = R.string.fName), painterResource(id = R.drawable.crystal),setFname, firstName)
            StandTextField(labelValue = stringResource(id = R.string.lName), painterResource(id = R.drawable.crystal), setLname, lastName)
            StandTextField(labelValue = stringResource(id = R.string.email), painterResource(id = R.drawable.crystal), setEmail, email)
            StandTextField(labelValue = stringResource(id = R.string.username), painterResource(id = R.drawable.crystal), setUsername, username)
            PasswordTextField(labelValue = stringResource(id = R.string.password), painterResource(id = R.drawable.crystal), setPassword, password) //Todo: should I check some textfields by having user reenter the same value.

            //Terms and Conditions approval checkbox
            CheckBoxComponent(value = stringResource(id = R.string.message), onTextSelected = {
                NavRouter.navigateTo(Screen.TermsAndConditions)
            })

            //Check that values are set properly
            Log.d("RegisterView", "$firstName $lastName $email $username $password")

            Spacer(modifier = Modifier.height(40.dp))

            //Call to RegisterViewModel to attempt user registration
            ButtonComponent(value = stringResource(id = R.string.register), onButtonClicked = {viewModel.registerUser(firstName, lastName, email, username, password)})

            Log.d("RegisterView", "Button Clicked")

            Spacer(modifier = Modifier.height(20.dp))
            DividerTextComponent()

            Spacer(modifier = Modifier.height(40.dp))

            //Already have an account navigate to LoginView
            ClickableTextComponentEnding(initialText = stringResource(id = R.string.already_registered), ending = stringResource(id = R.string.login), onTextSelected = {
                NavRouter.navigateTo(Screen.LoginView)
            })
        }

    }
}

@Preview
@Composable
fun DefaultPreviewRegisterView() {
    RegisterView(viewModel = viewModel())
}