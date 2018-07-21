# Navigation

[Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) に関する調査結果

## 対象バージョン

1.0.0-alpha03

## サンプル

| モジュール名 | 概要 | 作成バージョン |
| --- | --- | --- |
| app | デフォルト動作でない使い方のサンプルで不正な挙動となっている(#6) | 1.0.0-alpha02 |
| blankdestination | startDestination にレイアウトのない Fragment を利用した(#8) | 1.0.0-alpha02 |
| bottomnavigation | 通常の BottomNavigationView の動作(#12) | 1.0.0-alpha02 |
| nestednavigation | conditional navigation の動作\nProfile 画面で未ログインだったら Login 画面へ遷移する。(#5) | 1.0.0-alpha02 |
| toolbar | Toolbar 単体で利用する Activity のサンプル | 1.0.0-alpha03 |
| safeargs | Safe Args Plugin のサンプル | 1.0.0-alpha03 |

### app

* Home, Dashboard, Notifications に切り替えで、ParentFragment へ遷移
  * タブ切り替えで、各タブのバックスタックは復帰せず、必ず ParentFragment へ遷移できる
* ParentFragment は ChildFragment へ遷移できる
  * ChildFragment のバックキーでアプリが終了する
* ChildFragment はさらに ChildFragment へ遷移できる
  * ChildFragment のボタンをタップしても遷移しない

## 更新履歴

### 1.0.0-alpha03

#### API / Behavior Changes

* `NavigationUI.setupWithNavController` で `Toolbar`, `CollapsingToolbarLayout` に対応
  * `ActionBar` として、`Toolbar` が利用されていないとタイトルなどが反映されなかったのを、`Toolbar`　単体として利用しても、反映される様になった
  * `DrawerLayout` も同時に渡すことができる
  * `OnNavigatedListener`
  * `ActionBarOnNavigatedListener` の実装が変更された
    * 直接、`NavController.OnNavigatedListener` を実装していたのを、`AbstractAppBarOnNavigatedListener` が新規作成され、その抽象クラスを継承する様になった
      * `setTitle`, `setNavigationIcon` が抽象化された
  * `AbstractAppBarOnNavigatedListener` を継承した、`ToolbarOnNavigatedListener`, `CollapsingToolbarOnNavigatedListener` が追加され、`setupWithNavController` で利用される様になった
* バックスタックが空だったりバックスタックにない destination の ID が渡されたりした場合は、`popBackStack` が false を返却する様になった
  * `FragmentNavigator#popBackStack` の実装が大きく変わった
* `onSaveInstanceState` 後は、`FragmentNavigator` は遷移処理を無視する様になった
  * `FragmentNavigator#navigate` で遷移処理前に、`FragmentManager#isStateSaved` をチェックする様になった

#### Safe Args

* [Breaking Change] アルファベットか数字以外はキャメルケースに変換される様になった
* [Breaking Change] 引数はデフォルトは NonNull だが、`app:nullable` で null を渡せる様になった
* `app:type` に `long` が追加された
* `Parcelable` な引数がサポートされた。デフォルト値は `@null` のみ
* `NavDirections` に `equals` と `hashCode` が実装された
* ライブラリプロジェクトに適用できる様になった
* feature プロジェクトに適用できる様になった

#### Bug Fixes
