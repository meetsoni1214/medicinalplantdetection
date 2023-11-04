package com.example.medicinalplant.presentation.screens.info

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.medicinalplant.R
import com.example.medicinalplant.data.DataSource
import com.example.medicinalplant.ui.component.ChangeLanguageDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoTopBar(
    selectedLanguage: Int,
    onApplyClick:(Int) -> Unit,
    navigateUp: () -> Unit
) {
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showExpandedMenu by remember { mutableStateOf(false) }
    TopAppBar(
        actions = {
            IconButton(onClick = { showLanguageDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings Icon")
            }
        },
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back button")
            }
        },
        title = {
            Text(text = stringResource(id = R.string.usage))
        })
    if (showLanguageDialog) {
        ChangeLanguageDialog(
            title = stringResource(id = R.string.select_language),
            optionList = DataSource.Languages,
            selectedLang = selectedLanguage,
            onApplyClick = onApplyClick,
            onDismissRequest = { showLanguageDialog = false }
        )
    }
}