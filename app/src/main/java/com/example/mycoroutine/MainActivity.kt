package com.example.mycoroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.mycoroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener {
            binding.progress.isVisible = true
            binding.buttonLoad.isEnabled = false
            //Создаем новую корутину
            val jobCity=lifecycleScope.launch {
                //1) Загружаем город и устанавливем его
                val city=loadCity()
                binding.tvLocation.text = city
            }
        //2) создаем 2 корутин и внтури делаем загрузку устанавливая полсе ее завершение значение
            val jobTemp=lifecycleScope.launch {
                val temp = loadTemperature()
                binding.tvTemperature.text=temp.toString()
            }
            //Создаем 3 корутину и у обоих экземпляров job выызваем join
            lifecycleScope.launch{
                //join - остановить кортину пока загрузка не будет выполнена и пойдет дальше
                jobCity.join()
                jobTemp.join()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
            }
        }
    }

    private suspend fun loadData() {
        Log.d("MainActivity", "Load started: $this")
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false

        val city = loadCity()
        binding.tvLocation.text = city


        val temp = loadTemperature()
        binding.tvTemperature.text = temp.toString()
        binding.progress.isVisible = false
        binding.buttonLoad.isEnabled = true
        Log.d("MainActivity", "Load finished: $this")
    }


    private suspend fun loadCity(): String {
        delay(2000)
        return "Moscow"
    }

    private suspend fun loadTemperature(): Int {
        delay(5000)
        return 17
    }


}