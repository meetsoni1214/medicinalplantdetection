package com.example.medicinalplant.presentation.screens.detect

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.medicinalplant.util.Constants.LANGUAGE_INDEX

class DetectScreenViewModel(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    fun getIndex(): Int {
        val ind = savedStateHandle.get<Int>(key = LANGUAGE_INDEX)
        return ind ?: 0
    }
}