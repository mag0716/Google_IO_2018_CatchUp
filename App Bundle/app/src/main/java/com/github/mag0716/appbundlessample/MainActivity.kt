package com.github.mag0716.appbundlessample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.*

/**
 * Android Studio から通常通りにインストールすると、Dynamic Feature Module は全て含まれた状態で起動する
 *
 * Edit Configurations から Dynamic Feature Module を含まない状態で起動し動作確認することは可能
 * この状態でインストールをリクエストしても onStateUpdate は何も通知されない
 *
 * Internal Test Track で動作確認するしかない
 */
class MainActivity : AppCompatActivity(), SplitInstallStateUpdatedListener {

    companion object {
        const val TAG = "DynamicFeature"
    }

    private lateinit var invalidModuleButton: Button
    private lateinit var validModuleButton: Button

    private lateinit var manager: SplitInstallManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = SplitInstallManagerFactory.create(this)

        invalidModuleButton = findViewById(R.id.invalid_dynamic_feature_button)
        invalidModuleButton.setOnClickListener {
            loadModuleIfNeeded(getString(R.string.invalid_dynamic_feature_name))
        }
        validModuleButton = findViewById(R.id.valid_dynamic_feature_button)
        validModuleButton.setOnClickListener {
            loadModuleIfNeeded(getString(R.string.valid_dynamic_feature_name))
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
        // dynamic_feature が module に依存していると、status が 3(DOWNLOADED)になるが起動できない状態になる
        // module は app で依存している or module も dynamic_feature にする必要がある？
        logWithToast("onStateUpdate : $state")
    }

    private fun loadModuleIfNeeded(moduleName: String) {
        logWithToast("loadModuleIfNeeded : $moduleName, ${manager.installedModules}")
        if (manager.installedModules.contains(moduleName)) {
            launchFeatureModule(moduleName)
        } else {
            // モジュールを複数リクエストすることができる
            val request = SplitInstallRequest.newBuilder()
                    .addModule(moduleName)
                    .build()
            manager.startInstall(request)
        }
    }

    private fun launchFeatureModule(moduleName: String) {
        val className = when (moduleName) {
            getString(R.string.invalid_dynamic_feature_name) -> "com.github.mag0716.invalid_dynamic_feature.FeatureActivity"
            getString(R.string.valid_dynamic_feature_name) -> "com.github.mag0716.valid_dynamic_feature.FeatureActivity"
            else -> throw IllegalArgumentException("invalid parameter : $moduleName")
        }
        val intent = Intent(Intent.ACTION_VIEW).setClassName(packageName, className)
        startActivity(intent)
    }

    private fun MainActivity.logWithToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        Log.d(TAG, message)
    }
}
