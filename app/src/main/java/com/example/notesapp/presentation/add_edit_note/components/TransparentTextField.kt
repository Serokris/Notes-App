package com.example.notesapp.presentation.add_edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    isHintVisible: Boolean = true,
    singleLine: Boolean = false,
    onTextChange: (String) -> Unit,
    onFocusChanged: (FocusState) -> Unit,
) {
    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = onTextChange,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    onFocusChanged(focusState)
                }
        )
        if (isHintVisible)
            Text(text = hint, style = textStyle, color = Color.DarkGray)
    }
}