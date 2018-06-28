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
  * `<Constraint>` 以下に `<CustomAttribute>` を指定
    * `attributeName` に `BackgroundColor`
    * `customColorValue` を指定

## ImageFilterView (1/2)

* 2枚の画像をフェードしながら切り替える
* motion_04_imagefilter.xml
  * `ImageFilterView` を配置
  * `src`, `altSrc` に画像を指定
* scene_04.xml
  * `<CustomAttribute>`
    * `attributeName` に `Crossfade` を指定
    * `customFloatValue` で 0 から 1 へ遷移

## ImageFilterView (2/2)

* 1枚の画像の色を変更する
* motion_05_imagefilter.xml
  * `ImageFilterView` を配置
* scene_05.xml
  * `<CustomAttribute>`
    * `attributeName` に `Saturation` を指定
    * `customFloatValue` で 1 から 0 へ遷移

## Keyframe Position (1/3)

* Custom Attribute の動作に加えて、遷移が 50% の位置になったら、y 座標を -25% 分だけずらしてアニメーションする
* motion_06_keyframe.xml
* scene_06.xml
  * `<Transition>` 以下に `<KeyFrameSet>` を定義
  * `<KeyFrameSet>` 以下に `<KeyPosition>` を指定
    * `type` に `pathRelative`
    * `percentY` に -0.25 を指定
    * `framePosition` に 50 を指定

## Keyframe Interpolation (2/3)

* Keyframe Position (1/3) の動作に加えて、拡大縮小と回転を追加
* motion_07_keyframe.xml
* scene_07.xml
  * `<Transition>` 以下に `<ConstraintsSet>`, `<KeyFrameSet>` を定義している
    * `<KeyAttribute>`
      * `scaleX`, `scaleY` に 2
      * `rotation` に -45
      * `framePosition` に 50
    * `<KeyPosition>`
      * `type` に `pathRelative`
      * `percentY` に -0.3
      * `framePosition` に 50

## Keyframe Cycle (3/3)

* Keyframe Position (1/3) の動作を sin グラフのパスで動作
* motion_08_cycle.xml
* scene_08.xml
  * `<KeyFrameSet>` 以下に `<KeyCycle>` を定義
    * `waveShape` に `sin`
    * `wavePeriod` に 0
    * `translationY` に 50dp
    * `framePosition` に 0, 50, 100
    * `wavePeriod` に 0, 1, 0

## 疑問点

* `ImageFilterView` の saturation, contrast, warmth の変化でどう変わるか
* Keyframe の `pathRelative`, `deltaRelative`, `screenRelative` の違い
* `<ConstraintSet>` の定義位置が、`<MotionScene>` 直下、`<Transition>` 直下の定義
* `<KeyCycle>`
  * sin, square, triangle, sawtooth, reverseSawtooth, cos, bounce
