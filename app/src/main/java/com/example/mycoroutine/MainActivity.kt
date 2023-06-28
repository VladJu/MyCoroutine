package com.example.mycoroutine

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.mycoroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
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

            //1)Корутина будет запущена на выполнение, создастся объект Deferred<> и внутри
            // этого объекта как загрузка будет завершена будет лежать строка
            val deferredCity : Deferred<String> = lifecycleScope.async {
                val city = loadCity()
                binding.tvLocation.text = city
                //2)какое значение будет последним в этом блоке, то значение и верент Deferred
                city
            }

            val deferredTemp : Deferred<Int> = lifecycleScope.async {
                val temp = loadTemperature()
                binding.tvTemperature.text = temp.toString()
                temp
            }

            lifecycleScope.launch {
                //3)Получаем значение из объекта Deferred<>
                // await() - делает то же самое что и join останавливает коуртину и ждем завершения
                //загрузки, но он вернет объет который лежит в deferredCity / deferredTemp
                val city = deferredCity.await()
                val temp = deferredTemp.await()
                Toast.makeText(
                    this@MainActivity,
                    "City $city -- temperature:$temp",
                    Toast.LENGTH_SHORT
                ).show()
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