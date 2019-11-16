package com.test.indoorlocation.ble

import android.util.Log
import com.test.indoorlocation.ble.dto.Beacon
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.pow

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer.Optimum
import com.lemmingapex.trilateration.TrilaterationFunction
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver
import com.test.indoorlocation.RxPublisher

import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer



object BeaconManager {


    private val executor = Executors.newScheduledThreadPool(1)
    private val beaconMap: HashMap<Int, Beacon> = HashMap()

    fun saveBeacon(beacon: Beacon) {
        beaconMap[beacon.id] = beacon
    }

    fun alreadyInMap(key: Int) = beaconMap.containsKey(key)

    fun calculateDistance() {
        executor.scheduleAtFixedRate({

            val distances = DoubleArray(3)
            val nearestBeacons = beaconMap.values
                .toList()
                .sortedWith(sortBeaconsByRssi())
                .take(3)


            if (nearestBeacons.size >= 3) {
                for(i in distances.indices){
                    distances[i] = calculateDistance(nearestBeacons[i])
                }

                val positions = arrayOf(
                    doubleArrayOf(nearestBeacons[0].x.toDouble(), nearestBeacons[0].y.toDouble()),
                    doubleArrayOf(nearestBeacons[1].x.toDouble(), nearestBeacons[1].y.toDouble()),
                    doubleArrayOf(nearestBeacons[2].x.toDouble(), nearestBeacons[2].y.toDouble())
                )
                val solver = NonLinearLeastSquaresSolver(
                    TrilaterationFunction(positions, distances),
                    LevenbergMarquardtOptimizer()
                )
                val optimum = solver.solve()
                val point = optimum.point.toArray()
                RxPublisher.publishPoint(point)
                Log.i("DEBUG_BEACON_POINT", "${point[0]} ${point[1]}")
            }


        }
            , 1000, 2000, TimeUnit.MILLISECONDS)
    }

    fun sortBeaconsByRssi() = Comparator<Beacon> { b1, b2 ->
        when {
            b1.rssi > b2.rssi -> -1
            b1.rssi < b2.rssi -> 1
            else -> 0
        }
    }

    fun calculateDistance(b: Beacon) =
        10.toDouble().pow((b.calibratedRssi.toDouble() - b.rssi) / 10 * pathLoss)


}