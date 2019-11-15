package com.test.indoorlocation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.indoorlocation.foreground_service.ForegroundBleService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        askForLocationPermissions(this)
        startForegroundService(Intent(this, ForegroundBleService::class.java))
    }
}
