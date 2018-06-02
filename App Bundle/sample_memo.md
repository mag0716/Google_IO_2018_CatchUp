# Sample メモ

リファレンスに記載の内容を試してみたり、疑問になった点をメモ

## TODO

* bundletool 使ってみる
* Play Console 使ってみる

## 実際に試したこと

### 署名時に誤った情報を渡したらどうなる？

#### パスワードが誤っている

```
FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':app:packageReleaseBundle'.
> java.util.concurrent.ExecutionException: java.lang.RuntimeException: jarsignerfailed with exit code 1 :
  jarsignerエラー: java.lang.RuntimeException: キーストアのロード: Keystore was tampered with, or password was incorrect

* Try:
Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output. Run with --scan to get full insights.

* Get more help at https://help.gradle.org
```

#### エイリアスが誤っている

```
FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':app:packageReleaseBundle'.
> java.util.concurrent.ExecutionException: java.lang.RuntimeException: jarsignerfailed with exit code 1 :
  jarsigner: 次の証明書チェーンが見つかりません: androiddebugkeystore。androiddebugkeystoreは、秘密鍵および対応する公開鍵証明書チェーンを含む有効なKeyStore鍵エントリを参照する必要があります。

* Try:
Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output. Run with --scan to get full insights.

* Get more help at https://help.gradle.org
```

## 疑問に思ったこと

* .aab が署名されているかどうかはどうやって確認する？
  * .apk の場合は、`jarsigner -verify -verbose -certs hoge.apk`
* internal test track を実案件でどう運用するのがよいか？
  * 署名ファイルは何をもらっておけばよいか？
  * クライアント側では何をしてもらう必要があるのか？
