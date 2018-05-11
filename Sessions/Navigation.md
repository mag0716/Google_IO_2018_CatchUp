# Navigation

[Android Jetpack: manage UI navigation with Navigation Controller (Google I/O '18)
](https://www.youtube.com/watch?v=8GCXtCjtg40)

## Chalenge Navigation

* Fragment Transactions
* Testing
* Deep Links
* Passing Arguments
* Up and Back
* Errorprone Boilerplate

## Demo

* ゲーム終了画面からゲーム開始画面に戻りたい時などは Pop Behavior を指定する

## The role of Activity

* An entrypoint to your app
* Activity は Bottom Navigation, Navigation Drawer については管理するが、コンテンツについては NavHost に委譲する

## navigation-ui

* BottomNavigationView と連携させるには、Navigation Graph の ID と同じ ID を使った menu を作成して、 setupWithNavController を呼び出す必要がある

## Navigating With NavController

## Safe Args Gradle Plugin

* 今までの Bundle を生成する方法だと、必要なデータが足りなかったりすると、実行時にエラーになる

## Deep Linking

### Explicit

* Notifications
* App Shortcuts
* App Widgets
* Actions
* Slices

### Implicit

* Web URLs
* Custom Scheme URIs

## Testing

* Testing Strategy
  * Destination to be tested
* テストコード
  * Espresso
  * Navigator#addOnNavigatorNavigatedListener 内で assert +  CountDownLatch を使って、正常に遷移したかを確認している
