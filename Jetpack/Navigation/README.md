# Navigation

[Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) に関する調査結果

## 対象バージョン

1.0.0-alpha06

## サンプル

| モジュール名 | 概要 | 作成バージョン |
| --- | --- | --- |
| app | デフォルト動作でない使い方のサンプルで不正な挙動となっている(#6) | 1.0.0-alpha02 |
| blankdestination | startDestination にレイアウトのない Fragment を利用した(#8)<br/>スプラッシュを想定 | 1.0.0-alpha02 |
| bottomnavigation | 通常の BottomNavigationView の動作(#12)<br/>replaceし直されるので、EditText の内容は復帰しない | 1.0.0-alpha02 |
| nestednavigation | conditional navigation の動作<br/>Profile 画面で未ログインだったら Login 画面へ遷移する。(#5) | 1.0.0-alpha02 |
| toolbar | Toolbar 単体で利用する Activity のサンプル | 1.0.0-alpha03 |
| safeargs | Safe Args Plugin のサンプル | 1.0.0-alpha03 |
| sharedelementtransition | [WIP] Fragment 間の遷移時に Shared Element Transition を利用するサンプル<br/>RecyclerView のセルからの遷移は上手くいかない | 1.0.0-alpha06 |
| bottomsheetdialog | BottomSheetDialogFragment 内の NavigationView と Navigation Graph を連動させる | 1.0.0-alpha06 |

### app

* Home, Dashboard, Notifications に切り替えで、ParentFragment へ遷移
  * タブ切り替えで、各タブのバックスタックは復帰せず、必ず ParentFragment へ遷移する
  * ただし、バックキーを使う場合は、バックスタックも保持した状態でタブの切り替えが行われる
    * TODO: 1.0.0-alpha06で動作が変わったのかを確認する
* ParentFragment は ChildFragment へ遷移する
  * ChildFragment のバックキーで前画面に遷移する
* ChildFragment はさらに ChildFragment へ遷移する
  * ChildFragment のボタンをタップで、ChildFragment へ遷移するが、タイトル、ボタン名が正しく反映されていない

## 更新履歴

### 1.0.0-alpha06

* https://developer.android.com/jetpack/docs/release-notes#september_20_2018

#### New Features

* Shared Element Transitions に対応(https://developer.android.com/topic/libraries/architecture/navigation/navigation-implementing#shared-element)
  * `FragmentNavigator.Extras`
  * `ActivityNavigator.Extras`
* BottomSheetDialogFragment に対応(https://issuetracker.google.com/issues/112158843)
  * 実際には `NavigationView` と Navigation Graph を連動させる

#### API Changes

* [Breaking Change] navigate にパラメータが追加
* `getGraph` が `NonNull` になった

#### Bug Fixes

* `NavigationUI.setupWithNavController` のリーク(https://issuetracker.google.com/issues/111961977)
* `onSaveState` が複数回呼ばれていた不具合が修正(https://issuetracker.google.com/issues/112627079)

#### Safe Args

* Navigation が親の Navigation を継承するようになった(https://issuetracker.google.com/issues/79871405)
* `toString` が実装された(https://issuetracker.google.com/issues/111843389)

### 1.0.0-alpha05

* https://developer.android.com/jetpack/docs/release-notes#august_10_2018

#### Bug Fixes

* バックキー遷移でバックスタックが不正になる(https://issuetracker.google.com/issues/111907708)
* Args の equals が不正(https://issuetracker.google.com/issues/111450897)
* git でブランチの切り替えをするとSafe Args でのビルドに失敗する(https://issuetracker.google.com/issues/109409713)
* ドットが含まれた ID だと Safe Args のビルドに失敗する(https://issuetracker.google.com/issues/111602491)
* Safe Args の nullability に関するエラーメッセージが変更
* `@Nullable` が正しく追加されるようになる

### 1.0.0-alpha04

#### API / Behavior Changes

* `NavHostFragment` が現在の Fragment を primary Fragment として扱う様になった(https://issuetracker.google.com/issues/111345778)
  * これで Fragment がネストされていても、バックキーが ChildFragmentManager に伝搬するようになった

#### Safe Args

* [Breaking Change] `app:type` は他のライブラリとコンフリクトしやすいので `app:argType` に変更された(https://issuetracker.google.com/issues/111110548)
* Android Studio に表示される Safe Args Plugin のエラーメッセージがクリック可能になった(https://issuetracker.google.com/issues/111534438)
* NonNull な引数に null を代入可能だった(https://issuetracker.google.com/issues/111451769)
* Safe Args Plugin で生成されるメソッドに `@NonNull` が指定されるようになった(https://issuetracker.google.com/issues/111455456)

#### Bug Fixes

* Deep Link からの起動で、バックキーで Toolbar が更新されない不具合(https://issuetracker.google.com/issues/111515685)

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
