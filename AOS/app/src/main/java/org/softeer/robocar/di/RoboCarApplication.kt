package org.softeer.robocar.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RoboCarApplication : Application() {
    companion object{
        var token : String? = null
    }
}