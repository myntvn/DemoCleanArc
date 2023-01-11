package com.mynt.democleanarc.repo

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction

@Composable
fun AppBar(
    title: String,
    onSearch: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    val focusManager = LocalFocusManager.current

    var query by remember {
        mutableStateOf(title)
    }

    var isExpanded by remember { mutableStateOf(false) }

    val onDone = {
        query = title
        focusManager.clearFocus(true)
    }

    TopAppBar {
        Row {
            TextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { isExpanded = it.isFocused },

                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.White
                ),
                textStyle = TextStyle(color = Color.White),
                singleLine = true,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (isExpanded) {
                                if (query.isNotEmpty()) query = ""
                                else onDone()
                            } else {
                                focusRequester.requestFocus()
                            }
                        },
                    ) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Filled.Close else Icons.Filled.Search,
                            contentDescription = "search",
                            tint = Color.White
                        )
                    }
                },
                keyboardOptions = if (query.isNotBlank()) KeyboardOptions(imeAction = ImeAction.Search) else KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (query.isNotBlank()) {
                            onSearch(query)
                            focusManager.clearFocus(true)
                        }
                    },
                    onDone = { onDone() }
                )
            )
        }

    }
}
