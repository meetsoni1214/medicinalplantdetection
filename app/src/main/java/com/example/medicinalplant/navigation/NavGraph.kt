package com.example.medicinalplant.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringArrayResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.medicinalplant.R
import com.example.medicinalplant.data.DataSource.Langs
import com.example.medicinalplant.presentation.screens.detect.DetectScreen
import com.example.medicinalplant.presentation.screens.detect.DetectScreenViewModel
import com.example.medicinalplant.presentation.screens.entry.EntryScreen
import com.example.medicinalplant.util.Constants.LANGUAGE_INDEX
import com.example.medicinalplant.util.Screen
import com.example.medicinalplant.util.changeLanguage

@Composable
fun SetUpNavGraph(
    startDestination: String,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = startDestination) {
        selectLanguageScreenRoute(
            applyLanguage = { ind ->
                navController.popBackStack()
                navController.navigate(Screen.DetectScreen.passInd(ind = ind))
                changeLanguage(Langs[ind])
            }
        )
        detectScreenRoute()
    }

}

fun NavGraphBuilder.selectLanguageScreenRoute(
    applyLanguage: (Int) -> Unit
) {
    composable(route = Screen.SelectLanguageScreen.route) {
        EntryScreen(
            applyLanguage = applyLanguage
        )
    }
}

fun NavGraphBuilder.detectScreenRoute() {
    composable(
        route = Screen.DetectScreen.route,
        arguments = listOf(navArgument(name = LANGUAGE_INDEX){
            type = NavType.IntType
            nullable = false
            defaultValue = 0
        })
    ) {
        val viewModel: DetectScreenViewModel = viewModel()
        val index = viewModel.getIndex()
        val langs = stringArrayResource(id = R.array.langs)
        var selectedLanguage by remember { mutableIntStateOf(0) }
        DetectScreen(selectedLanguage = index,
            onApplyClick = { ind ->
                selectedLanguage = ind
                changeLanguage(langs[selectedLanguage])
            })
    }
}
