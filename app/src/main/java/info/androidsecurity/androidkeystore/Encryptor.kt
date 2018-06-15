package info.androidsecurity.androidkeystore

import android.security.keystore.*
import javax.crypto.*


internal class Encryptor {

    lateinit var encryptionValue: ByteArray
    lateinit var ivValue: ByteArray

    fun encryptText(alias: String, textToEncrypt: String): ByteArray {

        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(alias))

        ivValue = cipher.iv
        encryptionValue = cipher.doFinal(textToEncrypt.toByteArray())
        return encryptionValue
    }

    private fun getSecretKey(alias: String): SecretKey {

        val keyGenerator = KeyGenerator
                .getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)

        keyGenerator.init(KeyGenParameterSpec.Builder(alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build())

        return keyGenerator.generateKey()
    }

    fun getEncryption(): ByteArray {
        return encryptionValue
    }


    // Initialisation Vector
    fun getIv(): ByteArray {
        return ivValue
    }

    companion object {

        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val ANDROID_KEY_STORE = "AndroidKeyStore"
    }
}