package com.github.mag0716.common

import android.util.Log
import androidx.work.Worker
import androidx.work.toWorkData

class LoggingWorker : Worker() {

    companion object {
        private const val KEY = "Tag"
        fun createInputData(tag: String) = mapOf(KEY to tag).toWorkData()
    }

    override fun doWork(): Result {
        val tag = inputData.getString(KEY)
        Log.d(tag, "doWork start...")
        Thread.sleep(3000)

        if (isCancelled) {
            return Result.FAILURE
        }

        Log.d(tag, "doWork finish!!")
        return Result.SUCCESS
    }
}