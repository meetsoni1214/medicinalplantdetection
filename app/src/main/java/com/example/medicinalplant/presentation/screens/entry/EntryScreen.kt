package com.example.medicinalplant.presentation.screens.entry

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.medicinalplant.R
import com.example.medicinalplant.data.DataSource.Langs
import com.example.medicinalplant.util.changeLanguage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreen(
    applyLanguage:(Int) -> Unit
) {
    var padding by remember { mutableStateOf(PaddingValues()) }
    var languageDropdownOpened by remember { mutableStateOf(false) }
    var languageIndex by remember { mutableIntStateOf(0) }
    var selectedLanguage by remember { mutableStateOf("") }
    LaunchedEffect(key1 = Unit) {
        changeLanguage(Langs[0])
    }
    Scaffold() { values ->
        padding = values
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Column (
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.greetings),
                )
                Spacer(modifier = Modifier.height(12.dp))
                ExposedDropdownMenuBox(
                    expanded = languageDropdownOpened,
                    onExpandedChange = { languageDropdownOpened = !languageDropdownOpened}
                ) {
                    TextField(
                        value = selectedLanguage,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = languageDropdownOpened)
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.select))
                        },
                        modifier = Modifier.menuAnchor(),
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    )
                    ExposedDropdownMenu(
                        expanded = languageDropdownOpened,
                        onDismissRequest = { languageDropdownOpened = false }) {
                        DropdownMenuItem(
                            text = { Text(text = stringResource(id = R.string.English) ) },
                            onClick = {
                                selectedLanguage = "English"
                                languageIndex = 0
                                languageDropdownOpened = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = stringResource(id = R.string.Hindi) ) },
                            onClick = {
                                selectedLanguage = "हिंदी"
                                languageIndex = 1
                                languageDropdownOpened = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = stringResource(id = R.string.Gujarati) ) },
                            onClick = {
                                selectedLanguage = "ગુજરાતી"
                                languageIndex = 2
                                languageDropdownOpened = false
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Button(
                        onClick = { applyLanguage(languageIndex) },
                        shape = Shapes().small
                    ) {
                        Text(text = stringResource(id = R.string.submit))
                    }
                }
            }
        }
    }
}