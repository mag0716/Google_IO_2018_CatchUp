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
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
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
        private const val REQUEST_IMMEDIATE_UPDATES = 100
        private const val REQUEST_FLEXIBLE_UPDATES = 200
    }

    private lateinit var immediateUpdatesButton: Button
    private lateinit var flexibleUpdatesButton: Button
    private lateinit var independentModuleButton: Button
    private lateinit var dependencyModuleButton: Button
    private lateinit var textView: TextView

    private lateinit var localeText: TextView
    private lateinit var spinner: Spinner

    private lateinit var appUpdateManager: AppUpdateManager
    private lateinit var manager: SplitInstallManager

    private val installStateUpdatedListener = InstallStateUpdatedListener { state ->
        logWithText("install State : $state")
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            popupSnackbarForCompleteUpdate()
        }
    }

    private var appUpdateInfo: AppUpdateInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appUpdateManager = AppUpdateManagerFactory.create(this)
        manager = SplitInstallManagerFactory.create(this)

        immediateUpdatesButton = findViewById(R.id.immediate_updates_button)
        immediateUpdatesButton.setOnClickListener {
            requestImmediateUpdatesIfNeeded()
        }
        flexibleUpdatesButton = findViewById(R.id.flexible_updates_button)
        flexibleUpdatesButton.setOnClickListener {
            requestFlexibleUpdatesIfNeeded()
        }
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
        appUpdateManager.registerListener(installStateUpdatedListener)
        appUpdateManager.appUpdateInfo
                .addOnSuccessListener { appUpdateInfo ->
                    logWithText("in-app updates success : ${appUpdateInfo.toStringForLog()}")
                    if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                        // Immediate は自動的に再起動が行われるのでここには到達しない
                        popupSnackbarForCompleteUpdate()
                    } else {
                        val updateAvailability = appUpdateInfo.updateAvailability()
                        if (updateAvailability == UpdateAvailability.UPDATE_AVAILABLE) {
                            this.appUpdateInfo = appUpdateInfo
                            if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                                immediateUpdatesButton.isEnabled = true
                            }
                            if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                                flexibleUpdatesButton.isEnabled = true
                            }
                        } else if (updateAvailability == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                            // アプリ更新中なので再開する
                            appUpdateManager.startUpdateFlowForResult(
                                    appUpdateInfo,
                                    AppUpdateType.IMMEDIATE,
                                    this,
                                    REQUEST_IMMEDIATE_UPDATES
                            )
                        }
                    }
                }
                .addOnFailureListener {
                    logWithText("in-app updates failed", it)
                }
                .addOnCompleteListener {
                    logWithText("in-app updates complete")
                }
        manager.registerListener(this)
    }

    override fun onPause() {
        manager.unregisterListener(this)
        appUpdateManager.unregisterListener(installStateUpdatedListener)
        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        logWithText("onActivityResult : $requestCode, $resultCode")
        super.onActivityResult(requestCode, resultCode, data)
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

    private fun requestImmediateUpdatesIfNeeded() {
        appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                AppUpdateType.IMMEDIATE,
                this,
                REQUEST_IMMEDIATE_UPDATES
        )
        // 擬似強制アップデート
        finish()
    }

    private fun requestFlexibleUpdatesIfNeeded() {
        appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                AppUpdateType.FLEXIBLE,
                this,
                REQUEST_FLEXIBLE_UPDATES
        )
    }

    private fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
                findViewById(R.id.container),
                "in-app updates",
                Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("更新") {
                appUpdateManager.completeUpdate()
            }
        }.show()
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

    private fun logWithText(message: String, exception: Exception? = null) {
        val sb = StringBuilder(textView.text)
        sb.append("$message\n")
        textView.text = sb.toString()
        if (exception == null) {
            Log.d(TAG, message)
        } else {
            Log.e(TAG, message, exception)
        }
    }
}

private fun AppUpdateInfo.toStringForLog(): String = """availableVersionCode=${availableVersionCode()}
        updateAvailability=${updateAvailability()}
        installStatus=${installStatus()}"""