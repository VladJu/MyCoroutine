package com.example.mycoroutine


sealed class State

//1
//чтобы каждый раз не создавать новый object, будем использовать 1 и тот же объект
object Error : State()
object Progress : State()
//Не сможем сделать объектом т.к у него есть поле и надо будет каждый раз создавать новый экземлр
class Result(
    val factorial: String
) : State()