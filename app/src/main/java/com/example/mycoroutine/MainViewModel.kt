package com.example.mycoroutine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = _error

    //такие числа могут храниться только в виде строки или класса BigInteger
    private val _factorial = MutableLiveData<String>()
    val factorial: LiveData<String>
        get() = _factorial

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean>
        get() = _progress

    //вычисляет значение факториала
    fun calculate(value: String?) {
        _progress.value = true
        //если передали пустую строку или значение null
        if (value.isNullOrBlank()) {
            _progress.value=false
            _error.value = true
            return
        }
        viewModelScope.launch {
            val number = value.toLong()
            // calculate
            delay(1000)
            _progress.value = false
            _factorial.value=number.toString()
        }
    }
}