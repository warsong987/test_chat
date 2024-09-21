package ru.ivan.eremin.testchat

import android.app.Application
import ru.ivan.eremin.testchat.di.startKoinApp

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoinApp(this)
    }
}