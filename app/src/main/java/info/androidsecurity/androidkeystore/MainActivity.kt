package info.androidsecurity.androidkeystore

import android.os.*
import android.support.v7.app.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val sampleAlias = "MYALIAS"

    private lateinit var encryptor: Encryptor
    private lateinit var decryptor: Decryptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        encryptor = Encryptor()
        decryptor = Decryptor()

        btEncrypt.setOnClickListener {
            val encryptedText = encryptor.encryptText(sampleAlias, etEncrypt.text.toString())
            tvEncrypt.text = encryptedText.toString()
        }

        btDecrypt.setOnClickListener {
            tvDecrypt.text = decryptor
                    .decryptData(sampleAlias, encryptor.getEncryption(), encryptor.getIv())
        }

    }
}
