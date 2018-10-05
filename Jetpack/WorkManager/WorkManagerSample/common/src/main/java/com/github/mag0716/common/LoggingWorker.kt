package com.github.mag0716.common

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class LoggingWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    companion object {
        private const val KEY = "Tag"
        fun createInputData(tag: String) = workDataOf(KEY to tag)
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