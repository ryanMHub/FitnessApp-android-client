package com.ryanmhub.fitnessapp.android_client

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ryanmhub.fitnessapp.android_client.app.*
import com.ryanmhub.fitnessapp.android_client.common.data_store.authDataStore
import com.ryanmhub.fitnessapp.android_client.common.nav.NavRouter
import com.ryanmhub.fitnessapp.android_client.common.nav.Screen
import com.ryanmhub.fitnessapp.android_client.common.retrofit.RetrofitInstances
import com.ryanmhub.fitnessapp.android_client.features.dashboard.ui.DashboardView
import com.ryanmhub.fitnessapp.android_client.features.login.ui.LoginView
import com.ryanmhub.fitnessapp.android_client.features.register.ui.RegisterView
import com.ryanmhub.fitnessapp.android_client.ui.theme.AndroidclientTheme


//Todo: Should I try to move all important objects that can be used as a singleton to the FitnessApp::Application class, Maybe Retrofit, etc
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//    Todo:    RetrofitInstances.setEncryptedDataManager(applicationContext.authDataStore)
        setContent {
            AndroidclientTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Log.d("MainActivity","Hello World!")

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
                            is Screen.DashboardView -> {
                                DashboardView(viewModel = viewModel())
                            }
                        }
                    }
                }
            }
        }

        logSharedPrefsAccess(applicationContext, "master_key_preference")
    }
}
