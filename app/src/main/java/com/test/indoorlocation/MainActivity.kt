package com.test.indoorlocation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.test.indoorlocation.foreground_service.ForegroundBleService
import io.reactivex.observers.DisposableObserver

class MainActivity : AppCompatActivity() {

    private lateinit var xText: TextView
    private lateinit var yText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        askForLocationPermissions(this)
        startForegroundService(Intent(this, ForegroundBleService::class.java))


        xText = findViewById(R.id.x_coord)
        yText = findViewById(R.id.y_coord)

        RxPublisher.subscribeToPoint(object : DisposableObserver<DoubleArray>() {
            override fun onComplete() {

            }

            override fun onNext(point: DoubleArray) {
                xText.text = point[0].toString()
                yText.text = point[1].toString()
            }

            override fun onError(e: Throwable) {

            }
        })
    }
}
