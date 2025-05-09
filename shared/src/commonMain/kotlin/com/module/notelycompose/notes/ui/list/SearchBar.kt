package com.module.notelycompose.notes.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import com.module.notelycompose.notes.ui.theme.LocalCustomColors
import notelycompose.shared.generated.resources.Res
import notelycompose.shared.generated.resources.search_bar_search_description
import notelycompose.shared.generated.resources.search_bar_search_text
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchBar(
    onSearchByKeyword: (String) -> Unit
) {
    var value by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    var isLabelVisible by remember { mutableStateOf(true) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                value = it
                isLabelVisible = !isFocused && value.isEmpty()
                onSearchByKeyword(it)
            },
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                    isLabelVisible = !isFocused && value.isEmpty()
                },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = LocalCustomColors.current.searchOutlinedTextFieldColor,
                textColor = LocalCustomColors.current.searchOutlinedTextFieldColor,
                focusedBorderColor = LocalCustomColors.current.searchOutlinedTextFieldColor,
                unfocusedBorderColor = LocalCustomColors.current.searchOutlinedTextFieldColor,
                disabledBorderColor = LocalCustomColors.current.searchOutlinedTextFieldColor
            ),
            label = {
                if (isLabelVisible) {
                    Text(
                        text = stringResource(Res.string.search_bar_search_text),
                        color = LocalCustomColors.current.searchOutlinedTextFieldColor
                    )
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(Res.string.search_bar_search_description),
                    tint = LocalCustomColors.current.searchOutlinedTextFieldColor,
                    modifier = Modifier.size(38.dp).padding(start = 8.dp)
                )
            },
            shape = RoundedCornerShape(48.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    isLabelVisible = value.isEmpty()
                    focusManager.clearFocus()
                }
            ),
            singleLine = true
        )
    }
}