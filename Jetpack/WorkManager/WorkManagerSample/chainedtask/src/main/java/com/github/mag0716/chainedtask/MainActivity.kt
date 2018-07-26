package com.github.mag0716.chainedtask

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
    }

    private fun requestSequentialTask() {
        val work1 = OneTimeWorkRequestBuilder<LoggingWorker>()
                .setInputData(LoggingWorker.createInputData("$TAG-1"))
                .build()
        val work2 = OneTimeWorkRequestBuilder<LoggingWorker>()
                .setInputData(LoggingWorker.createInputData("$TAG-2"))
                .build()
        WorkManager.getInstance()
                .beginWith(work1)
                .then(work2)
                .enqueue()
    }
}
