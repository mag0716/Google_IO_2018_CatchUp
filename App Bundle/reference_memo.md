# Android App Bundle

## https://developer.android.com/platform/technology/app-bundle/

### Feature

* Stop managing multiple APKs
  * 全てのコード、リソース、ライブラリを含んだ生成物を1つビルドする
* Benefit from a smaller app
  * インストールするデバイスの構成によって Google Play が動的に apk を提供する
  * その結果エンドユーザは自分のデバイスに不要なリソースが削除され、ダウンロードサイズが削減できる
* (BETA)Deliver features on-demand, not at install
  * 主な機能だけをインストールすることができる
  * その後、必要になった時に、インストールすることができるようになる
  * 使うには Beta Program に参加することが必要
* (COMING SOON)Enable modules to run instantly, without install
  * ユーザはインストールなしに直ちにモジュールを起動することができる

## [Abound Android App Bundles](https://developer.android.com/guide/app-bundle/)

* アプリのコード、リソースを全て含んだ新しいアップロードフォーマットで、apk の生成と署名は延期される
* Dynamic Delivery
  * ユーザーのデバイスの構成ごとに最適化されたアプリが生成され、必要なコード、リソースのみがダウンロードされる
  * 複数の apk 管理は不要になり、ダウンロードサイズが削減できる
* dynamic feature modules
  * 初回のインストール時にダウンロードされるかを決定することができる
  * これらの modules のダウンロードをあとからリクエストできる
  * まだ、Beta で対応するにはリファクタリングが必要

### Dynamic Delivery with split APKs

* 21 以上から有効
* Google Play はが、ユーザーのデバイスに必要な単位で分割する
* Base APK
  * 他の module からアクセスする基本的な機能のコードとリソースなどを含む
  * ユーザがダウロードするさいに最初にインストールされる
* Configuration APKs
  * 特定の解像度、CPUアーキテクチャ、言語用のライブラリ、リソース
  * 大部分のアプリではリファクタリングは不要で、Google Play が生成する
* Dynamic feature APKs
  * 初回インストール時には不要な機能のコード、リソース
* 20 以下では、自動的に 1つの APK が提供されるので、20 以下を対象としていても利用できる

### Build, deploy, and upload Android App Bundles

* APK との違いはデバイスにインストールできないこと
* Android App Bundles は1つの生成物が生成され、アップロード後に、Google Play が APKs を生成し、Dynamic Delivery を通じてユーザーに提供される

## [Build, deploy, and upload Android App Bundles](https://developer.android.com/guide/app-bundle/build)

## [Download modules with the Play Core Library](https://developer.android.com/guide/app-bundle/playcore)

## [Configure your project for Dynamic Delivery](https://developer.android.com/guide/app-bundle/configure)

## [Test Android App Bundles with bundletool](https://developer.android.com/guide/app-bundle/test)
