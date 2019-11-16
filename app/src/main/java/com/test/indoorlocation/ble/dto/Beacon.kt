package com.test.indoorlocation.ble.dto

data class Beacon(
    val id: Int,
    val calibratedRssi: Int,
    val x: Int,
    val y: Int,
    val rssi:Int
)