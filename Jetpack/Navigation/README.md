# Navigation

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

## Codelabs

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

## サンプル実装で気づいた点

* Activity は destination に設定できるが「Set Start Destination」ができない
* NavController.OnNavigatedListener を NavController に add, remove できる
  * [疑問点]BottomNavigation では、タブ切り替えで HomeFragment の onNavigated が走る
* Action に Launch Options を設定できる
  * Single Top
  * [疑問点]Document
  * Clear Task
* action がない場合は `java.lang.IllegalArgumentException: navigation destination com.github.mag0716.navigationsample:id/action_child is unknown to this NavController` になる
* [疑問点] NavControl#navigate で別 Fragment へ遷移させると遷移元は onDestroy まで走る
  * defaultNavHost 未指定時の動作。true にすれば、onDestroyView までになり、バックキーでスタックされた画面へ戻れるようになる
* `override fun onSupportNavigateUp() = container.navController.navigateUp()` で Up Key と Navigation Graph を連動できる
  * popUpTo が指定されていなかったらバックキーと同じ
  * バックキーも popUpTo で指定された destination に遷移する
  * Navigation を利用すると、アプリ終了以外はバックキー = Up キー
  * [疑問点] 会員登録などで前の画面に戻したくない場合には使えるが、Up Key をいままで通りの遷移にするにはどうすればいいのか？
* [疑問点] Up キーと Navigation Drawer は共存できない？
  * Up キーは表示されるがタップしても Navigation Drawer が表示されてしまう
  * NavigationView を無効化すると、開かなくなるが Up キーも動作しない
  * onSupportNavigationUp よりも onOptionsItemSelected が優先
  * NavigationUI.setupActionBarWithNavController
    * stack に積まれると自動的に、Navigation Drawer が無効になり、Up キーに変わる
* Activity 間の遷移に Action は使えない
  * Destination が Activity だと、arguments と deep link しか定義できない
  * Global Action は定義できるが、Activity から NavController を取得する方法がなさそうなので、Activity 間の遷移には利用できない
  * deep link での遷移には利用できるが、Fragment のように Arguments から placeholder で指定した key で文字列を取得することはできない
* Destination は arguments か action を 1つ以上定義するか、他の Destination で利用されている必要がある
  * ` Destination with arguments or action mush have either name either id attributes`
    * [はまった] navigation タグに id を指定する必要がある(https://issuetracker.google.com/issues/79627172)

## その他のサンプルの確認

### [Android Architecture Components Navigation Basic Sample](https://github.com/googlesamples/android-architecture-components/tree/master/NavigationBasicSample)

* MainActivity
  * TitleScreen(Start Destination)
    * register
    * leaderboard
  * Register
    * match
  * Leaderboard
    * userProfile
  * Match
    * in_game
  * UserProfile
    * deep link
    * arguments
  * InGame
    * resultsWinner
    * gameOver
  * Winner
    * leaderboard
    * match
  * GameOver
    * match

上記の画面構成で 1 Activity、複数 Fragment
起動直後は必ず TitleScreen から始まり、順番に遷移していく
Winner, GameOver で popUpTo で match が指定されている他は特殊なことは行っておらず Codelab と同じで Fragment の遷移のための Navigation Graph
Leaderboard から UserProfile の遷移でデータ受け渡しのために Bundle を生成している

## 疑問点

* Navigation Editor の Create Destination で候補に上がるのは何？
* 遷移先での結果が必要な場合(startActivityForResult)ではどうする？
  * 1.0.0-alpha1 では対応していないので、今まで通り実装する必要がある
  * https://issuetracker.google.com/issues/79672220
* 遷移時にデータを渡す場合はどうする？
  * NavigationController#navigate に Bundle を渡せる
  * <arguments> タグでも渡せる
* サンプルでは Home タブの Fragment はタブ切り替えで破棄される。残す場合はどう設定すればいいのか？
* Activity 間の遷移
* Fragment から ChildFragment への遷移
* Navigation Editor の The Destinations list に Host というのがあるがこれは何？
  * NavHostsFragment が定義されているリソース
* <argment> の type には何が指定できる？
  * string
  * integer
  * reference
