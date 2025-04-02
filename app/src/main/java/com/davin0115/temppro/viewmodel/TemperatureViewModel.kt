package com.davin0115.temppro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf

class TemperatureViewModel : ViewModel() {
    val history = mutableStateListOf<String>()

    fun addToHistory(entry: String) {
        history.add(entry)
    }
}
