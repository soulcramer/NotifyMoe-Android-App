package app.soulcramer.arn.cache

import android.content.Context
import android.content.SharedPreferences

/**
 * General Preferences Helper class, used for storing preference values using the Preference API
 */
class PreferencesHelper(context: Context) {

    companion object {
        private const val PREF_BUFFER_PACKAGE_NAME = "app.soulcramer.arn.preferences"

        private const val PREF_KEY_LAST_CACHE = "last_cache"
    }

    private val bufferPref: SharedPreferences

    init {
        bufferPref = context.getSharedPreferences(PREF_BUFFER_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Store and retrieve the last time data was cached
     */
    var lastCacheTime: Long
        get() = bufferPref.getLong(PREF_KEY_LAST_CACHE, 0)
        set(lastCache) = bufferPref.edit().putLong(PREF_KEY_LAST_CACHE, lastCache).apply()

}