package com.example.channel_demo_kotlin

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.time.measureTime

class MainActivity : AppCompatActivity() {

    val channel = kotlinx.coroutines.channels.Channel<Int> {  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //producer()
        //consumer()
        GlobalScope.launch(Dispatchers.Main) {
            producer()
                .collect {
                    Log.d("jashwant", "Collector Thread - ${Thread.currentThread().name}")
                }
        }

    }
    private fun producer(): Flow<Int> {
        return flow <Int> {
            val list = listOf(1, 2, 3, 4, 5)
            list.forEach {
                delay(timeMillis = 1000)
                Log.d( "jashwant","Emitter Thread - ${Thread.currentThread().name}")
                emit(it)
            }
        }
    }
}