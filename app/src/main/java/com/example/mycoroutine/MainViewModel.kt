package com.example.mycoroutine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.math.BigInteger

class MainViewModel : ViewModel() {

    //1
    private val myCoroutineScope = CoroutineScope(
        Dispatchers.Main + CoroutineName(
            "My coroutine scope"
        )
    )

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state


    //вычисляет значение факториала
    fun calculate(value: String?) {
        //Каждый раз когда state меняется, создаем объект State и сохраняем в эту LiveData
        _state.value = Progress
        if (value.isNullOrBlank()) {
            _state.value = Error
            return
        }
        //2
        myCoroutineScope.launch(Dispatchers.Main) {
            val number = value.toLong()
            //3
            val result = withContext(Dispatchers.Default) {
                factorial(number)
            }
            _state.value = Factorial(result)
        }
    }


    //Если не suspend, переключаем поток вместе вызова
    private fun factorial(number: Long): String {
        var result = BigInteger.ONE
        for (i in 1..number) {
            result = result.multiply(BigInteger.valueOf(i))
        }
        return result.toString()

    }

    override fun onCleared() {
        super.onCleared()
        myCoroutineScope.cancel()
    }
}