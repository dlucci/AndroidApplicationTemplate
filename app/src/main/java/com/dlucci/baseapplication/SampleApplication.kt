package com.dlucci.baseapplication

import android.app.Application
import com.dlucci.baseapplication.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SampleApplication)
            modules(appModule)
        }

    }

}
