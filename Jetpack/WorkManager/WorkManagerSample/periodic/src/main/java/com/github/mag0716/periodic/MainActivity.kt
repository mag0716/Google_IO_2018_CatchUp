package com.github.mag0716.periodic

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.github.mag0716.common.LoggingWorker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "PeriodicWork"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { startPeriodicWork() }
    }

    private fun startPeriodicWork() {
        val work = PeriodicWorkRequestBuilder<LoggingWorker>(1L, TimeUnit.MINUTES)
                .setInputData(LoggingWorker.createInputData(TAG))
                .build()
        WorkManager.getInstance().getStatusById(work.id)
                .observe(this, Observer { status ->
                    Log.d(TAG, "observe : status = $status")
                })
        WorkManager.getInstance().enqueue(work)
    }
}
