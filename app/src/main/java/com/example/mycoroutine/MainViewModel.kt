package com.example.mycoroutine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger

class MainViewModel : ViewModel() {


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
        viewModelScope.launch {
            val number = value.toLong()
            //При вызове данной fun мы выйдем из метода пока факториал не будут вычислен
            //и Ui thread не будет заблокирован
            val result = factorial(number)
            _state.value = Factorial(result)
        }
    }

    //OPTION ONE - suspendCoroutine
//    //1
//    //Т.к факториал 1000 очень большое число и оно не пометиться в тип Long
//    //Поэтому используем класс BigInteger
//    private suspend fun  factorial(number: Long): String {
//    //2
//    //Если из метода с callback нам надо сделать suspend fun use method suspendCoroutine
//    //suspendCoroutine - из обычной fun с callback позволяет сделать suspend fun
//        return suspendCoroutine {
//            thread {
//                var result = BigInteger.ONE
//                for (i in 1..number) {
//                    result = result.multiply(BigInteger.valueOf(i))
//                }
//                //3 когда получили результат его надо отправить в объект Continuation<String>
//                //resumeWith если его не вызывать программа не будет выполняться и будет ожидать
//                завершение работы корутины
//                it.resumeWith(Result.success(result.toString()))
//            }
//        }
//    }

    //OPTION TWO - WithContext - позволяет переключать потоки
    //То значение которое хотим вернуть указыаем последним выражением
    private suspend fun factorial(number: Long): String {
        return withContext(Dispatchers.Default){
            var result = BigInteger.ONE
            for (i in 1..number) {
                result = result.multiply(BigInteger.valueOf(i))
            }
             result.toString()
        }
    }
}