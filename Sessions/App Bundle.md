# Actions

## [Build the new, modular Android App Bundle (Google I/O '18)
](https://www.youtube.com/watch?v=bViNOUeFuiQ)

* Publishing format
  * Not installable
  * Metadata files
  * Strict validation
* File targeting
  * インストール対象のデバイスに必要なファイルのみをインストールする
    * 解像度
    * 言語
    * アーキテクチャ
* Assets targeting
* Split APKs
  * Lollipop+
    * Lollipop より前は全てをインストールする
    * Lollipop 以降は必要なファイルのみをインストールする
  * Config Splits for Graphics APIs はもうすぐ
* 対応すればユーザのダウンロードサイズも減る
* Language、Architecture の部分が大きく減る傾向にあるらしい
* Building the Android App Bundle
  * Build Bundle(s)
  * `./gradlew modulename:bundle`
  * outputs/bundleVariant/bundle.aab
* Google Play
  * Google Play が自動的に split してくれる
* bundle -> enableSplit = false で分割しない様にもできる
* Bundle Explorer
  * どれだけサイズが減るかを出してくれる
  * どういうデバイスでインストールされているかも見れる
* How to test
  * Android Studio からは直接インストールできる
  * Internal test track
  * Bundletool
    * Play Console にアクセス権限がない場合
    * aab から apk を生成する
      * build-apks
    * --connected-device オプションでデバイスに適した apk を生成してくれる
    * get-device-spec
    * install-apks でインストールする
    * 不特定多数の端末に入れる場合
      * --universal
      * 全てのリソースが含まれる
* dynamic feature
  * AndroidManifest
    * split
    * <dist:module>
  * build.gradle
    * feature : com.android.dynamic-feature
    * base : dynamicFeatures
  * code
    * SplitInstallManager
    * SplitInstallRequest
    * getInstalledModules
    * deferredUninstall
  * プロセスの再起動なしで利用できる
* Updating module
  * 常に同期される
