package com.example.notesapp

import android.app.Application
import com.example.notesapp.utils.ModelPreferencesManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoteApp : Application() {

    override fun onCreate() {
        super.onCreate()

        ModelPreferencesManager.with(this)
    }
}