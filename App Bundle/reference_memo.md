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

* Play Console にアップロード前に署名済みの App Bundle を生成する必要がある
  * Build -> Generate Signed Bundle/APK
  * Generate Signed Bundle or APK
    * Android App Bundle
  * Module ドロップダウンメニュー
    * base モジュールを選択
  * keystore の情報を指定
  * 出力先を指定
* ポップアップメニューの locate リンクで出力先を表示できる

### Build an app bundle from the command line

* コマンドラインから生成する
  * `build${Variant}`
    * ex. `./gradlew :base:bundleDebug`
  * 署名付きで生成したい場合は、build.gradle に署名情報を定義しておく
  * ビルド後に署名する場合は、jarsigner を使う
    * apksigner は App Bundle には使えない

### Upload your app bundle to the Play Console

* Play Console で解析、テスト、公開ができる
* 以下のことを知っておくこと
  * App Bundle をアップロード前に、App Signing を登録しておく必要がある
  * dynamic feature modules を含んでいる場合は Play Console にアップロード、internal test は可能だが、公開は [Beta Program](https://support.google.com/googleplay/android-developer/answer/9006925#beta) に登録する必要がある
  * 100 MB 以下のダウンロードをサポートしている
    * base module, dynamic features ごとに 100MB 以下になっている必要がある
    * アップロード後に制限に引っかかっているかはチェックしてくれる

#### Inspect APKs using bundle explorer

* Play Console にアップロードすると、全てのデバイス構成用に APKs を分割する
* Play Console で App Bundle Explorer を利用できる
  * 生成される APK
  * どれだけサイズがセーブできたか
  * ローカルテスト用に APKs のダウンロード

#### Test Dynamic Delivery using the internal test track

* 簡単にテストするためには、internal test track が利用できる
* 登録されたテスターに素早く共有できる
* Google Play から Dynamic Delivery としてダウンロードできる

#### Update your app

* internal test track へアップロードするには、versionCode をインクリメントする必要がある

## [Configure your project for Dynamic Delivery](https://developer.android.com/guide/app-bundle/configure)

* Dynamic Delivery をサポートするためにはビルド構成を変更する必要がある
  * 大部分のプロジェクトでは、少しの作業で対応できる
* dynamic feature modules
  * Dynamic Delivery を通じて、ユーザーが必要な時に dynamic features をダウンロード、インストールすることができる
  * こちらを対応するにはリファクタリングが必要になる
  * まずは、アプリで対応する利点があるのかを検討する必要がある

### Dynamic Delivery with split APKs

* APKs の構成
  * Base APK
    * 他の APKs がアクセス可能なコード、リソースを含み、基本的な機能を提供する
    * アプリのダウンロード時に最初にダウンロード、インストールされる
    * manifest に全ての宣言を記述
      * Services
      * ContentProvider
      * Permissions
      * バージョン
      * 他のモジュールへの依存
    * base モジュールから生成される
  * Configuration APKs
    * 解像度、CPU アーキテクチャ、言語ごとのライブラリ、リソースを含む
    * インストールするデバイスに合致する APKs のみインストールされる
    * base, dynamic feature とは異なり、作成することはできない
  * Dynamic feature APKs
    * 特定機能のコード、リソースを含み、初回のインストール時には必須ではない
    * Play Core Library を使って、 Base APK インストール後に提供される
    * ex.チャットアプリ
      * 写真の撮影、送信機能
    * dynamic feature モジュール から生成される

#### Devices running Android 4.4(API level 20) and lower

* 4.4 以下では、APKs のダウンロード、インストールにサポートしていないので、1つの APK で提供される
  * 全ての機能は含まれているが、不要なコード、リソースは含まれない
* ダウンロードなしに言語設定を切り替えるようにしたいのであれば、全ての言語を APKs に含めることができる
* dynamic feature モジュールを含めるためには、On-demand を無効化するか、Fusing を有効化する
* 4.4 以下を対応するかに関わらず、Dynamic Delivery は利用できる

### The base module

* 初回のダウンロードサイズを減らすことを考えている場合は、base モジュールのコードはサイズに必ず含まれることを覚えておく必要がある
* アプリのコア機能だけでなく、アプリ全体に影響を及ぼす、ビルド設定、マニフェストの定義を含む
  * ex.App Bundle の署名は、base モジュール用に指定した情報で決定する
  * ex.アプリのバージョンは、base モジュールの versionCode で決定する

#### The base module manifest

* マニフェストの定義は他のモジュールと類似している
* dynamic feature modules を検討している場合は、以下に気をつける必要がある
  * アプリの開始 Activity を定義する必要がある
  * Android 6.0 以下では、モジュールのインストール完了前にアプリの再起動が必要
    * 即座にアクセスする場合は、[SplitCompat](https://developer.android.com/guide/app-bundle/playcore#access_downloaded_modules) を含める必要がある
  * Android 6.0 以下では、プラットフォームが manifest を適用する前に、アプリの再起動が必要
    * dynamic feature 用の permissions や services の定義を base モジュールに持たせることを検討する

#### The base module build configuration

* dynamic feature modules の追加を検討している場合は、以下に気をつける必要がある
  * App signing
    * 署名情報を含める必要はないが、含める場合は、base module のみに含める
  * Code shrinking
    * code shrinking を有効化したい場合は、base module の build.gradle で定義する必要がある
    * Proguard のカスタムルールは、dynamic feature modules に含めることができるが、`minifyEnabled` の設定は無視される
  * The splits block is ignore
    * App Bundle を生成する時、`android.splits` は無視される
    * これらの設定を操作したい場合は、`android.bundle` を代わりに利用する
  * App versioning
    * versionCode, versionName は base module の定義で決定する

#### Disable types of configuration APKs

* デフォルトでは、App Bundle をビルドする時、configuration APKs は言語、解像度、ABI libraries ごとに生成される
* `android.bundle` の設定で無効化することができる
  * language, density, abi
    * enableSplit に false 指定で、生成されなくなる

#### Manage app updates

* バージョンは base module の定義のみで管理可能
* base module のバージョンが、全ての生成される APKs に利用される
* アップデート時も今まで通り、base module のバージョンを上げるだけでよい
  * Multiple APK の場合は、それぞれ管理する必要があった

### Dynamic feature modules

## [Test Android App Bundles with bundletool](https://developer.android.com/guide/app-bundle/test)

* App Bundle のテストには2種類ある
  * bundletool を使って、ローカルでテストする
  * Google Play の internal test track を利用する
* bundletool
  * Gradle, Android Studio, Google Play が利用するツール
  * デプロイする端末の構成用の APKs へ変換するツール
  * 通常は Android Studio を使うべきだが、CI などを利用する場合に利用する
  * デフォルトでは、Android Studio では、bundletool を利用しないが、run/debug configuration の変更すると利用されるようになる

### Download bundletool

* https://github.com/google/bundletool/releases

#### Generate a set of APKs from your app bundle

* `*.apks` の、APK をまとめて管理するファイルが生成される
  * 全てのデバイス構成用の APK が含まれる
  * `bundletool bunild-apks`
    * 未署名の APKs が生成される
  * 署名する場合
    * --ks:keystoreのパス
    * --ks-pass:keystore のパスワード
    * --ks-key-alias:エイリアス
    * --key-pass:key エイリアスのパスワード
* オプション
  * --bundle:(必須) App Bundle のパス
  * --output:(必須) APKs の出力先
  * --aapt2:AAPT2 をカスタムする場合のパス
    * デフォルトでは bundletool に AAPT2 のバージョンが含まれている
  * --ks:keystoreのパス
  * --ks-pass:keystore のパスワード、もしくは、パスワードが記載されたフィアルのパス
  * --ks-key-alias:エイリアス
  * --key-pass:エイリアスのパスワード、もしくは、パスワードが記載されたファイルのパス
  * --universal:全てのデバイス構成で使える APKs を生成する場合に指定
    * `<dist:fusing dist:include="true"/>` が含まれるモジュールのみ利用する
    * あくまで、簡易テスト用で、最適化はされていない

#### Deploy APKs to a connected device

* Android 5.0 以上であれば、必要な構成のみインストールされる
* Android 4.4 以下であれば、全ての APK がインストールされる
* `bundle install-apks`
  * --apks:APKs のパス
  * 複数のデバイスが接続されている場合は、`--device-id` でインストールするデバイスを指定する必要がある

#### Extract APKs for a specific device configuration

* `bundletool extract apks`
  * APKs から特定のデバイス構成用の APKs を抜き出す
  * --output-dir:出力先
  * --device-spec:デバイス構成が記載された JSON ファイルのパス
* `bundletool get-device-spec`
  * 接続されたデバイスの構成情報を取得する
  * --output:(必須)出力先

#### Manually create a device specification JSON

* 自前で JSON ファイルを作成してもいい

## [Download modules with the Play Core Library](https://developer.android.com/guide/app-bundle/playcore)
