package com.test.indoorlocation.ble

import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.os.ParcelUuid
import android.util.Log
import com.test.indoorlocation.ble.dto.Beacon
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.ArrayList

const val pathLoss = 2
const val eddyUuid = "0000FEAA-0000-1000-8000-00805F9B34FB"
val serviceUuid = ParcelUuid.fromString(eddyUuid)

private val averageRssi = ConcurrentHashMap<String, ArrayList<Int>>()
private const val rssiThreshold = 10

fun startScan() {
    val scanFilter = ScanFilter.Builder().setServiceUuid(serviceUuid).build()
    BluetoothAdapter.getDefaultAdapter()
        .bluetoothLeScanner
        .startScan(
            listOf(scanFilter),
            ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_BALANCED).build(),
            scannerCallback
        )
}

fun stopScan() {
    BluetoothAdapter.getDefaultAdapter()
        .bluetoothLeScanner
        .stopScan(scannerCallback)
}

private val scannerCallback = object : ScanCallback() {
    override fun onScanFailed(errorCode: Int) {
        super.onScanFailed(errorCode)
        Log.i("DEBUG_SCANNER", "Scan failed: $errorCode")
    }

    override fun onScanResult(callbackType: Int, result: ScanResult?) {
        result?.let {
//            val mac = result.device.address
//            when {
//                averageRssi[mac] == null || averageRssi[mac]?.size == 0 -> {
//                    val rssiArray: ArrayList<Int> = ArrayList()
//                    rssiArray.add(result.rssi)
//                    averageRssi[mac] = rssiArray
//                }
//                averageRssi[mac]!!.size < rssiThreshold -> averageRssi[mac]!!.add(result.rssi)
//                averageRssi[mac]!!.size >= rssiThreshold -> {
//                    val avr = averageRssi[mac]!!
//                        .sortedWith(sortRssiList())
//                        .reduce { acc, it ->
//                            acc + it
//                        }
//                }
//                else -> {
//
//                }
//            }
            parseRecord(result)
        }
    }
}

fun sortRssiList() = Comparator<Int> { r1, r2 ->
    when {
        r1 > r2 -> -1
        r1 < r2 -> 1
        else -> 0
    }
}

private fun parseRecord(result: ScanResult) {
    val map = result.scanRecord?.serviceData
    val bytes: ByteArray? = map?.get(serviceUuid)
    bytes?.let {
        val tx = bytes[1] - 41
        val beacon = Beacon(
            ByteParser.shortToInt(bytes[12], bytes[13]),
            tx,
            ByteParser.shortToInt(bytes[14], bytes[15]),
            ByteParser.shortToInt(bytes[16], bytes[17]),
            result.rssi
        )
        BeaconManager.saveBeacon(beacon)
        Log.i("DEBUG_SCANNER", "$beacon")
    }


}