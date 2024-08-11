package com.ryanmhub.fitnessapp.android_client.common.data_store

import android.util.Base64
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ryanmhub.fitnessapp.android_client.common.encryption.Hasher
import com.ryanmhub.fitnessapp.android_client.common.encryption.KeyStoreConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EncryptedDataManager(private val dataStore: DataStore<Preferences>) {

    suspend fun saveEncryptedData(key: String, data: String){
        Log.d("DataStore", "EncryptStartKey: $key")
        Log.d("DataStore", "PreEncrypted: $data")
        val hashedKey = Base64.encodeToString(Hasher.hashData(key), Base64.DEFAULT)
        val encryptedData = Base64.encodeToString(KeyStoreConfig.encryptData(data.toByteArray()), Base64.DEFAULT)
        Log.d("DataStore", encryptedData.toString())
        val dataStoreKey = stringPreferencesKey(hashedKey)
        Log.d("DataStore", dataStoreKey.toString())
        dataStore.edit { item ->
            item[dataStoreKey] = encryptedData
        }

    }

    //Todo: Focus on why the key is a different value between both functions after encrypting and encoding. Also why can't I get it to work without encrypting key.
    fun getEncryptedData(key: String) : Flow<String?> {
        Log.d("DataStore", "Starting Key: $key")
        val hashedKey = Base64.encodeToString(Hasher.hashData(key), Base64.DEFAULT)
        val dataStoreKey = stringPreferencesKey(hashedKey)
        Log.d("DataStore", dataStoreKey.toString())
        return dataStore.data.map { item ->
            item[dataStoreKey]?.let {
                val encryptedData = Base64.decode(it, Base64.DEFAULT)
                Log.d("DataStore", encryptedData.toString())
                val decryptedData = KeyStoreConfig.decryptData(encryptedData)
                Log.d("DataStore", "Decrypted: ${String(decryptedData)}")
                String(decryptedData)
            }
        }
    }
}