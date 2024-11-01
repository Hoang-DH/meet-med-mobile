package com.example.doctorapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication @Inject constructor() : Application() {
    override fun onCreate() {
        super.onCreate()
        EventBus.getDefault().register(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        EventBus.getDefault().unregister(this)
    }
}