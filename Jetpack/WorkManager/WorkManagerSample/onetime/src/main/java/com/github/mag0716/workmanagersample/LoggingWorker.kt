package com.github.mag0716.workmanagersample

import android.util.Log
import androidx.work.Worker
import com.github.mag0716.workmanagersample.MainActivity.Companion.TAG

class LoggingWorker : Worker() {

    override fun doWork(): Result {
        Log.d(TAG, "doWork start...")
        Thread.sleep(3000)
        Log.d(TAG, "doWork finish!!")
        return Result.SUCCESS
    }
}