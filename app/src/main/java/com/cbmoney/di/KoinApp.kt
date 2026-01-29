package com.cbmoney.di

import android.app.Application
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class KoinApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KoinApp)
            modules(
                appModule,
                useCaseModule,
                dataSourceModule,
                roomModule,
                repositoryModule,
                viewModelModule
            )
            FirebaseApp.initializeApp(this@KoinApp)
        }
    }
}