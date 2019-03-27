package com.github.mag0716.appbundlessample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import java.util.*

/**
 * Android Studio から通常通りにインストールすると、Dynamic Feature Module は全て含まれた状態で起動する
 *
 * Edit Configurations から Dynamic Feature Module を含まない状態で起動し動作確認することは可能
 * この状態でインストールをリクエストしても onStateUpdate は何も通知されない
 *
 * Internal Test Track で動作確認するしかない
 */
class MainActivity : AppCompatActivity(), SplitInstallStateUpdatedListener, AdapterView.OnItemSelectedListener {

    companion object {
        const val TAG = "DynamicFeature"
    }

    private lateinit var independentModuleButton: Button
    private lateinit var dependencyModuleButton: Button
    private lateinit var textView: TextView

    private lateinit var localeText: TextView
    private lateinit var spinner: Spinner

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
        spinner = findViewById(R.id.spinner)
        spinner.onItemSelectedListener = this
        localeText = findViewById(R.id.locale_text)

        if (savedInstanceState == null) {
            logWithText("installed languages")
            val installedLanguages = manager.installedLanguages
            logWithText("$installedLanguages")
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
        logWithText("onStateUpdate : $state")

        if (state == null) {
            return
        }

        val installedLanguage = state.languages().isEmpty().not()

        when (state.status()) {
            SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                // TODO:REQUIRES_USER_CONFIRMATIONのハンドリング
            }
            SplitInstallSessionStatus.INSTALLED -> {
                if (installedLanguage) {
                    // FIXME: デバッグのために固定している
                    updateText(Locale.CHINESE)
                }
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d(TAG, "onItemSelected : $position")
        val locales = listOf(Locale.JAPANESE, Locale.ENGLISH, Locale.CHINESE)
        val locale = locales[position]
        val installedLanguages = manager.installedLanguages
        if (installedLanguages.contains(locale.language)) {
            updateText(locale)
        } else {
            installLanguage(locale)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // noop
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

    private fun installLanguage(locale: Locale) {
        val request = SplitInstallRequest.newBuilder()
                .addLanguage(locale)
                .build()
        // Task だけど onSuccess などは呼び出されない？
        manager.startInstall(request)
                .addOnSuccessListener {
                    logWithText("success : $it")
                    updateText(locale)
                }
                .addOnFailureListener { logWithText("failure : $it") }
                .addOnCompleteListener {
                    logWithText("complete : $it")
                    updateText(locale)
                }

    }

    private fun updateText(locale: Locale) {
        logWithText("updateText : $locale")
        val configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        localeText.text = getString(R.string.test_text)
    }

    private fun logWithText(message: String) {
        val sb = StringBuilder(textView.text)
        sb.append("$message\n")
        textView.text = sb.toString()
        Log.d(TAG, message)
    }
}
