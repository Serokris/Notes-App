package com.example.notesapp.presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notesapp.models.NoteWithColors
import com.example.notesapp.presentation.add_edit_note.components.TransparentTextField
import com.example.notesapp.utils.Constants
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value
    val scaffoldState = rememberScaffoldState()
    val noteBackgroundAnimatable = remember {
        Animatable(
            initialValue = Color(
                if (noteColor != Constants.DEFAULT_NAVIGATION_ARGUMENT_VALUE) noteColor
                else viewModel.currentNoteSelectedColor.value
            )
        )
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.screenEventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteScreenEvents.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditNoteScreenEvents.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            val color = viewModel.currentNoteSelectedColor.value
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditNoteEvent.SaveNote)
                },
                backgroundColor = Color(
                    if (noteColor != Constants.DEFAULT_NAVIGATION_ARGUMENT_VALUE)
                        ColorUtils.blendARGB(color, Color.Black.toArgb(), 0.2f)
                    else
                        ColorUtils.blendARGB(color, Color.Black.toArgb(), 0.2f)
                )
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null)
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = { shackBarHostState ->
            SnackbarHost(shackBarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    contentColor = Color.White,
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NoteWithColors.noteColors.forEach { color ->
                    val colorArgb = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(8.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 2.dp,
                                color =
                                if (viewModel.currentNoteSelectedColor.value == colorArgb)
                                    Color.Black
                                else
                                    Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorArgb),
                                        animationSpec = tween(500)
                                    )
                                }
                                viewModel.onEvent(AddEditNoteEvent.ChangedColor(colorArgb))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentTextField(
                text = titleState.text,
                hint = titleState.hint,
                onTextChange = { title ->
                    viewModel.onEvent(AddEditNoteEvent.EnteredTitle(title))
                },
                onFocusChanged = { focusState ->
                    viewModel.onEvent(AddEditNoteEvent.ChangedTitleFocus(focusState))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentTextField(
                text = contentState.text,
                hint = contentState.hint,
                onTextChange = { content ->
                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(content))
                },
                onFocusChanged = { focusState ->
                    viewModel.onEvent(AddEditNoteEvent.ChangedContentFocus(focusState))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}