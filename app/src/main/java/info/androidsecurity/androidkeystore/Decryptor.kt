package info.androidsecurity.androidkeystore

import java.security.*
import javax.crypto.*
import javax.crypto.spec.*

internal class Decryptor {

    private var keyStore: KeyStore? = null

    init {
        initKeyStore()
    }

    private fun initKeyStore() {
        keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
        keyStore!!.load(null)
    }

    fun decryptData(alias: String, encryptedData: ByteArray, encryptionIv: ByteArray): String {

        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(128, encryptionIv)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(alias), spec)

        return String(cipher.doFinal(encryptedData))
    }

    private fun getSecretKey(alias: String): SecretKey {
        return (keyStore!!.getEntry(alias, null) as KeyStore.SecretKeyEntry).secretKey
    }

    companion object {
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val ANDROID_KEY_STORE = "AndroidKeyStore"
    }
}