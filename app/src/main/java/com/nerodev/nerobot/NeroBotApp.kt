package com.nerodev.nerobot

import android.app.Application
import com.nerodev.nerobot.core.modules.chatModule
import com.nerodev.nerobot.core.modules.newsModule
import com.nerodev.nerobot.core.modules.topBarModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NeroBotApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
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