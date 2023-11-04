package com.example.medicinalplant.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.medicinalplant.R


@Composable
fun ChangeLanguageDialog(
    title: String,
    optionList: List<String>,
    selectedLang: Int,
    onApplyClick: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    var selectedOption by remember { mutableStateOf(selectedLang) }

    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Surface (
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(10.dp)
        ){
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ){
                Text(
                    text = title,
                    style = TextStyle(fontSize = MaterialTheme.typography.titleLarge.fontSize)

                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn() {
                    items(items = optionList, key = {it}) { lang ->
                        LanguageRadioButton(
                            text = lang,
                            selectedValue = optionList[selectedOption],
                            onClickItem = { language ->
                                selectedOption = optionList.indexOf(language)
                            })
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    shape = Shapes().small,
                    onClick = {
                        onApplyClick(selectedOption)
                        onDismissRequest()
                    }) {
                    Text(text = stringResource(id = R.string.apply))
                }
            }
        }
    }
}

@Composable
fun LanguageRadioButton(
    text: String,
    selectedValue: String,
    onClickItem: (String) -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = (text == selectedValue),
                onClick = { onClickItem(text) }
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        RadioButton(
            selected = (text == selectedValue),
            onClick = {onClickItem(text)})
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text,)
    }
}