package com.example.notesapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesapp.presentation.add_edit_note.AddEditNoteScreen
import com.example.notesapp.presentation.notes_list.NotesScreen
import com.example.notesapp.presentation.theme.NotesAppTheme
import com.example.notesapp.utils.Constants
import com.example.notesapp.utils.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route
                    ) {
                        composable(route = Screen.NotesScreen.route) {
                            NotesScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditNoteScreen.route +
                                    "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument("noteId") {
                                    type = NavType.IntType
                                    defaultValue = Constants.DEFAULT_NAVIGATION_ARGUMENT_VALUE
                                },
                                navArgument("noteColor") {
                                    type = NavType.IntType
                                    defaultValue = Constants.DEFAULT_NAVIGATION_ARGUMENT_VALUE
                                }
                            )
                        ) { navBackStackEntry ->
                            val color = navBackStackEntry.arguments?.getInt("noteColor")
                                ?: Constants.DEFAULT_NAVIGATION_ARGUMENT_VALUE
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}