# Navigation

* [Schedule tasks with WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)
* [Background Work with WorkManager](https://codelabs.developers.google.com/codelabs/android-workmanager/)

## 概要

* deferrable background work(延期可能なバックグラウンドでの処理)を compatible, flexible, simple に実行することができる
* stable になれば、推奨の Task Scheduler

## リファレンス

* アプリが強制終了されても、デバイスが再起動しても実行される
* プロセスが終了すると終了させたいような処理向きではない
* WorkManager は API レベルとアプリの状態によって適切な方法を選択する
  * アプリが起動中であれば、アプリのプロセスの別スレッドで実行される
  * 起動中でなければ、JobScheduler, Firebase JobDispatcher, AlarmManager のいずれかが利用される
* 追加の特徴
  * チェインタスク
  * LiveData でのステータスの監視

### Classes and concepts

* Worker
  * このクラスを継承して、実際のタスクを実装する
* WorkerRequest
  * Worker とタスクを実行するべき状況をセットする
  * WorkRequest 毎にユニークIDが自動生成される
    * State の取得やキャンセルに利用される
  * OneTimeWorkRequest, PeriodWorkWorkRequest がサブクラスが用意されている
  * WorkRequest.Builder から WorkRequest が生成される
  * WorkRequest.Builder を通じて Constraints でを実行状況を指定する
* WorkManager
  * WorkRequest を渡してキューに入れる
  * 指定された Constraints を満たしながら、システムリソースへの負荷を分散するような方法でスケジュールする
* WorkStatus
  * タスクの情報を保持する
  * LiveData を通じて提供される

### Typical workflow

* Worker を実装するクラスを作成し、doWork を実装する
* WorkRequest を生成し、WorkManager に enqueue する
* WorkManager は適切なタイミングでタスクを実行する
* タスクの状態を監視したいなら、LiveData をハンドリングする

#### Cancelling a Task

* タスクをキャンセルしようとすると、すでに実行中または終了している可能性があるので注意

### Advanced functionality

#### Recurring tasks

* PeriodicWorkRequest を利用する
  * 使い方は OneTimeWorkRequest と同じ

#### Chained tasks

* WorkManager#beginWith
  * OneTimeWorkRequest を複数渡して並列に実行させることができる
* WorkContinuation#then
* いずれかのタスクが失敗したらそこでタスクは終了する
* WorkContinuation を組み合わせる事もできる

### Unique work sequences

* WorkManager#beginUniqueWork
* unique work は名前を持ち、WorkManager は1度に1つのタスクが実行する
* すでに実行されている状態で新しいタスクを実行する時の挙動は以下の3つ
  * REPLACE：既存のタスクがキャンセルされ、新規のタスクが実行される
  * KEEP：既存のタスクが継続され、新規のタスクが無視される
  * APPEND：既存のタスクが終了した後に新規のタスクが実行される

### Tagged work

* WorkRequest に String 型の Tag を指定できる
* WorkManager#cancelAllWorkByTag, WorkManager#getStatusesByTag などで利用する

### Input parameters and returned values

* key-value pairs のデータを input, output に渡せる
* input
  * WorkRequest.Builder#setInputData
  * Worker#getInputData
* output
  * Worker#setOutputData
  * WorkStatus#getOutputData
* タスクをチェインしている場合は、output を設定すると次のタスクの input として利用できる

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
    * 引数は List

* Unique Work
  * ローカルデータをサーバと同期させるような処理
    * 他の同期を開始する前に最初の同期を完了させたい
  * beginUniqueWork を利用する
  * ExistingWorkPolicy
    * REPLACE
    * KEEY
    * APPEND

* Tag and Work Status
  * LiveData を通じて WorkRequest から WorkStatus を取得する
  * WorkStatus
    * BLOCKED, CANCELLED, ENQUEUED, FAILED, RUNNING, SUCCEEDED
  * LiveData の取得
    * getStatusById
      * WorkManager が WorkRequest 毎に生成する unique ID を使う
    * getStatuesForUniqueWork
      * 自分で指定した unique ID を指定する
    * getStatuesByTag
      * WorkRequest に複数追加できる Tag を使う
  * Tag
    * getStatuesByTag で指定した Tag の WorkerRequest を取得する
  * WorkStatus#getState().isFinished() で終了しているかどうかをチェックできる

* Cancel work
  * ID, Tag, ユニークなチェイン名を指定することでキャンセルできる

* Constraints
  * Constraints.Builder で生成し、WorkerRequest にセットする
  * Constraints に合致しない状況だと、その状況になるまで待機され続ける
    * Status -> ENQUEUED

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

* Work Status を LiveData から取得しているが前回アプリ実行時の処理結果が取得できているが正しい動作なのか？
