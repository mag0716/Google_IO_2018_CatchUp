package com.github.mag0716.common

import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.toWorkData

class LoggingWorker : Worker() {

    companion object {
        private val KEY = "Tag"
        fun createInputData(tag: String) = mapOf(KEY to tag).toWorkData()
    }

    override fun doWork(): Result {
        val tag = inputData.getString(KEY, "WorkManagerSample")
        Log.d(tag, "doWork start...")
        Thread.sleep(3000)
        Log.d(tag, "doWork finish!!")
        return Result.SUCCESS
    }
}