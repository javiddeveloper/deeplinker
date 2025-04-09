package com.javid.sattar.deeplinker

import android.content.Context
import androidx.core.content.edit
import javax.inject.Inject

class SharedPrefManager @Inject constructor(
    private val context: Context
) {
    private val sharedPref = context.getSharedPreferences("deep_link_prefs", Context.MODE_PRIVATE)

    fun saveField(key: String, value: String) {
        sharedPref.edit() { putString(key, value) }
    }

    fun getField(key: String): String {
        return sharedPref.getString(key, "") ?: ""
    }

    fun getList(key: String): List<String> {
        return sharedPref.getStringSet(key, emptySet())!!.toList()
    }

    fun appendToList(key: String, value: String) {
        val set = sharedPref.getStringSet(key, mutableSetOf())!!.toMutableSet()
        if (value.isNotBlank()) {
            set.add(value)
            sharedPref.edit() { putStringSet(key, set) }
        }
    }
}

