package com.ryanmhub.fitnessapp.android_client.features.dashboard.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ryanmhub.fitnessapp.android_client.R
import com.ryanmhub.fitnessapp.android_client.common.components.PopUpComponent
import com.ryanmhub.fitnessapp.android_client.common.data.UserDTO
import com.ryanmhub.fitnessapp.android_client.common.nav.NavRouter
import com.ryanmhub.fitnessapp.android_client.common.nav.Screen
import com.ryanmhub.fitnessapp.android_client.common.state.BaseAPIState
import com.ryanmhub.fitnessapp.android_client.features.dashboard.di.DashboardViewModel

@Composable
fun DashboardView(viewModel: DashboardViewModel) {

    var userData by remember { mutableStateOf<UserDTO?>(null) }
    //lateinit var userData: UserDTO Todo: possibly remove
    //Todo: I need to fix how this state machine is working so I'm not always creating the same code everytime
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
            //Todo: is there even a use case for this option
        }
        is BaseAPIState.Error -> {
            val errorMessage = (dataState as BaseAPIState.Error).message
            val showDialog = remember { mutableStateOf(true)}
            PopUpComponent(stringResource(R.string.error), errorMessage.toString(), showDialog)
            Log.d("DashboardView Error", errorMessage!!)
        }
    }

    //Todo: Based on the Logcat the state is going Error-with no message, Success, Success. Why is it cycling three times?
    //call api to get user data
    if(userData==null){
        viewModel.getUserData()
    } else {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            items(listOf(userData!!)){ user -> //Todo: Not sure how good it is to use the forced non-null without an exception catcher
                UserCard(user)
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
            Text(text = "First Name: ${user.username}", style = MaterialTheme.typography.body1)
            Text(text = "First Name: ${user.firstName}", style = MaterialTheme.typography.body2)
            Text(text = "First Name: ${user.lastName}", style = MaterialTheme.typography.body2)
            Text(text = "First Name: ${user.email}", style = MaterialTheme.typography.body2)
            Text(text = "First Name: ${user.password}", style = MaterialTheme.typography.body2)
        }
    }
}