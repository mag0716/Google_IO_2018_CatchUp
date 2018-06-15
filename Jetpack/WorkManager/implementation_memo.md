## WorkDatabase

* abstract class
* テーブル
  * Dependency
    * `Database entity that defines a dependency between two {@link WorkSpec}s.`
  * WorkSpec
    * `Stores information about a logical unit of work.`
    * state: Worker の状態。初期値は `ENQUEUED`
    * Worker や InputMerger のクラス名
    * input, output も保持している
    * duration や backoff などの Worker を実行するために利用する時間
    * Constraints
  * WorkTag
    * `Database entity that defines a mapping from a tag to a {@link WorkSpec} id.`
  * AlarmInfo
    * `Stores Alarm ids for a {@link WorkSpec}.`
  * WorkName
    * `Database entity that defines a mapping from a name to a {@link WorkSpec} id.`

### State はいつ更新されるのか？

* `WorkSpecDao#setState` で State を更新する
* `CancelWorkRunnable#recursivelyCancelWorkDependents` で `CANCELLED` に変更
* `StopWorkRunnable#run` で `ENQUEUED` に変更
* `WorkerWrapper`
  * `trySetRunning` で `ENQUEUED` の場合に、`RUNNING` に変更
  * `recursivelyFailWorkAndDependents` で `CANCELLED` 以外の場合に、`FAILED` に変更
  * `rescheduleAndNotify` で `ENQUEUED` に変更
  * `resetPeriodicAndNotify` で `ENQUEUED` に変更
  * `setSucceededAndNotify` で `SUCCEEDED` に変更
    * さらに、該当する Worker に依存している Worker を `ENQUEUED` に変更

### WorkSpecDao で delete が呼ばれるのはいつ？

* `EnqueueRunnable#enqueueWorkWithPrerequisites`
  * 同じタグの Worker の実行で、`ExistingWorkPolicy.APPEND` 以外の場合に、既存の Worker が削除される
-> つまり、Worker が成功しても、DB からは削除されない
