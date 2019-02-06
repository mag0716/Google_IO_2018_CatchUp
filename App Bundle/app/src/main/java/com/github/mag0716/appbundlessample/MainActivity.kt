package com.github.mag0716.appbundlessample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus

class MainActivity : AppCompatActivity(), SplitInstallStateUpdatedListener {

    companion object {
        const val TAG = "DynamicFeature"
    }

    private lateinit var button: Button

    private lateinit var manager: SplitInstallManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = SplitInstallManagerFactory.create(this)

        button = findViewById(R.id.button)
        button.setOnClickListener {
            loadModuleIfNeeded()
        }
    }

    override fun onResume() {
        super.onResume()
        manager.registerListener(this)
    }

    override fun onPause() {
        manager.unregisterListener(this)
        super.onPause()
    }

    override fun onStateUpdate(state: SplitInstallSessionState?) {
        Log.d(TAG, "onStateUpdate : $state")
        if (state?.status() == SplitInstallSessionStatus.INSTALLED) {
            launchFeatureModule()
        }
    }

    private fun loadModuleIfNeeded() {
        Log.d(TAG, "loadModuleIfNeeded : ${manager.installedModules}")
        val moduleName = "dynamic_feature"
        if (manager.installedModules.contains(moduleName)) {
            launchFeatureModule()
        } else {
            val request = SplitInstallRequest.newBuilder()
                    .addModule(moduleName)
                    .build()
            manager.startInstall(request)
        }
    }

    private fun launchFeatureModule() {
        val intent = Intent(Intent.ACTION_VIEW).setClassName(
                packageName,
                "com.github.mag0716.dynamic_feature.FeatureActivity"
        )
        startActivity(intent)
    }
}
