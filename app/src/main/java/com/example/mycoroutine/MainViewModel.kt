package com.example.mycoroutine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {


    private val _state =MutableLiveData<State>()
            val state : LiveData<State>
            get() = _state


    //вычисляет значение факториала
    fun calculate(value: String?) {
        //2
        //Каждый раз когда state меняется, создаем объект State и сохраняем в эту LiveData
        _state.value = Progress
        //если передали пустую строку или значение null
        if (value.isNullOrBlank()) {
            _state.value= Error
            return
        }
        viewModelScope.launch {
            val number = value.toLong()
            // calculate
            delay(1000)
            _state.value= Result(number.toString())
        }
    }
}