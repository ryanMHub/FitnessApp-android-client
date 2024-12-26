package com.ryanmhub.fitnessapp.android_client.common.nav

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


//Todo: remove file when navigation works

object NavRouter {
    var currScreen : MutableState<Screen> = mutableStateOf(Screen.RegisterView)

    fun navigateTo(destination: Screen) {
        currScreen.value = destination
    }
}