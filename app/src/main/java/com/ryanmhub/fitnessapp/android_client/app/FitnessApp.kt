package com.ryanmhub.fitnessapp.android_client.app

import android.app.Application
import com.ryanmhub.fitnessapp.android_client.common.data_store.authDataStore
import com.ryanmhub.fitnessapp.android_client.common.retrofit.RetrofitInstances
import com.ryanmhub.fitnessapp.android_client.common.retrofit.RetrofitSingleton

class FitnessApp : Application() {

    override fun onCreate(){
        super.onCreate()
        RetrofitSingleton.init(this)
    }
}