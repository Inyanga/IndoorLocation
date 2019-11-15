package com.test.indoorlocation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

fun askForLocationPermissions(activity: Activity) {
    val dialogMultiplePermissionsListener: MultiplePermissionsListener =
        DialogOnAnyDeniedMultiplePermissionsListener.Builder
            .withContext(activity)
            .withTitle(activity.getString(R.string.permission_title))
            .withMessage(activity.getString(R.string.permission_message))
            .withButtonText(android.R.string.ok)
            .build()
    Dexter.withActivity(activity)
        .withPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).withListener(
            dialogMultiplePermissionsListener
        ).check()
}

fun checkPermiision(activity: Activity) =
    ContextCompat.checkSelfPermission(
        activity,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
            &&
            ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED