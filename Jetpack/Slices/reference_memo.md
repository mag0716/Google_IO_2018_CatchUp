# Slices

## [Overview](https://developer.android.com/guide/slices/)

Slices は、Google Search や Google Assistant にアプリのコンテンツを rich, dynamic, interactive に表示することができる UI テンプレート
App Actions の効果を高めるために利用することができる

Android 4.4+

## [Getting Started](https://developer.android.com/guide/slices/getting-started)

### Prerequisites

* 新規プロジェクトか AndroidX へのリファクタリングが完了しているプロジェクト
* Android Studio 3.2 は必須ではないが、Slice の開発で有益なツールが含まれている
  * AndroidX のリファクタリング
  * Slice の Lint
  * SliceProvider テンプレート

### Download and install the Slice Viewer

* Slice Viewer は SliceView API の実装なしに Slice のテストが可能
  * Android 4.4+

### Run the Slice Viewer

Android Studio と コマンドラインから Slice Viewer を起動できる

#### Launch Slice Viewer from your Android Studio project

1. Run -> Edit Configurations
1. + ボタン
1. Android App を選択
1. Name に slice を入力
1. Module ドロップダウンで app モジュールを選択
1. Launch Options の Launch ドロップダウンで URL を選択
1. slice-<your slice URI> を入力
1. OK

1度作成すれば Slice のテスト時に利用できる

#### Launch the Slice Viewer tool via ADB (command line)

* インストール
  * `adb install -t -r <yourapp>.apk`
* Slice の起動
  * `adb shell am start -a android.intent.action.VIEW -d slice-<your slice URI>`

#### View all of your Slices in one place

* 1つの Slice に加えて、Slice の一覧を表示することができる
  * URI を通じて Slice を手動で検索する
  * Slice URI で Slice Viewer を起動する時に一覧に追加される
  * スワイプで一覧から削除できる
  * Slice の URI タップで、その Slice のみが含まれた状態で起動する
* Slice の一覧表示中はスクロールが無効なのでスクロールのテストは1つの Slice の表示で行う

#### View the Slice in different modes

* SliceView#mode を切り替えることができ、それぞれのモードで期待する表示になるかを確認できる

### Build your first Slice

* New -> Other -> Slice Provider で追加することができる
  * SliceProvider を継承したクラスが作成され、AndroidManifest に追加される。また、build.gradle に Slice の依存関係が追加される
* Slice ごとに関連する URI を持っている
  * Slice を表示する時、この URI を使って、バインディングをリクエストする
  * それから Slice は `onBindSlice` を通じて、ダイナミックにビルドされる

### Interactive Slices

* Notification と同様で、PendingIntent によって Slice のクリックをハンドリングできる
  * `SliceAction` に PendingIntent を渡す
* Slice はトグルボタンのような入力もサポートしている

### Dynamic Slices

* Slice は動的なコンテンツも含めることができる
  * 例えば、BroadcastReceiver でデータを受け取って、Slice の表示を更新できる
  * `ContentResolver#notifyChange`

### Templates

* Slice は様々なテンプレートをサポートしている

## [Slice templates](https://developer.android.com/guide/slices/templates)
