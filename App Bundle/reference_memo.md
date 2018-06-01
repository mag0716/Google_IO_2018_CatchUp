# Android App Bundle

## https://developer.android.com/platform/technology/app-bundle/

### Feature

* Stop managing multiple APKs
  * 全てのコード、リソース、ライブラリを含んだ生成物を1つビルドする
* Benefit from a smaller app
  * インストールするデバイスの構成によって Google Play が動的に apk を提供する
  * その結果エンドユーザは自分のデバイスに不要なリソースが削除され、ダウンロードサイズが削減できる
* (BETA)Deliver features on-demand, not at install
  * 主な機能だけをインストールすることができる
  * その後、必要になった時に、インストールすることができるようになる
  * 使うには Beta Program に参加することが必要
* (COMING SOON)Enable modules to run instantly, without install
  * ユーザはインストールなしに直ちにモジュールを起動することができる

## [Abound Android App Bundles](https://developer.android.com/guide/app-bundle/)

* アプリのコード、リソースを全て含んだ新しいアップロードフォーマットで、apk の生成と署名は延期される
* Dynamic Delivery
  * ユーザーのデバイスの構成ごとに最適化されたアプリが生成され、必要なコード、リソースのみがダウンロードされる
  * 複数の apk 管理は不要になり、ダウンロードサイズが削減できる
* dynamic feature modules
  * 初回のインストール時にダウンロードされるかを決定することができる
  * これらの modules のダウンロードをあとからリクエストできる
  * まだ、Beta で対応するにはリファクタリングが必要

### Dynamic Delivery with split APKs

* 21 以上から有効
* Google Play はが、ユーザーのデバイスに必要な単位で分割する
* Base APK
  * 他の module からアクセスする基本的な機能のコードとリソースなどを含む
  * ユーザがダウロードするさいに最初にインストールされる
* Configuration APKs
  * 特定の解像度、CPUアーキテクチャ、言語用のライブラリ、リソース
  * 大部分のアプリではリファクタリングは不要で、Google Play が生成する
* Dynamic feature APKs
  * 初回インストール時には不要な機能のコード、リソース
* 20 以下では、自動的に 1つの APK が提供されるので、20 以下を対象としていても利用できる

### Build, deploy, and upload Android App Bundles

* APK との違いはデバイスにインストールできないこと
* Android App Bundles は1つの生成物が生成され、アップロード後に、Google Play が APKs を生成し、Dynamic Delivery を通じてユーザーに提供される

### Test your app bundle with bundletool

* Google Play がどうやって APKs を生成するのかやデバイスにインストール後にどのように振る舞うのかをテストするべき
* bundletool, Gradle のコマンドラインツール, Android Studio, Google Play を使ってローカルでテストできる
* ローカルでテストした後は、Google Play を通して(internal test track)テストするべき

### Download dynamic feature modules with the Play Core Library

