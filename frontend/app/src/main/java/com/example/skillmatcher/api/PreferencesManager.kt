// file to enable EncryptedSharedPreferences in order to safely store JWT
package com.example.skillmatcher.api

import androidx.security.crypto.EncryptedSharedPreferences
import android.content.Context
import androidx.security.crypto.MasterKey







class PreferencesManager(context: Context) {
    val masterKey = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
    private val masterKeyAlias = MasterKey.getKeyAlias(context)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "encrypted_prefs",
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    // takes a JWT as an argument and writes it to the shared preferences file
    fun saveJWT(jwt: String) {
        val editor = sharedPreferences.edit()
        editor.putString("jwt", jwt)
        editor.apply()
    }

    // retrieves the JWT from the shared preferences file
    fun getJWT(): String? {
        return sharedPreferences.getString("jwt", null)
    }
}
