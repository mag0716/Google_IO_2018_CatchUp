# sample memo

* MainActivity
  * デモの情報を定義する
    * タイトル
    * 概要
    * リソースファイル
    * 遷移先の Activity
      * デフォルトは DemoActivity
  * セルタップでデモで利用するリソースファイルとパスを表示するかの設定値を遷移先の Activity へ渡す
  * 遷移のための start は DemosAdapter で呼ばれる

## Basic Example (1/2)

* スワイプで左から右に動く View
* motion_01_basic.xml
  * `layoutDescription` に scene_01 を指定
* scene_01.xml
  * `constraintSetStart`, `constraintSetEnd` にレイアウトファイルを指定
  * `OnSwipe` を定義

## Basic Example (2/2)

* スワイプで左から右に動く View
  * Basic Example (1/2) との違いは、`constraintSetStart`, `constraintSetEnd` の指定方法
* motion_02_basic.xml
  * `layoutDescription` に scene_02 を指定
* scene_02.xml
  * `<ConstraintSet>` を直接定義
  * `constraintSetStart`, `constraintSetEnd` に `ConstraintSet` の ID を指定

## Custom Attribute

* Basic Example との違いは、モーションで背景色も変わる
* motion_03_custom_attribute.xml
  * `layoutDescription` に scene_03 を指定
* scene_03.xml
  * `<ConstraintSet>` を直接定義
  * `<Constraint>` 以下に `CustomAttribute>` を指定
    * `attributeName` に `BackgroundColor`
    * `customColorValue` を指定
