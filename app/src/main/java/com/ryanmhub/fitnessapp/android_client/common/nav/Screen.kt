package com.ryanmhub.fitnessapp.android_client.common.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

//Todo: is the explicit of imported needed as a parameter
sealed class Screen(val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector? = null) {
    object RegisterView : Screen("register", Icons.Filled.Create)
    object TermsAndConditions : Screen("terms_and_conditions", Icons.Filled.Contacts)
    object LoginView : Screen("login", Icons.Filled.Login)
    object MainScreen : Screen("main", Icons.Filled.Home)
    object DashboardView : Screen("dashboard", Icons.Filled.Dashboard)
    object SettingsView : Screen("settings", Icons.Filled.Settings)
    object HelpView : Screen("help", Icons.Filled.Help)
}