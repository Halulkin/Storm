package com.halulkin.storm

import android.app.Application
import com.halulkin.storm.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    viewModelModule,
                    locationRepoModule,
                    apiModule,
                    networkModule,
                    weatherRepoModule
                )
            )
        }
    }
}
