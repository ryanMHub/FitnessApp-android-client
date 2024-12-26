package com.ryanmhub.fitnessapp.android_client.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ryanmhub.fitnessapp.android_client.common.nav.*
import com.ryanmhub.fitnessapp.android_client.common.nav.Screen.DashboardView.route


//Primary screen which interchanges the desired screen using the navigation of a hamburger button and a bottom bar
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var bottomBarVisible by remember { mutableStateOf(true) }
    bottomBarVisible = shouldShowBottomBar(currentRoute)

    //Todo: Never used
    val bottomBarHeight by animateDpAsState(targetValue = if (bottomBarVisible) 56.dp else 0.dp, label = "")

    Scaffold(
        drawerContent = {
            NavigationDrawerContent(navController, drawerState, scope)
        },
        topBar = {
            TopAppBarWithHamburger(drawerState, scope)
        },
        bottomBar = {
            AnimatedVisibility(
                visible = bottomBarVisible,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                BottomNavigationBar(navController)
            }
        }) { innerPadding ->
        AppNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            onSettingsOpen = { bottomBarVisible = false },
            onHelpOpen = { bottomBarVisible = false }
        )
    }
}

//Todo: Finish implementation
fun shouldShowBottomBar(currentRoute: String?): Boolean {
    return when (route){
        Screen.SettingsView.route -> false
        else -> true
    };
}
