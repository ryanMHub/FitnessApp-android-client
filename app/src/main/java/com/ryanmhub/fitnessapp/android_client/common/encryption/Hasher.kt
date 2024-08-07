package com.ryanmhub.fitnessapp.android_client.common.encryption

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

object Hasher {

    //Turn given string value into hash
    fun hashData(value: String): ByteArray {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(value.toByteArray(StandardCharsets.UTF_8))
        return hash;
    }
}