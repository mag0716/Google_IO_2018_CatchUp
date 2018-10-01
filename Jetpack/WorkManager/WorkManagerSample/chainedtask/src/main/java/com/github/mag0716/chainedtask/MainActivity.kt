package com.github.mag0716.chainedtask

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.github.mag0716.common.LoggingWorker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "ChainedTask"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sequential_button.setOnClickListener { requestSequentialTask() }
        parallel_button.setOnClickListener { requestParallelTask() }
    }

    private fun requestSequentialTask() {
        val work1 = OneTimeWorkRequestBuilder<LoggingWorker>()
                .setInputData(LoggingWorker.createInputData("$TAG-1"))
                .build()
        val work2 = OneTimeWorkRequestBuilder<LoggingWorker>()
                .setInputData(LoggingWorker.createInputData("$TAG-2"))
                .build()

        // enqueue した時点で work2 は BLOCKED になる
        // その後、work1 が SUCCEEDED になったら、work2 が ENQUEUED -> RUNNING -> SUCCEEDED となる
        WorkManager.getInstance().getStatusById(work1.id)
                .observe(this, Observer { status ->
                    Log.d(TAG, "observe[work1] : status = $status")
                })
        WorkManager.getInstance().getStatusById(work2.id)
                .observe(this, Observer { status ->
                    Log.d(TAG, "observe[work2] : status = $status")
                })

        WorkManager.getInstance()
                .beginWith(work1)
                .then(work2)
                .enqueue()
    }

    private fun requestParallelTask() {
        val work1 = OneTimeWorkRequestBuilder<LoggingWorker>()
                .setInputData(LoggingWorker.createInputData("$TAG-1"))
                .build()
        val work2 = OneTimeWorkRequestBuilder<LoggingWorker>()
                .setInputData(LoggingWorker.createInputData("$TAG-2"))
                .build()

        WorkManager.getInstance().getStatusById(work1.id)
                .observe(this, Observer { status ->
                    Log.d(TAG, "observe[work1] : status = $status")
                })
        WorkManager.getInstance().getStatusById(work2.id)
                .observe(this, Observer { status ->
                    Log.d(TAG, "observe[work2] : status = $status")
                })

        WorkManager.getInstance()
                .beginWith(work1, work2)
                .enqueue()
    }
}
