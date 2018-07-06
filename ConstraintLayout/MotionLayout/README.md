# README

## app

* https://github.com/googlesamples/android-ConstraintLayoutExamples と同じサンプルを1から実装

## keycycle

* `KeyCycle` の `waveShape` の違いを確認するサンプル

| waveShape | 結果 |
| - | - |
| sin | ![](./screenshot/KeyCycle/sin.png) |
| square | ![](./screenshot/KeyCycle/square.png) |
| triangle | ![](./screenshot/KeyCycle/triangle.png) |
| sawtooth | ![](./screenshot/KeyCycle/sawtooth.png) |
| reverseSawtooth | ![](./screenshot/KeyCycle/reverseSawtooth.png) |
| cos | ![](./screenshot/KeyCycle/cos.png) |
| bounce | ![](./screenshot/KeyCycle/bounce.png) |

## imagefilterview

* `ImageFilterView` のサンプル

| 概要 | 結果 |
| - | - |
| オリジナル | ![](./screenshot/ImageFilterView/original.png) |
| saturation 0 | ![](./screenshot/ImageFilterView/saturation_0.png) |
| saturation 2 | ![](./screenshot/ImageFilterView/saturation_2.png) |
| contrast 0 | ![](./screenshot/ImageFilterView/contrast_0.png) |
| contrast 2 | ![](./screenshot/ImageFilterView/contrast_2.png) |
| warmth 0.5 | ![](./screenshot/ImageFilterView/warmth_0.5.png) |
| warmth 2 | ![](./screenshot/ImageFilterView/warmth_2.png) |

## constraintsetintransition

* app と同じ動作
* `ConstraintSet` の定義位置を app とは異なり、`Transition` 以下に定義
* https://developer.android.com/reference/android/support/constraint/motion/MotionLayout#transition には、`ConstraintSet` は記載がないが、定義できるらしい

## keyframe

* `KeyPosition` の `type` の違いを確認するサンプル
* `parentRelative`
  * 親のレイアウトからの相対位置を指定する
    * ex. `percentY` を指定する場合
      * 0:親レイアウトの上端
      * 0.5:親レイアウトの中央
      * 1:親レイアウトの下端
* `pathRelative`
  * パスからの相対位置を指定する
    * ex. 垂直位置にセンタリングされた位置をアニメーションし、`percentY` を指定する場合
      * -1:親レイアウトの上端
      * 1:親レイアウトの下端
* `deltaRelative`
  * ex. 垂直位置にセンタリングされた位置を左から右へアニメーションし、`percentX` を指定する場合(y座標には移動しないので `percentY` は効果なし)
    * 0:開始位置
    * 1:終了位置
    * `framePosition` が 50 時点で、中央以外の座標にしておきたい場合に利用できる(interpolationがlinearでもアニメーション速度を一定でなくできる)
