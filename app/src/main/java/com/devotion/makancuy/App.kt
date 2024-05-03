package com.devotion.makancuy

import android.app.Application
import com.devotion.makancuy.data.source.local.database.AppDatabase
import com.devotion.makancuy.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.createInstance(this)
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(AppModules.modules)
        }
    }
}
