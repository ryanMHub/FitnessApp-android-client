package com.ryanmhub.fitnessapp.android_client.common.data_store

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.authDataStore by preferencesDataStore(name = "auth")
//Todo: add settings here