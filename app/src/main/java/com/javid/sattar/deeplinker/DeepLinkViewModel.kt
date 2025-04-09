package com.javid.sattar.deeplinker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeepLinkViewModel @Inject constructor(
    private val pref: SharedPrefManager
) : ViewModel() {

    var scheme by mutableStateOf(pref.getField("scheme"))
        private set
    var host by mutableStateOf(pref.getField("host"))
        private set
    var path by mutableStateOf(pref.getField("path"))
        private set


    var schemeHistory by mutableStateOf(pref.getList("scheme_history"))
    var hostHistory by mutableStateOf(pref.getList("host_history"))
    var pathHistory by mutableStateOf(pref.getList("path_history"))

    fun onSchemeChanged(new: String) {
        scheme = new
    }

    fun onHostChanged(new: String) {
        host = new
    }

    fun onPathChanged(new: String) {
        path = new
    }

    fun onSave() {
        pref.saveField("scheme", scheme)
        pref.saveField("host", host)
        pref.saveField("path", path)

        pref.appendToList("scheme_history", scheme)
        pref.appendToList("host_history", host)
        pref.appendToList("path_history", path)

        schemeHistory = pref.getList("scheme_history")
        hostHistory = pref.getList("host_history")
        pathHistory = pref.getList("path_history")
    }

    fun getFullUri(): String = "$scheme://$host$path"
}