* [The Navigation Architecture Component](https://developer.android.com/topic/libraries/architecture/navigation)
* [Navigation Codelab](https://codelabs.developers.google.com/codelabs/android-navigation)
* [サンプル](https://github.com/googlesamples/android-architecture-components/tree/master/NavigationBasicSample)

## 概要

アプリ内の遷移の実装を容易にする

### Principles of Navigation

https://developer.android.com/topic/libraries/architecture/navigation/navigation-principles

* アプリは固定の destination を持っていること
  * 1度きりの設定やログイン画面などを持っている場合は対象外としてよい
* スタックは navigation state として利用されること
  * スタックは LIFO で構成されるべき
* Up ボタンでアプリを終了してはならない
* Up ボタンとバックキーは同じ動作になる
  * バックキーでアプリが終了されない場合、Up ボタンとバックキーは同じ動作をすること
  * [疑問点] Up ボタンとバックキーが異なる動作になることはありえるはず
* Deepリンクでも通常の起動と同じスタックとなること
  * Deepリンク起動時に残っていたスタックは削除か置き換えられる

### Implement navigation with the Navigation Architecture Component

https://developer.android.com/topic/libraries/architecture/navigation/navigation-implementing

* デフォルトでは Navigation Architecuter Component は Activity, Fragment のサポートしている
  * それ以外の destination についてもカスタマイズできる
* Navigation Graph は「actions」と呼ばれる destination 同士を繋ぐコネクションを持っている

#### setup

* build.gradle に定義追加
* res/navigation 以下に xml ファイルを追加

#### Navigation Editor

* The Desitinations list：destinations の一覧
* The Graph Editor：Navigation Graph を UI 操作で編集できる
* The Attributes Editor：Attributes の編集

#### Identify destinatinos

* Activity と複数の Fragment のために設計されている
* Activity 毎に Navigation Graph を作成する

#### Connect destinations

* app:destination には Fragment or Activity が指定できる

#### Modify an activity to host navigation

* NavHost インタフェースを Activity のレイアウトに追加する必要がある
  * デフォルトで用意されている NavHostFragment は NavHost を implements している
* バックボタンの動作は AppCompatActivity#onSupportNavigateUp を override することで上書きできる

#### Tie destinations to UI widgets

* Up ボタン：NavController#navigateUp
* バックボタン：NavController#popBackStack
* ボタン用に Navigation#createNavigateOnClickListener も利用できる

##### Tie destinations to menu-drive UI components

* NavigationView#setupWithNavController：メニューでの遷移を紐づける

#### Pass data between destinations

* 2つの方法がある
  * Bundle
  * safeargs Gradle plugin

#### Pass data between destinations in a type-safe way

* XXXDirections#confirmAction
* 引数が渡されると XXXArgs が生成される

#### Group destinations into a nested navigation graph

* nested graph はログインフローなどを分けるのに便利
* Navigation Editor で destinations を複数選択し、nested graph に変更できる
* nested graph 内の destinatinos にも navigate で遷移できる

#### Create a deep link for a destination

##### Assign a deep link to a destination

* Auto Verify
  * https://developer.android.com/training/app-links/verify-site-associations

##### Add an intent filter for a deep link

* Android Studio 3.2 以上から、<nav-graph> が利用できる

#### Create a transition between destinations

### Migrate to the Navigation Architecture Component

* Activity 毎にフォーカスして Navigation Graph を作成する
* Activity 毎に完了したら、startActivity の代わりに Activity 間の遷移を Navigation Graph で作成する
* Activity をまとめられるなら、直接 Navigation Graph を組み合わせる

### Add support for new destination types

### Implement conditional navigation

* ログインなどの条件付きの遷移がある場合
  * destinatinos を分ける
  * nested graph
  * ログインが終わったら popBackStack を呼び出す必要がある

### Identify a common destination for several UI elements

* キャンセルボタンなどで同じ画面に遷移させる場合などでは、Global Action を作成するとよい
* Graph Editor で destination を選択中に右クリックで Add Action -> Global で作成できる
