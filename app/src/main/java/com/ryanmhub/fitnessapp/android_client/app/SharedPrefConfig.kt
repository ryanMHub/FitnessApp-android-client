package com.ryanmhub.fitnessapp.android_client.app

import android.content.Context
import android.util.Log
import java.io.File

fun logSharedPrefsAccess(context: Context, fileName: String){
    try{
        checkSharedPreferencesExists(context, fileName)
        readSharedPrefs(context, fileName)
    } catch(e: Exception){
        Log.e("SharedPrefConfig", "Error accessing shared prefs: $fileName",e)
    }
}

fun checkSharedPreferencesExists(context: Context, fileName: String){
    val file = File(context.filesDir.parent?.plus("/shared_prefs/") + fileName + ".xml")
    if(file.exists()){
        Log.d("SharedPrefConfig", "Shared prefs file exists: $fileName")
    } else {
        Log.d("SharedPrefConfig", "Shared prefs file does not exists: $fileName")
    }
}

fun readSharedPrefs(context: Context, fileName: String){
    val sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    val allEntries = sharedPref.all
    for((key, value) in allEntries){
        Log.d("SharedPrefConfig", "key: $key, value: $value")
    }
}

