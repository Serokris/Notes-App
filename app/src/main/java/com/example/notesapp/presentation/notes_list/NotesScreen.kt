package com.example.notesapp.presentation.notes_list

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notesapp.presentation.notes_list.components.NoteItem
import com.example.notesapp.presentation.notes_list.components.OrderSection
import com.example.notesapp.presentation.theme.Beige
import com.example.notesapp.utils.Screen
import com.example.notesapp.utils.noRippleClickable
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val screenState = viewModel.screenState.value
    val recentlyDeletedNoteColor = viewModel.recentlyDeletedNote?.color
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)
                },
                backgroundColor = Beige
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.Black)
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = { shackBarHostState ->
            SnackbarHost(shackBarHostState) { data ->
                if (recentlyDeletedNoteColor != null) {
                    val actionColor = Color(recentlyDeletedNoteColor)
                    Snackbar(snackbarData = data, actionColor = actionColor)
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your notes",
                    style = MaterialTheme.typography.h4
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(NotesEvent.ToggleOrderSection)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = null
                    )
                }
            }
            AnimatedVisibility(
                visible = screenState.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteOrder = screenState.noteOrder,
                    onOrderChange = { noteOrder ->
                        viewModel.onEvent(NotesEvent.Order(noteOrder))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(screenState.notesList) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .noRippleClickable {
                                navController.navigate(
                                    Screen.AddEditNoteScreen.route +
                                            "?noteId=${note.id}&noteColor=${note.color}"
                                )
                            },
                        onDeleteButtonClick = {
                            viewModel.onEvent(NotesEvent.DeleteNote(note))
                            coroutineScope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    "Note deleted",
                                    actionLabel = "Undo"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NotesEvent.RestoreNote)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}