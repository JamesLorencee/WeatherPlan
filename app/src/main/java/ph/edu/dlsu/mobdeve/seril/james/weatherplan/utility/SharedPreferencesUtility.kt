package ph.edu.dlsu.mobdeve.seril.james.weatherplan.utility

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtility(context: Context) {
    private val PREFS = "appPreferences"
    private var appPreferences: SharedPreferences? = null

    init {
        appPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
    }

    fun setStringPrefs(key: String?, value: String?) {
        val prefsEditor = appPreferences!!.edit()
        prefsEditor.putString(key, value)
        prefsEditor.apply()
    }

    fun getStringPrefs(key: String?): String? {
        return appPreferences!!.getString(key, "")
    }
}