# WorkManagerSample

## 対象バージョン

* 1.0.0-alpha08

## サンプル

| モジュール名 | 概要 | 作成バージョン | 備考 |
| - | - | - | - |
| onetime | Worker を1度実行するサンプル | 1.0.0-alpha08 | |
| periodic | Worker を定期的に実行するサンプル | 1.0.0-alpha08 | interval を1分に指定しているが、最小リピート間隔は呼び出す API や端末の状態による |
| cancel | ID を指定してキャンセルするサンプル | 1.0.0-alpha08 | |
| chainedtask | 2つの Worker を直列、並列で実行するサンプル | 1.0.0-alpha08 | |

### periodic

```
2018-08-30 22:17:08.026 24260-24260/com.github.mag0716.periodic D/PeriodicWork: observe : status = WorkStatus{mId='8d4f2e0a-4ce7-4b4c-8513-552920c84740', mState=ENQUEUED, mOutputData=androidx.work.Data@0, mTags=[com.github.mag0716.common.LoggingWorker]}
2018-08-30 22:17:08.036 24260-24301/com.github.mag0716.periodic D/PeriodicWork: doWork start...
2018-08-30 22:17:08.100 24260-24260/com.github.mag0716.periodic D/PeriodicWork: observe : status = WorkStatus{mId='8d4f2e0a-4ce7-4b4c-8513-552920c84740', mState=RUNNING, mOutputData=androidx.work.Data@0, mTags=[com.github.mag0716.common.LoggingWorker]}
2018-08-30 22:17:11.039 24260-24301/com.github.mag0716.periodic D/PeriodicWork: doWork finish!!
2018-08-30 22:17:11.055 24260-24260/com.github.mag0716.periodic D/PeriodicWork: observe : status = WorkStatus{mId='8d4f2e0a-4ce7-4b4c-8513-552920c84740', mState=ENQUEUED, mOutputData=androidx.work.Data@0, mTags=[com.github.mag0716.common.LoggingWorker]}
2018-08-30 22:32:13.951 24260-24415/com.github.mag0716.periodic D/PeriodicWork: doWork start...
2018-08-30 22:32:13.957 24260-24260/com.github.mag0716.periodic D/PeriodicWork: observe : status = WorkStatus{mId='8d4f2e0a-4ce7-4b4c-8513-552920c84740', mState=RUNNING, mOutputData=androidx.work.Data@0, mTags=[com.github.mag0716.common.LoggingWorker]}
2018-08-30 22:32:16.953 24260-24415/com.github.mag0716.periodic D/PeriodicWork: doWork finish!!
2018-08-30 22:32:16.966 24260-24260/com.github.mag0716.periodic D/PeriodicWork: observe : status = WorkStatus{mId='8d4f2e0a-4ce7-4b4c-8513-552920c84740', mState=ENQUEUED, mOutputData=androidx.work.Data@0, mTags=[com.github.mag0716.common.LoggingWorker]}
```
