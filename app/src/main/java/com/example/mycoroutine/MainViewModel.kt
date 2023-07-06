package com.example.mycoroutine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    fun method() {
        Log.d(LOG_TAG, "Started coroutine")
        val job = viewModelScope.launch(Dispatchers.Default) {
            val before = System.currentTimeMillis()
            var count = 0
            for (i in 0 until 100_000_000) {
                for (j in 0 until 100) {
                    ensureActive()
                    count++
                }
            }
            Log.d(LOG_TAG, "Finished: ${System.currentTimeMillis() - before}")
        }
        //слушаетль коуртины(Job-ы) на завершение работы, как она завершиться будет выплненен блок{}
        job.invokeOnCompletion {
            Log.d(LOG_TAG, "Coroutine was cancelled: $it")
        }
        viewModelScope.launch {
            delay(3000)
            job.cancel()
        }
    }

    companion object {
        private const val LOG_TAG = "MainViewModel"
    }
}