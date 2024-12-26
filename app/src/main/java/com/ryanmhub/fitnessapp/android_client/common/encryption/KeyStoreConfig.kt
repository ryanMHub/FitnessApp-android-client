package com.ryanmhub.fitnessapp.android_client.common.encryption

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object KeyStoreConfig {
    private val keyAlias = "KeyAlias"
    private val keyStoreAddress = "AndroidKeyStore"
    private val cipherCode = "AES/GCM/NoPadding"
    private val ivLength = 12
    private val tagLength = 128
    private var secretKey: SecretKey? = null

    //Lazy initialize the keystore in this singleton
    private val keyStore: KeyStore by lazy {
        KeyStore.getInstance(keyStoreAddress).apply{
            load(null)
        }
    }
    //On the first access of this object the secretKey will either be generated or retrieved in the singleton value
    init {
        if(secretKey == null){
            if(!keyStore.containsAlias(keyAlias)){
                generateSecretKey()
            }
            secretKey = keyStore.getKey(keyAlias, null) as? SecretKey
        }
    }

    //Generate the secretKey if one is not currently in the KeyStore
    private fun generateSecretKey() {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, keyStoreAddress)
        keyGenerator.init(
            KeyGenParameterSpec.Builder(keyAlias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
        )
        keyGenerator.generateKey()
    }

    //Encrypt and return data
    fun encryptData(data: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(cipherCode)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        var encryptedData = cipher.doFinal(data)
        return iv + encryptedData
    }

    //Decrypt and return data
    fun decryptData(encryptedData: ByteArray) : ByteArray {
        val cipher = Cipher.getInstance(cipherCode)
        val iv = encryptedData.copyOfRange(0, ivLength)
        val data = encryptedData.copyOfRange(ivLength, encryptedData.size)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(tagLength, iv))
        return cipher.doFinal(data)
    }


}
