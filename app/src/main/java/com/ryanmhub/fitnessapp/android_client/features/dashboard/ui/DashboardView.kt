package com.ryanmhub.fitnessapp.android_client.features.dashboard.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ryanmhub.fitnessapp.android_client.R
import com.ryanmhub.fitnessapp.android_client.common.components.*
import com.ryanmhub.fitnessapp.android_client.common.data.UserDTO
import com.ryanmhub.fitnessapp.android_client.common.data_store.EncryptedDataManager
import com.ryanmhub.fitnessapp.android_client.common.data_store.authDataStore
import com.ryanmhub.fitnessapp.android_client.common.nav.NavRouter
import com.ryanmhub.fitnessapp.android_client.common.nav.Screen
import com.ryanmhub.fitnessapp.android_client.common.state.BaseAPIState
import com.ryanmhub.fitnessapp.android_client.features.dashboard.di.DashboardViewModel
import com.ryanmhub.fitnessapp.android_client.features.login.ui.LoginView

@Composable
fun DashboardView(viewModel: DashboardViewModel) {
    var userData by remember { mutableStateOf<UserDTO?>(null) }
    val dataState by viewModel.dataState

    //State Machine Controller
    when(dataState){
        is BaseAPIState.Loading -> {
            CircularProgressIndicator()
        }
        is BaseAPIState.Success -> {
            val data = (dataState as BaseAPIState.Success).data
            userData = UserDTO(data?.firstName?:"Default", data?.lastName?:"Default", data?.username?:"Default", data?.email?:"Default", data?.password?:"Default")
            Log.d("DashboardView", "${userData!!.firstName} ${userData!!.lastName}")
        }
        is BaseAPIState.Failed -> {
        }
        is BaseAPIState.Error -> {
            val errorMessage = (dataState as BaseAPIState.Error).message
            val showDialog = remember { mutableStateOf(true)}
            PopUpComponent(stringResource(R.string.error), errorMessage.toString(), showDialog)
            Log.d("DashboardView Error", errorMessage!!)
        }
    }

   Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(30.dp)
        ) {

            //Todo: temporary display of user data, remove when done with it
            //call api to get user data
            if(userData==null){
                viewModel.getUserData()
            } else {
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)) {
                    items(listOf(userData!!)){ user ->
                        UserCard(user)
                    }
                }
            }
        }
    }


}

@Composable
fun UserCard(user: UserDTO) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "User Name: ${user.username}", style = MaterialTheme.typography.body1)
            Text(text = "First Name: ${user.firstName}", style = MaterialTheme.typography.body2)
            Text(text = "Last Name: ${user.lastName}", style = MaterialTheme.typography.body2)
            Text(text = "Email: ${user.email}", style = MaterialTheme.typography.body2)
            Text(text = "Password: ${user.password}", style = MaterialTheme.typography.body2)
        }
    }
}

@Preview
@Composable
fun DashboardViewPreview() {
    DashboardView(viewModel = viewModel())
}