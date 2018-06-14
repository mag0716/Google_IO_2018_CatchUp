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

### Define your Slice template

* `ListBuilder` を使って Slice が作成される
  * `ListBuilder` は違った種類の行を一覧に表示することができる

#### SliceAction

* PendingIntent とラベルを含み、以下のいずれかをもつ
  * Icon button
  * Default toggle
  * Custom toggle

#### HeaderBuilder

* 大部分では、`HeaderBuilder` を使ってヘッダーをセットすべき
  * Title
  * Subtitle
  * Summary Subtitle
  * Primary action

##### Header rendering on different surfaces

* ヘッダーの概要を指定したかったら、subtitle の代わりに概要を表示する

#### SliceActions in headers

* ヘッダーにも SliceActions を表示することができる

#### RowBuilder

* 行の作成は `RowBuilder` を使用する
  * Title
  * Subtitle
  * Start item
    * SliceAction, Icon, timestamp
  * End items
    * SliceAction, Icon, timestamp
  * Primary action
* 行を結合することもできるが、以下の制約がある
  * End items は SliceActions と Icons を共存できない
  * timestamp は1つだけしか含めることができない

#### GridBuilder

* グリッドの作成は `GridBuilder` を使用し、以下の画像タイプをサポートしている
  * ICON_IMAGE
  * SMALL_IMAGE
  * LARGE_IMAGE
* セルの作成は `CellBuilder` を使用し、以下をサポートしている
  * ２行のテキスト
  * 1つの画像
* 空のセルは不可能

#### RangeBuilder

* プログレスバーを含む行の作成は `RangeBuilder` を利用する

### Delayed content

* `SliceProvider#onBindSlice` は可能な限り早く Slice を返却するべき
  * 時間をかけると、ちらつきや急なリサイズが発生してしまう
  * すぐにコンテンツを表示できない場合は、先にプレイスホルダーを表示する
  * 準備ができたら、`getContentResolver().notifyChange` で表示を更新する

### Handle disabled scrolling within your Slice

* Slice を表示する領域次第ではスクロールをサポートしていないかもしれない
  * その場合はいくつかのコンテンツが表示できなくなってしまう
* 表示するコンテンツが多い場合は、`addSeeMoreAction` を利用する
  * ボタンタップ時は、PendingIntent を通じて、アプリなどへ遷移させる
  * 以下の状況のときは `addSeeMoreAction` を使うべき
    * Slice がスクロール無効
    * コンテンツを表示する領域が十分にない

### Combine templates

* 複数のタイプの行を組み合わせて rich, dynamic な Slice を作成することができる
