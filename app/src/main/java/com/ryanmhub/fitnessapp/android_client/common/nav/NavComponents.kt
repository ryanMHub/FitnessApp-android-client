package com.ryanmhub.fitnessapp.android_client.common.nav

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.DrawerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import com.ryanmhub.fitnessapp.android_client.app.MainScreen
import com.ryanmhub.fitnessapp.android_client.features.dashboard.ui.DashboardView
import com.ryanmhub.fitnessapp.android_client.features.help.ui.HelpView
import com.ryanmhub.fitnessapp.android_client.features.login.ui.LoginView
import com.ryanmhub.fitnessapp.android_client.features.register.ui.RegisterView
import com.ryanmhub.fitnessapp.android_client.features.settings.ui.SettingsView
import kotlinx.coroutines.launch

//Builds the box that will contain the contents of the Hamburger Button
@Composable
fun NavigationDrawerContent (
    navController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    val menuItems = listOf(
        Screen.SettingsView to "Settings",
        Screen.HelpView to "Help"
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)){
        Text("Menu", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(16.dp))
        menuItems.forEach { (screen, title) ->
            DrawerMenuItem(title, screen.route, navController, drawerState, scope)

        }
    }
}

//Will represent each clickable option in the menu
@Composable
fun DrawerMenuItem(
    title: String,
    route: String,
    navController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    val haptic = LocalHapticFeedback.current
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                navController.navigate(route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                scope.launch { drawerState.close() }
            }
            .padding(vertical = 8.dp),
        style = MaterialTheme.typography.body1
    )
}

//adds the title and hamburger button to the top of the app
@Composable
fun TopAppBarWithHamburger(drawerState: DrawerState, scope: CoroutineScope){
    TopAppBar(
        title = { Text("Fitness App") },
        navigationIcon = {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    )
}

//adds the bottom menu with all the provided options
@Composable
fun BottomNavigationBar(navController: NavHostController){
    val items = listOf(
        Screen.RegisterView,
        Screen.LoginView,
        Screen.DashboardView
    )

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            BottomNavigationItem(
                icon = { screen.icon?.let { Icon(it, contentDescription = null) } }, //Todo: get rid of this let
                label = { Text(screen.route)},
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

//Routes the selected screen to the Main Scaffold
@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onSettingsOpen: () -> Unit,
    onHelpOpen: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.DashboardView.route,
        modifier = modifier
    ) {
        composable(Screen.DashboardView.route) { DashboardView(viewModel = viewModel()) }
        composable(Screen.HelpView.route) {
            onHelpOpen()
            HelpView()
        }
        composable(Screen.SettingsView.route) {
            onSettingsOpen()
            SettingsView()
        }
    }
}

//Routes all screens related to the perimeter of the app, Login not necessary
@Composable
fun PerimeterNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.LoginView.route,
        modifier = modifier
    ) {
        composable(Screen.LoginView.route) {
            LoginView(onNavigateToMain = { navController.navigate("main"){popUpTo("login") {inclusive = true} } },
                onNavigateToRegister = { navController.navigate("register")})
        }
        composable(Screen.RegisterView.route){
            RegisterView(viewModel = viewModel(), onNavigateToLogin = {navController.navigate("login"){popUpTo("register") {inclusive = true} } })
        }
        composable(Screen.MainScreen.route) {
            MainScreen()
        }


//        navigation(startDestination = Screen.LoginView.route, route = "auth"){
//            composable(Screen.LoginView.route) {
//                LoginView(onNavigateToMain = { navController.navigate("main") { popUpTo("auth") { inclusive = true}}},
//                        onNavigateToRegister = { navController.navigate("register")})
//            }
//            composable(Screen.RegisterView.route){
//                RegisterView(viewModel = viewModel(), onNavigateToLogin = {navController.popBackStack()})
//            }
//        }
//
//        navigation(startDestination = Screen.DashboardView.route, route = "main"){
//            composable(Screen.DashboardView.route) { MainScreen()}
//        }
    }
}