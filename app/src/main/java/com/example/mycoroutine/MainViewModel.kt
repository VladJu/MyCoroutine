package com.example.mycoroutine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    //2
    private val _state =MutableLiveData<State>()
            val state : LiveData<State>
            get() = _state


    //вычисляет значение факториала
    fun calculate(value: String?) {
        //3
        //Каждый раз когда state меняется, создаем объект State и сохраняем в эту LiveData
        _state.value = State(isInProgress = true)
        //если передали пустую строку или значение null
        if (value.isNullOrBlank()) {
            _state.value= State(isError = true)
            return
        }
        viewModelScope.launch {
            val number = value.toLong()
            // calculate
            delay(1000)
            _state.value= State(factorial = number.toString())
        }
    }
}