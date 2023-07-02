package com.example.mycoroutine

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)

    fun method() {
        //1) при вызове launch был создан новый объект Job() который является наследником у
        // созданного parentJob
        val childJob1 = coroutineScope.launch {
            delay(3000)
            Log.d(LOG_TAG, "first coroutine finished")
        }
        val childJob2 = coroutineScope.launch {
            delay(2000)
            childJob1.cancel()
            Log.d(LOG_TAG, "second coroutine finished")
        }
        thread {
            Thread.sleep(2000)
//            //4)Отменили родительскую Job
//            parentJob.cancel()
            //3 Пока дочерние Job-ы не завершат свою работу, род Job не будет закрыта
            Log.d(LOG_TAG, "Parent job is active:${parentJob.isActive}")
        }
        //2)являются ли childJob1-childJob2 наследниками parentJob
        Log.d(LOG_TAG, parentJob.children.contains(childJob1).toString())
        Log.d(LOG_TAG, parentJob.children.contains(childJob2).toString())
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

    companion object {
        private const val LOG_TAG = "MainViewModel"
    }
}