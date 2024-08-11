package com.ryanmhub.fitnessapp.android_client.common.nav

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

//Todo: research navigation and overhaul
sealed class Screen{
    object RegisterView : Screen()
    object TermsAndConditions : Screen()
    object LoginView : Screen()
    object DashboardView : Screen()
}

object NavRouter {
    var currScreen : MutableState<Screen> = mutableStateOf(Screen.RegisterView)

    fun navigateTo(destination: Screen) {
        currScreen.value = destination
    }
}