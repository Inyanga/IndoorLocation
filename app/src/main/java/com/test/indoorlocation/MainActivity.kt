package com.test.indoorlocation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.test.indoorlocation.foreground_service.ForegroundBleService
import io.reactivex.observers.DisposableObserver
import org.eclipse.paho.android.service.MqttAndroidClient






class MainActivity : AppCompatActivity() {

    private lateinit var xText: TextView
    private lateinit var yText: TextView
    //    private lateinit var  client:MqttAndroidClient
    private lateinit var client: MqttAndroidClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        askForLocationPermissions(this)
        startForegroundService(Intent(this, ForegroundBleService::class.java))
        client = MqttAndroidClient(
            applicationContext, "tcp://broker.hivemq.com:1883",
            "1313"
        )
        connectClient()
        xText = findViewById(R.id.x_coord)
        yText = findViewById(R.id.y_coord)

        RxPublisher.subscribeToPoint(object : DisposableObserver<DoubleArray>() {
            override fun onComplete() {

            }

            @SuppressLint("SetTextI18n")
            override fun onNext(point: DoubleArray) {
                xText.text = "X = " + point[0].toString()
                yText.text = "Y = " + point[1].toString()
            }

            override fun onError(e: Throwable) {

            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        client.close()
    }

    private fun connectClient() {
//        try {
//            val token = client.connect()
//            token.actionCallback = object : IMqttActionListener {
//                override fun onSuccess(asyncActionToken: IMqttToken) {
//                    // We are connected
//                   // Log.d(FragmentActivity.TAG, "onSuccess")
//                }
//
//                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
//                    // Something went wrong e.g. connection timeout or firewall problems
//                  //  Log.d(FragmentActivity.TAG, "onFailure")
//
//                }
//            }
//        } catch (e: MqttException) {
//            e.printStackTrace()
//        }


    }
}
