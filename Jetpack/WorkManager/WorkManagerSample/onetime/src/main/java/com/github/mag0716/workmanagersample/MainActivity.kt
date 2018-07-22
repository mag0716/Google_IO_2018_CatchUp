package com.github.mag0716.workmanagersample

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.github.mag0716.common.LoggingWorker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "OneTimeWork"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { startOneTimeWork() }
    }

    private fun startOneTimeWork() {
        val work = OneTimeWorkRequestBuilder<LoggingWorker>()
                .setInputData(LoggingWorker.createInputData(TAG))
                .build()
        // ENQUEUED -> RUNNING -> SUCCEEDED の順だが、ENQUEUED が出力されない場合もある
        WorkManager.getInstance()!!.getStatusById(work.id)
                .observe(this, Observer { status ->
                    Log.d(TAG, "observe : status = $status")
                })
        WorkManager.getInstance()!!.enqueue(work)
    }
}
