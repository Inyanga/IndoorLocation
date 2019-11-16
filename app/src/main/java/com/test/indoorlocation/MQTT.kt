package com.test.indoorlocation

import android.content.Context
import io.reactivex.observers.DisposableObserver
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.eclipse.paho.client.mqttv3.MqttClient
import java.util.*

import org.eclipse.paho.android.service.MqttAndroidClient


//val mqttClient = MqttClient("tcp://broker.hivemq.com:1883", UUID.randomUUID().toString())
//var clientId = MqttClient.generateClientId()

fun subscribeToPoint() {
    RxPublisher.subscribeToPoint(
        object : DisposableObserver<DoubleArray>() {
            override fun onComplete() {

            }

            override fun onNext(point: DoubleArray) {

            }

            override fun onError(e: Throwable) {

            }
        }
    )
}