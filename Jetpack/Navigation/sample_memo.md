# サンプル

サンプルプロジェクトの調査メモ

## [Codelab](https://codelabs.developers.google.com/codelabs/android-navigation)

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
  * Fragment -> Activity の遷移には Action を利用できる
    * NavHostFragment から NavController を取得し、Action が定義されている Fragment を表示中であれば、該当の Action が Fragment からでも利用できる
* Destination は arguments か action を 1つ以上定義するか、他の Destination で利用されている必要がある
  * ` Destination with arguments or action mush have either name either id attributes`
    * [はまった] navigation タグに id を指定する必要がある(https://issuetracker.google.com/issues/79627172)
* Menu と Navigation Graph との連動は NavigationUI.onNavDestinationSelected を使う
  * 遷移時にフェードっぽいアニメーションがはいる
* destination に Nested Navigation Graph の id も指定できる
  * [疑問点] onResume とかで未ログインだったらログイン画面へ遷移を試したが、`java.lang.IllegalArgumentException: navigation destination com.github.mag0716.nestednavigation:id/action_login is unknown to this NavController` になる
    * ボタンタップ契機であれば遷移できる
* Fragment に NaHostFragment を持たせて別の Navigation Graph を指定して、NavHostFragment#findNavController を実行すると、Activity 側の Navigation Graph が取得される
  * [疑問点] NavController 取得の内部実装(なぜ、Activity 側の NavController が取得されるのか？)
    * `container.findNavController` を実行すると、ParentFragment に対して、findNavController を行い、親の Fragment を順番に調べていくので、ParentFragment が管理されている Activity 側の NavHostFragment が取得されるため

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
  * #6
* Activity 間の遷移
  * Activity からの遷移はできない、Activity への遷移は可能
* Fragment から ChildFragment への遷移
  * NavHostFragment が ChildFragmentManager を扱うので、バックキーでの挙動がおかしくなるので難しい
* Navigation Editor の The Destinations list に Host というのがあるがこれは何？
  * NavHostsFragment が定義されているリソース
* <argment> の type には何が指定できる？
  * string
  * integer
  * reference
