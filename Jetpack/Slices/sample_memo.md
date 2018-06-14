# Slices

## [Codelab](https://codelabs.developers.google.com/codelabs/android-slices-basic/index.html?index=..%2F..%2Findex#0)

* Slices は他の surfaces にアプリのコンテンツを埋め込む新しい手段
  * 柔軟な UI のテンプレートをサポートしている
* What is a slice?
  * Interactive - Slice はユーザに素早く動作すべき
  * Relevant - Slice はユーザにとってもっとも重要なコンテンツを含むべき
  * Modular - Slice はアプリの外側で独立して動作可能とすべき
* step-00
  * Activity 側でのコンテンツの実装
* step-01
  * ライブラリの定義
  * SliceProvider の作成
    * Slice ごとに Uri を持っていて、Slice と Uri をマッピングする
    * Slice を表示する時に Uri をバインディングする
    * SliceProvider は AndroidManifest.xml に定義
      * 全てのパーミッションを内部でチェックしているので export されていても安全
* step-02
  * 現状では Slice を試すには Slice Viewer を使う
    * Android Studio で Slice を試すには、Configurations を変更し、Launch Options に Uri を指定する
    * コマンドラインから試すには、`adb shell am start -a android.intent.action.VIEW -d slice-<your slice URI>`
  * タップ時にパーミッションの許可が必要
  * アプリで値を変えて、Slice Viewer に戻ってくると値が反映される
* step-03
  * SliceAction
    * PendingIntent が必要
      * BroadcastReceiver は exported=false でよい  
    * SliceAction に Icon を渡せる
    * ボタンタップでのアクション
    * View タップ時のアクションは、`setPrimaryAction`
  * コンテンツの更新を Slice に通知するためには、`notifyChange` を利用する
    * `SliceProvider#onBindSlice` が呼ばれる
