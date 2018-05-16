# Actions

## [Getting started with App Actions (Google I/O '18)](https://www.youtube.com/watch?v=lu3L6DxUBRA)

### App Actions

* アプリへのエンゲージメントをあげる
* Content -> Action -> App
* right context, right moment
* Capabilities + Content
* Android P のみに制限されない
* Google Assistant でも動作する
* Developer preview はまだ

### Example

* Smart Text Selection
  * サーバなどには送らないのでセキュア
* ORDER_RIDE
* TAKE_COURSE

### 実装

* actions.xml
  * Semantic intents
    * larger catalog
    * actions.xml を通じて Android Intents と繋ぐ
      * URLs に変換する
      * URLs に変換することで Web コンテンツからの起動と共通にできる
  * Fulfillment
    * URL template models
      * 例：タクシーのアプリが遷移元、遷移先のパラメータをもった deep-link の API を持っている
    * Content-driven model
      * 例：料理アプリが schema.org/Recipe のような URL の website を持っている
* deep-links が利用される
* Usage logging
  * Firebase app indexing
  * myactivity.google.com
  * g.co/AppIndexing
* Enhance Actions
  * Rich UI(Slices)
    * Rich UI for Actios via Templates
    * アプリ外で表示される
    * Kitkat 以上の端末で有効
  * Conversational

* Test your Actions
  * Actions Test Tool
    * 該当の Action 用のパラメータを設定できる window が表示されるぽい

### 参考

* g.co/AppActions
* Slices and Conversation Actions
