package com.test.indoorlocation.ble

import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.util.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun startScan() {
    BluetoothAdapter.getDefaultAdapter()
        .bluetoothLeScanner
        .startScan(
            listOf(ScanFilter.Builder().build()),
            ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build(),
            scannerCallback()
        )
}

private fun scannerCallback() = object : ScanCallback() {
    override fun onScanFailed(errorCode: Int) {
        super.onScanFailed(errorCode)
        Log.i("DEBUG_SCANNER", "Scan failed: $errorCode")
    }

    override fun onScanResult(callbackType: Int, result: ScanResult?) {
        result?.let {
            Observable
                .just(result)
                .filter {
                    result.device.name.contains("PKI")
                }
                .subscribe(
                    { result ->
                        run {
                            Log.i("DEBUG_SCANNER", result.device.name)
                        }
                    },
                    { e ->
                        run {
                            Log.i("DEBUG_SCANNER", "Scan result error: ${e.localizedMessage}")
                        }
                    }
                )
        }
    }
}