package com.example.channel_demo_kotlin

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

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
        GlobalScope.launch {
            producer()
                .onStart {
                    emit(-1)
                    Log.d("jashwant","flow on start")
                }
                .onCompletion {
                    emit(100)
                    Log.d("jashwant","flow on completed")
                }
                .onEach {
                    Log.d("jashwant","About to emit-$it")
                }
                .collect{
                Log.d("jashwant ","first -  "+it.toString()
                )
            }
        }
        /*GlobalScope.launch {
            val data:Flow<Int> =producer()
            delay(2500)
            data.collect{
                Log.d("jashwant  second -",it.toString())
            }
        }*/



    }
    /*fun producer(){
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            channel.send(1);
            channel.send(2);
        }
    }*/
    fun producer() = kotlinx.coroutines.flow.flow<Int> {
        android.util.Log.d("jashwant", "producer is producing: ")
        val list= kotlin.collections.listOf(1,2,3,4,5,6,7)
        list.forEach {
            kotlinx.coroutines.delay(1000)
            emit(it)
        }
    }

    /*fun consumer(){
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            android.util.Log.d("jashwant", "consumer: "+channel.receive().toString())
            android.util.Log.d("jashwant", "consumer: "+channel.receive().toString())
            channel.receive();
            channel.receive();
        }
    }*/
}