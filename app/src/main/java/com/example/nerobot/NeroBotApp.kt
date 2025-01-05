package com.example.nerobot

import android.app.Application
import com.example.nerobot.core.modules.chatModule
import com.example.nerobot.core.modules.newsModule
import com.example.nerobot.core.modules.topBarModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NeroBotApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)

            androidContext(this@NeroBotApp)
            modules(
                listOf(
                    topBarModule,
                    chatModule,
                    newsModule
                )
            )
        }
    }

}