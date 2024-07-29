package com.ryanmhub.fitnessapp.android_client.features.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ryanmhub.fitnessapp.android_client.R
import com.ryanmhub.fitnessapp.android_client.app.NavRouter
import com.ryanmhub.fitnessapp.android_client.app.Screen
import com.ryanmhub.fitnessapp.android_client.common.components.*


@Composable
fun LoginView(){
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()
            .padding(30.dp)) {
            NormalTextComponent(value = stringResource(id = R.string.login))
            HeaderTextComponent(value = stringResource(id = R.string.welcome))
            Spacer(modifier = Modifier.height(20.dp))
            StandTextField(labelValue = stringResource(id = R.string.username), painterResource(id = R.drawable.crystal))
            PasswordTextField(labelValue = stringResource(id = R.string.password), painterResource(id = R.drawable.crystal)) //Todo: should I check some textfields by having user reenter the same value.
            Spacer(modifier = Modifier.height(40.dp))
            UnderLinedTextComponent(value = stringResource(id = R.string.forgt_password))
            Spacer(modifier = Modifier.height(40.dp))
            ButtonComponent(value = stringResource(id = R.string.login))
            Spacer(modifier = Modifier.height(20.dp))
            DividerTextComponent()
            Spacer(modifier = Modifier.height(40.dp))
            ClickableTextComponentEnding(initialText = stringResource(id = R.string.no_account), ending = stringResource(id = R.string.register), onTextSelected = {
                NavRouter.navigateTo(Screen.RegisterView)
            })
        }
    }
}

@Preview
@Composable
fun LoginViewPreview() {
    LoginView()
}