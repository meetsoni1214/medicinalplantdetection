package com.example.medicinalplant.util

import com.example.medicinalplant.util.Constants.LANGUAGE_INDEX

sealed class Screen(val route: String) {
    object SelectLanguageScreen: Screen("select_language_screen")
    object DetectScreen: Screen("detect_screen?$LANGUAGE_INDEX={$LANGUAGE_INDEX}") {
        fun passInd(ind: Int) = "detect_screen?$LANGUAGE_INDEX=$ind"
    }
}
