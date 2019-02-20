package com.github.mag0716.appbundlessample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
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

    private lateinit var independentModuleButton: Button
    private lateinit var dependencyModuleButton: Button
    private lateinit var textView: TextView

    private lateinit var manager: SplitInstallManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = SplitInstallManagerFactory.create(this)

        independentModuleButton = findViewById(R.id.independent_dynamic_feature_button)
        independentModuleButton.setOnClickListener {
            loadModuleIfNeeded(getString(R.string.independent_dynamic_feature_name))
        }
        dependencyModuleButton = findViewById(R.id.dependency_dynamic_feature_button)
        dependencyModuleButton.setOnClickListener {
            loadModuleIfNeeded(getString(R.string.dependency_dynamic_feature_name))
        }
        textView = findViewById(R.id.text)
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
        logWithText("onStateUpdate : $state")
    }

    private fun loadModuleIfNeeded(moduleName: String) {
        logWithText("loadModuleIfNeeded : $moduleName, ${manager.installedModules}")
        if (manager.installedModules.contains(moduleName)) {
            launchFeatureModule(moduleName)
        } else {
            // モジュールを複数リクエストすることができる
            val request = SplitInstallRequest.newBuilder()
                    .addModule(moduleName)
                    .build()
            manager.startInstall(request)
                    .addOnSuccessListener { logWithText("success : $it") }
                    .addOnFailureListener { logWithText("failure : $it") }
                    .addOnCompleteListener { logWithText("complete : $it") }
        }
    }

    private fun launchFeatureModule(moduleName: String) {
        val className = when (moduleName) {
            getString(R.string.independent_dynamic_feature_name) -> "com.github.mag0716.independent_dynamic_feature.IndependentFeatureActivity"
            getString(R.string.dependency_dynamic_feature_name) -> "com.github.mag0716.dependency_dynamic_feature.DependencyFeatureActivity"
            else -> throw IllegalArgumentException("invalid parameter : $moduleName")
        }
        val intent = Intent(Intent.ACTION_VIEW).setClassName(packageName, className)
        startActivity(intent)
    }

    private fun logWithText(message: String) {
        val sb = StringBuilder(textView.text)
        sb.append("$message\n")
        textView.text = sb.toString()
        Log.d(TAG, message)
    }
}
