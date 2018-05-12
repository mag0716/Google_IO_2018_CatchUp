# Navigation

* [Schedule tasks with WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)
* [Background Work with WorkManager](https://codelabs.developers.google.com/codelabs/android-workmanager/)

## 概要

* deferrable background work(延期可能なバックグラウンドでの処理)を compatible, flexible, simple に実行することができる
* stable になれば、推奨の Task Scheduler

## Codelabs

* Opportunistic execution：可能になったらすぐに実行されるバックグラウンド処理
* Guaranteed execution：アプリが終了されていても、特定状況下で実行されるバックグラウンド処理

* one-off and periodic タスクのどちらもサポートしている
* ネットワーク状態、ストレージ領域、充電状況にサポートしている
* work の出力を他の work の入力に使える
* API level 14 対応
* Google Play services がなくてもいい
* システムの状態に従う(おそらく、Doze、Background Limits)
* work のステータスを LiveData で取得できる

* JobScheduler, FirebaseJobDispathcer, AlarmManager などの API の上に位置する

* WorkManager を使用する例
  * log のアップロード
  * 画像のフィルタの適用と保存
  * 定期的なデータの同期
* https://developer.android.com/guide/background/ に従う

* Data
  * key-value で管理するコンテナで WorkRequest の入力、出力に利用される
  * Data.Builder を使って生成する

* Chain Work
  * WorkerManager#beginWith
    * WorkContinuation が返却され、WorkerRequest を指定してチェインする

### クラス概要

* WorkerUtils：実際の blur 処理を行なっている
* BlurActivity：blur レベルを選択して、blur を実行する Activity
* BlurViewModel：BlurActivity の ViewModel
* SelectImageActivity：画像選択 Activity

### WorkManager Basics

* Worker
  * 実際のバックグラウンド処理を実装するクラス
  * doWork メソッドに実装する
  * OneTimeWorkRequest
    * 一度だけ実行される
  * PeriodicWorkRequest
    * 何度も繰り返し実行される
* WorkRequest
  * Worker から生成されるリクエストクラス
  * Constraints を指定できる
* WorkManager
  * WorkRequest を実行するクラス


## 疑問点
