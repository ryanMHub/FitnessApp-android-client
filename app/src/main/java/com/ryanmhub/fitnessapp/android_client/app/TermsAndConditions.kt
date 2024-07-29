package com.ryanmhub.fitnessapp.android_client.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ryanmhub.fitnessapp.android_client.common.components.HeaderTextComponent
import com.ryanmhub.fitnessapp.android_client.common.nav.SystemBackButtonHandler

//Todo: more than likely remove or convert for some other resason
@Composable
fun TermsAndConditions() {
    Surface (modifier = Modifier.fillMaxSize()) {
        HeaderTextComponent("Terms and Conditions")
        SystemBackButtonHandler {
            NavRouter.navigateTo(Screen.RegisterView)
        }
    }
}