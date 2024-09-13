package com.example.channel_demo_kotlin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
        producer()
        consumer()

    }
    fun producer(){
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            channel.send(1);
            channel.send(2);
        }
    }

    fun consumer(){
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            android.util.Log.d("jashwant", "consumer: "+channel.receive().toString())
            android.util.Log.d("jashwant", "consumer: "+channel.receive().toString())
            channel.receive();
            channel.receive();
        }
    }
}