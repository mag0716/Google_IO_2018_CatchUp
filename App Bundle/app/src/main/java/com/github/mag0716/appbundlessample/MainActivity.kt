package com.github.mag0716.appbundlessample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus

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
        logWithToast("onStateUpdate : $state")
        // 複数モジュールをリクエストして1つだけ失敗した場合も FAILED になる？
        if (state?.status() == SplitInstallSessionStatus.INSTALLED) {
            launchFeatureModule()
        }
    }

    private fun loadModuleIfNeeded() {
        logWithToast("loadModuleIfNeeded : ${manager.installedModules}")
        val moduleName = getString(R.string.dynamic_feature_name)
        if (manager.installedModules.contains(moduleName)) {
            launchFeatureModule()
        } else {
            // モジュールを複数リクエストすることができる
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

    private fun MainActivity.logWithToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        Log.d(TAG, message)
    }
}