* dynamic features を含んでいる場合は、Play Core Library にダウンロードを要求する
* [android-dynamic-features](https://github.com/googlesamples/android-dynamic-features)

### A note about Instant Apps

* Android App Bundle と Instant Apps は両方ともモジュール化を有効とすることを目的としている
* 将来、Android App Bundle は Instant Apps をサポートする

### Get Started

1. Android Studio 3.2 をダウンロードする
2. Dynamic Delivery 用にプロジェクトを構成する
3. Android App Bundle をビルドする
4. bundletool を使ってテスト
5. Google Play に署名を登録する
6. Play Console からアップロードする

#### Considerations for dynamic features beta tester

* dynamic features を含む場合は、まずは[Beta Program](https://support.google.com/googleplay/android-developer/answer/9006925#beta) に登録する必要がある
* Android 5.0 以上がサポート
  * それ以前で利用するためには、Fusing を有効にする
* SplitCompat を使うと、dynamic features に動的にダウンロードすることができる
* dynamic feature のサイズが大きい場合は、ユーザに確認する必要がある
* dynamic feature は `android:exported` に true を指定すべきではない
  * 別のアプリがその Activity を起動する際に存在するとは限らないから
* コードやリソースにアクセスする前にダウンロード済みかどうかを確認する必要がある
* リリース前には Google Play を通じてテストする

### Known issues

* APK expansion files をサポートしていない
  * Google Play は 100MB以下を必須としている
  * base feature, dynamic feature はそれぞれ 100MB 以下で、制限を超えていたら警告が出る
* 動的にリソーステーブルを修正するツールの利用は非推奨
* dynamic module の manifest で base module に存在しないリソースを使用してはいけない
  * Google Play が生成時の manifest のマージでリンクが壊れる
* Android Studio 3.2 Canary 14
  * base module のビルドバリアントを変更しても、dynamic feature のビルドバリアントが自動的に変わらない
* base module と dynamic feature module のプロパティで異なる設定値が可能になっている
  * デフォルトでは、dynamic feature module は base module の設定値を受け継ぐ
* インストールは1つ以上の apk  が必要となるので、デバイスに手動で送る場合は注意が必要
  * 失敗すると、実行時に問題が発生する
* 現在、Google Play では、1つの APK で提供されている
  * 近々、5.0 以上の端末で最適化された APK が提供されるようになる
* dynamic feature module のダウンロードには、Play Store app の最新版がインストールされている必要がある
  * それ以前の、Play Store app では、4.4 以下のデバイスと同じ動きになる

## [Build, deploy, and upload Android App Bundles](https://developer.android.com/guide/app-bundle/build)

* gradle.properties に `android.enableAapt2=true` を指定する必要がある
  * 新規プロジェクトはデフォルトで有効になっている

### The Android App Bundle format

* .aab
* Dynamic Delivery と呼ばれる Google Play がサポートする新しい提供モデル
* 署名された ZIP ファイル形式で、Google Play が有効な APKs を生成する
* 以下が Google Play によってモジュールごとに configuration APKs として利用される
  * drawable
  * values
  * lib
* App Bundle は以下の構成になっている
  * base/, featuer1/, feature2/
    * モジュールごとに配置される
    * base module は常に配置される
    * dynamic feature モジュールは split 属性によって命名される
  * `*.pb`
    * Protocol Buffer
    * Google Play にコンテンツの概要を説明するメタ情報を提供する
    * ex. BundleConfig.pb
      * 地震の bundle についてのビルドバージョンなどの情報
    * ex. resources.pb, native.pb
      * デバイス設定ごとにどうやってコードやリソースを使うかの情報
  * manifest/
    * APKs のものとは異なる
    * モジュールごとに存在する
  * dex/
    * APKs のものとは異なる
    * モジュールごとに存在する
  * root/
    * あとでこのディレクトリがあるモジュールを含む apk のルートに再配置されるファイルが格納される
      * ex. base/root/ : Class.getResource() でロードされるリソース
    * このディレクトリも apk に再配置される
    * コンテンツがモジュール間でコンフリクトしている場合は、Play Console のアップロードに失敗する
  * res/, lib/, assets/
    * 一般的な apk と同じ
    * App Bundle をアップロードするとき、Google Play がデバイスの構成を満たすファイルのみを使ってパッケージ化する

### Deploy your app from an app bundle

* Android Studio の run/debug では、App Bundle の生成は行わない
  * Instant Run は使えない
  * Android Studio のビルドシステムは、App Bundle を生成してから、APKs を生成する必要がある
* Android Studio を使ってデプロイする
  * Run -> Edit Configurations
  * Android App ノードを選択
  * Deploy を「APK from app bundle」
  * [表示されなかった] Dynamic features to deploy の下のチェックボックスを必要なモジュールを有効にする
  * OK
  * この設定後に実行すると、まずは、App Bundle を生成して、デバイスに必要な APKs が生成される
  * dynamic feature module のダウンロード、インストールをテストしたい場合は、Play Console の internal test track を使う

### Build an app bundle using Android Studio

* Android Studio を使って生成する
  * Build -> Build Bundle(s)/APK(s) -> Build Bundle(s)
  * project-name/module-name/build/outputs/bundle/ に生成される

* debug の場合は自動的にデバッグ署名で署名される
* bundletool を使うことでデプロイすることが可能
* App Bundle でも APK Analyzer のように解析可能
* Android Studio も bundletool を使っている

#### Build a signed app bundle for upload

### Build an app bundle using Android Studio

## [Download modules with the Play Core Library](https://developer.android.com/guide/app-bundle/playcore)

## [Configure your project for Dynamic Delivery](https://developer.android.com/guide/app-bundle/configure)

## [Test Android App Bundles with bundletool](https://developer.android.com/guide/app-bundle/test)
