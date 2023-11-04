package com.example.medicinalplant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.medicinalplant.navigation.SetUpNavGraph
import com.example.medicinalplant.ui.theme.MedicinalPlantTheme
import com.example.medicinalplant.util.Screen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicinalPlantTheme {
                val navController = rememberNavController()
                SetUpNavGraph(
                    startDestination = Screen.SelectLanguageScreen.route,
                    navController = navController)
            }
        }
    }
}
