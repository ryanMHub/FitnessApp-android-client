package com.ryanmhub.fitnessapp.android_client.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ryanmhub.fitnessapp.android_client.features.login.ui.LoginView
import com.ryanmhub.fitnessapp.android_client.features.register.ui.RegisterView

@Composable
fun FitnessApp() {
    Surface(modifier = Modifier.fillMaxSize(),
        color = Color.White) {

        Crossfade(targetState = NavRouter.currScreen, label = ""){ currScreen ->
            when(currScreen.value) {
                is Screen.RegisterView -> {
                    RegisterView(viewModel = viewModel())
                }
                is Screen.TermsAndConditions -> {
                    TermsAndConditions()
                }
                is Screen.LoginView -> {
                    LoginView(viewModel = viewModel())
                }
            }
        }
    }

}