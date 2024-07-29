package com.ryanmhub.fitnessapp.android_client.app

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen{
    object RegisterView : Screen()
    object TermsAndConditions : Screen()
    object LoginView : Screen()
}

object NavRouter {
    var currScreen : MutableState<Screen> = mutableStateOf(Screen.RegisterView)

    fun navigateTo(destination: Screen) {
        currScreen.value = destination
    }
}