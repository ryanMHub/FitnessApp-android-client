package com.ryanmhub.fitnessapp.android_client.app

import android.app.Application
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.crypto.tink.aead.AeadConfig
import com.ryanmhub.fitnessapp.android_client.features.login.ui.LoginView
import com.ryanmhub.fitnessapp.android_client.features.register.ui.RegisterView

class FitnessApp : Application() {
    override fun onCreate(){
        super.onCreate()

    }
}