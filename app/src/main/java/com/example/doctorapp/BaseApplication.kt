package com.example.doctorapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication @Inject constructor() : Application() {

}