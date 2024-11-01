package com.example.doctorapp.presentation.utils

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

fun AppCompatEditText.validate(validator: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            validator(s.toString())
        }
    })
}

fun <T> EventBus.asFlow(): Flow<T> = callbackFlow {
    val eventBusSubscriber = object : Any() {
        @Subscribe
        fun onEvent(event: Any) {
            trySend(event as T)
        }
    }
    EventBus.getDefault().register(eventBusSubscriber)
    awaitClose {
        EventBus.getDefault().unregister(eventBusSubscriber)
    }
}