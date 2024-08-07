package com.ryanmhub.fitnessapp.android_client.features.dashboard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ryanmhub.fitnessapp.android_client.common.data.UserDTO

@Composable
fun DashboardView() {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
//        items(userData){ user ->
//            UserCard(user)
//        }
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