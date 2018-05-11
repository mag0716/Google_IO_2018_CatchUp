# WorkManager

[Android Jetpack: easy background processing with WorkManager (Google I/O '18)](https://www.youtube.com/watch?v=IrKoBFLwTN0)

* There are may ways to do it
  * Thread
  * Executors
  * Services
  * AsyncTasks
  * Handlers and Loopers
  * Jobs(API 21+)
  * GcmNetworkManager
  * SyncAdapters
  * Loaders
  * AlarmManager
* Android battery optimizations
  * Doze mode(M)
  * App stanby(M)
  * Limited implicit broadcasts(N)
  * Release cached wakelocks(O)
  * Background service limitations(O)
  * App standby buckets(P)
  * Background restricted apps(P)
* Types of background work
  * Best-Effort <-> Guaranteed Execution
  * Exact Timing <-> Deferrable
* WorkManager features
  * Guaranteed, constraint-aware execution
  * Respectful of system background restrictions
  * Backwards compatible with or without Google Play Services
  * Queryable
  * Chainable
  *
* Core classes
  * Worker
  * WorkRequest
* retry はバックオフで実施されるらしい
* Observing work
  * LiveData が取得できるのでそれを監視する
  * State
    * ENQUEUED
    * RUNNING
    * SUCCEEDED
    * FAILED
    * BLOCKED
    * CANCELLED
* Chaining work
  * sequential
    * `workManager.beginWith(compressWork).then(uploadWork).enqueue()`
  * parallel
    * `workManager.enqueue(uploadWork1, uploadWork2, uploadWork3)`
  * parallel + sequential
    * `workManager.beinWith().then().then().enqueue()``
  * MapReduce
* Data
  * Simple key-value map
  * Serializable
  * Limited 10KB
  * Worker の結果を次の Worker の入力として使える
  * InputMergers
    * 複数の Data から1つの Data を生成する
* Cancelling work
  * WorkManager#cancelWorkById
  * WorkManager#cancelAllWorkByTag
* Tags
  * A more readable way to identify your work
  * 複数設定できる
* Unique work
  * sync operations
  * WorkManager#beginUniqueWork
    * KEEP：実行中の Work を継続する
    * REPLACE：実行中の Work をキャンセルする
    * APPEND：Dataを変更するが、Work は継続する
      * music player のプレイリストの順番などが変わった場合
* PeriodicWork basics
  * 15min
  * Doze, background limits の動作に従う
  * chain、delay はできない
* Implementation details
  * 必要な場合は wake locks を取得する必要がある
* Use the testing library
  * TestDriver
* Best Practice
  * プロセスが死んでも生き残るので、View や payment を扱うものには使ってはいけない
  * 10KB 制限なので保存するデータについては考慮する
  * Room が使われる
  * Be opportunistic
