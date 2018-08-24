package com.github.mag0716.optimizer

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.FrameMetrics
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        var TAG = MainActivity::class.java.simpleName
    }

    private val frameMetricsHandler = Handler()

    private val frameMetricsAvailableListener = Window.OnFrameMetricsAvailableListener { _, frameMetrics, _ ->
        val frameMetricsCopy = FrameMetrics(frameMetrics)
        // Layout measure duration in Nano seconds
        val layoutMeasureDurationNs = frameMetricsCopy.getMetric(FrameMetrics.LAYOUT_MEASURE_DURATION)

        Log.d(TAG, "layoutMeasureDurationNs: $layoutMeasureDurationNs")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addOnFrameMetricsAvailableListener(frameMetricsAvailableListener, frameMetricsHandler)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()
        window.removeOnFrameMetricsAvailableListener(frameMetricsAvailableListener)
    }
}
