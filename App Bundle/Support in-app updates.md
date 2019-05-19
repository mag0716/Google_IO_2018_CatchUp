# Support in-app updates

https://developer.android.com/guide/app-bundle/in-app-updates

PlayCore ライブラリでユーザにアプリ内で更新をリクエストする機能を提供する。

Android 5.0(21) 以上の端末、 PlayCore 1.5.0 以上で動作する。

* Flexible
  バックグラウンドでのダウンロード、インストールを提供する。
  ダウンロード中もアプリを利用可能にするケースで適切
  例えばクリティカルな新しい機能を早めに試してもらいたいときなど
* Immediate
  アプリを継続利用するためには更新か再起動が必要なケース
  アプリの継続利用にクリティカルな更新に最適
  更新後、Google Play は更新し再起動する

Note: Android App Bundle を発行する時、アップデートのサイズは 150MB が最大
In-app updates は 拡張 APK(.obb) には対応してない

## Check for update availability

更新リクエスト前にアプリの更新が必要かどうかをチェックする必要があり、`AppUpdateManager` を利用する

アップデートが有効で許可されている場合は、`AppUpdateInfo` が返される

もし in-app update が実行中であれば、そのステータスも渡される

## Start an update

更新のリクエストは `AppUpdateManager.startUpdateFlowForResult` を利用する
ユーザが邪魔だと思うような利用はすべきではなく、重要な機能のみでの利用に限定する必要がある

更新の失敗は監視できるので失敗しない限り、たった一度だけのリクエストにする必要がある

### Get a callback for update status

アップデート開始後、`onActivityResult` で更新の失敗、キャンセルをハンドリングする

`RESULT_OK`：ユーザが更新を許可した
          Immediate では Google Play によってハンドリングされている可能性があるのでコールバックを受け取れない場合がある
`RESULT_CANCELLED`：ユーザが更新を拒否した
`RESULT_IN_APP_UPDATE_FAILED`：エラーでユーザが同意できないか、更新を継続できない

## Handle a flexible update

flexible update を開始すると、ダイアログが表示される
ユーザが許可するとダウンロードがバックグラウンドで開始され、ユーザがアプリを継続利用することができる

### Monitor the flexible update state

ユーザが許可後、Google Play はバックグラウンドでダウンロードを開始する
ダウンロード開始後、アプリで更新完了や更新状態を表示したい場合は更新状態を監視する必要がある

Note：更新状態の監視は Flexible のみ
Immediate の場合は、Google Play がダウンロード、インストールをケアするので、更新失敗のみを `onActivityResult` でハンドリングする

更新状態の監視は InstallStateUpdatedListener を登録する

### Install a flexible update

更新状態を監視し、ダウンロード完了を検知したらアプリのインストール、更新のためにアプリの再起動が必要

Flexible ではユーザが更新前にアプリを継続して利用したいケースがあるかもしれないので、自動的に再起動はされない

Notification などでユーザに知らせるのが推奨される

例えば、SnackBar で通知し、ボタンタップで再起動する

アプリがフォアグラウンドで `AppUpdateManager#completeUpdate` を呼び出すとアプリ再起動のための全画面 UI が表示される。
インストール、更新後に main Activity を再起動する。

バックグラウンドで `AppUpdateManager#completeUpdate` を呼び出すと、サイレントに更新される

ユーザがアプリをフォアグラウンドにした時に更新待ちがないかを確認することが推奨される
もし `DOWNLOADED` であれば Notification でインストール、更新を促す
更新しない限り更新データがストレージに残り続けることになる

## Handle an immediate update

Immediate update の場合、ユーザが許可すると、Google Play はアプリの上に全画面の UI を表示する。
更新中、ユーザがアプリを閉じると、バックグラウンドでダウンロード、インストールが継続される。

ただし、フォアグラウンドに戻ってきた時に、更新中かどうかを `UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS` で確認する必要がある。
更新中の場合は、更新を `AppUpdateManager#startUpdateFlowForResult` で再開する

## Troubleshoot

* in-app updates はアプリを所有するアカウントのみに有効なので、一度でもインストールしている必要がある
* in-app updates のテストの場合、application ID、署名が同じかどうかを確認する
* 更新対象は version code が上である必要がある
* アカウントが適切で、Google Play のキャッシュが最新であるかを確認するために以下を実行する
  * Google Play を完全に終了させる
  * Google Play の My Apps & Games を開く
  * テスト中に更新が有効にならない場合、適切な test track を利用しているかをチェックする

## 疑問点

* AppUpdateInfo#isUpdateTypeAllowed で一方が true でもう一方が false　になるケースは何？
