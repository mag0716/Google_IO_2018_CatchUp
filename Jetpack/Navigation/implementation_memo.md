# 内部実装メモ

## androidx.navigation

https://developer.android.com/reference/androidx/navigation/package-summary

### NavGraph

通常は、navigation リソースから生成される Navigation Graph
NavGraph は複数の NavDestination を保持している

### NavHost

NavController の管理し特定の Destinations のサポートを提供する
ex. NavHostFragment

以下を行う必要がある

* NavController の状態の保存・復帰を扱う
* Navigation#setViewNavController を呼び出す

### NavController

Navigation Graph を操作するための main entry point
navigate, popBackStack, navigateUp が定義されている

NavGraph を持っている

### Navigator

実際の画面遷移の実装を持っている
ex. ActivityNavigator, FragmentNavigator

自身のスタックを管理する必要がある
Navigator.Name アノテーションが追加されている必要がある
ex. `<declare-styleable name="ActivityNavigator">`

## androidx.navigation.fragment

https://developer.android.com/reference/androidx/navigation/fragment/package-summary

### FragmentNavigator

* Navigator.Name("fragment") が指定されている
* コンストラクタで渡された FragmentManager を通じて Fragment を切り替えしている
* popBackStack は FragmentManager#popBackStackImmediate
* navigate
  * Destination から Fragment を生成
  * コンストラクタで受け取った FragmentManager を使い
  * replace で切り替えている
  * StateFragment を新しい Fragment のものに置き換える
  * 初めの遷移や task のクリアでなければ、FragmentTransition#addToBackStack を呼び出す
    * tag は FragmentNavigator に定義された getBackStackName
  * FragmentTransaction の commit, executePendingTransitions を呼び出す

### FragmentNavigator.Destination

* Fragment の Class を保持する
* コンストラクタで渡された NavigatorProvider を通じて Class を取得する
* onInflate で Navigation Graph に `app:name` で定義された Fragment の名前を利用している
* Fragment の生成はリフレクション

### NavHostFragment

* onCreate で getChildFragmentManager を利用して FragmentNavigator を生成している
* onCreateView で FrameLayout を生成している
* onViewCreated で rootView に NavController をセットしている
  * Navigation#setViewNavController
* onInflate 時に `app:navGraph` に指定されたリソースを NavController にセットしている
* getFragmentManager で FragmentManager を取得し、自身を setPrimaryNavigationFragment にしている
* findNavController
  * 親の Fragment を辿っていき NavHostFragment だったら返す
  * なければ、Navigation#findNavController で rootView にセットされた NavController を返却する

## androidx.navigation.ui

### NavigationUI

* setupWithNavController で BottomNavigationView と連動させる
  * NavigationUI#onNavDestinationSelected が利用される
    * タブタップでは、popUpTo がセットされた状態で navigate される
      * replace が利用され、Fragment のみで保持していたデータは復帰しないので注意
    * アニメーションもデフォルトのものが利用される
