package com.devotion.makancuy

import android.app.Application
import com.devotion.makancuy.data.source.local.database.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}
