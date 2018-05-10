# Navigation

* [The Navigation Architecture Component](https://developer.android.com/topic/libraries/architecture/navigation)
* [Navigation Codelab](https://codelabs.developers.google.com/codelabs/android-navigation)

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

*

## サンプル

* res/navigation 以下に xml ファイルを配置する
* destination：ユーザが遷移可能な場所で通常は Activity や Fragment
* Activity に NavHostFragment を持たせる必要がある
  * Navigation Graph を通じて Fragment の遷移を切り替える
  * app:defaultNavHost="true" を追加してバックボタンで遷移できるようにする
* NavigationUI
  * Action Bar, Navigation Drawer, Bottom Navigation Bar とのやりとりを簡単にする
* NavigationController
  * ボタンクリックなどのイベントで NavigationController#navigate を呼び出す
* デフォルトでは遷移時のアニメーションはない
  * NavOptions を NavigationController#navigate に渡せばアニメーションを変更できる
* Actions
  * Actions を利用すると、Navigation Graph が全ての遷移を管理するリソースになる
  * Action を使うと遷移を抽象的なレベルで提供でき、遷移処理の実装が共通化できる
    * 例：Navigation Graph では全て next_action で定義しているが遷移元によって遷移先を変えている
* menu, drawers, bottom navigation との連携
  * NavigationUI
  * navigation-ui-ktx
  * menu
    * NavigationUI#onNavDestinationSelected
    * menu の id が Navigation Graph の ID と一致している必要がある
* Safe Args
  * safe args と呼ばれる Gradle Plugin を持っている
  * destinations と actions に指定する type-safe な引数を生成する
  * <argument> タグを指定すると XXXArgs が生成される
* Deep Linking
  * URL や Notification からアプリの遷移の途中に飛ぶ
  * NavDeepLinkBuilder
    * デフォルトでは lancher Activity が起動される
    * setComponentName で別の Activity を設定できる
  * バックスタックはどうやって決定されるのか？
    * Navigation Graph から決定される
    * Navigation Graph が複数存在する場合はそれぞれの app:startDestination がバックスタックを決定する
  * NavController#createDeepLlink でも生成可能
* Web Link
  * <deepLink> タグを指定して Web Link からの起動を可能にする
  * app:uri には、{xxx} で指定できる
  * ワイルドカード指定も可能
  * NavController は自動的に ACTION_VIEW をハンドリングする
  * AndroidManifest.xml には <nav-graph> タグを指定する

## 疑問点

* Navigation Editor の Create Destination で候補に上がるのは何？
* 遷移先での結果が必要な場合(startActivityForResult)ではどうする？
* 遷移時にデータを渡す場合はどうする？
  * NavigationController#navigate に Bundle を渡せる
  * <arguments> タグでも渡せる
* サンプルでは Home タブの Fragment はタブ切り替えで破棄される。残す場合はどう設定すればいいのか？
