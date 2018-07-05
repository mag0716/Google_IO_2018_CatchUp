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

## CoordinatorLayout Example (1/3)

* `CoordinatorLayout` と連携して、`TextView` を回転
* motion_09_coordinatorlayout.xml
  * `AppBarLayout` 以下に motion_09_coordinatorlayout_header.xml を include
* motion_09_coorinatorlayout_header.xml
  * `MotionLayout` のサブクラスである `CollapsibleToolbar` を利用
* scene_09.xml
  * `TextView` を `CoordinatorLayout` の移動量を元に回転、背景色のアルファ値を変更
* `CollapsibleToolbar`
  * `MotionLayout` を継承
  * `AppBarLayout.OnOffsetChangedListener` を実装し、`onOffsetChanged` で、オフセット値を `setProgress` で渡す

## CoordinatorLayout Example (2/3)

* CoordinatorLayout Example (1/3) の動作 + 画面内に移動する `TextView` を追加
* motion_10_coordinatorlayout.xml
  * `AppBarLayout` 以下に motion_10_coordinatorlayout_header.xml を include
* motion_10_coordinatorlayout_header.xml
  * `MotionLayout` のサブクラスである `CollapsibleToolbar` を利用
* scene_10_header.xml
  * scene_09.xml の `Constraint` に加えて
    * `TextView` を画面外から画面内に移動させる
    * 背景画像の拡大率、表示位置を変更

## CoordinatorLayout Example (3/3)

* `CoordinatorLayout` と連携して、アイコンとテキストを画面中央から画面端に移動する
* motion_11_coordinatorlayout.xml
  * `AppBarLayout` 以下に motion_11_coordinatorlayout_header.xml を include
* motion_11_coordinatorlayout_header.xml
  * `MotionLayout` のサブクラスである `CollapsibleToolbar` を利用
  * レイアウト調整用のダミーの `TextView` が配置されている
* scene_11_header.xml

## DrawerLayout Example (1/2)

* `DrawerLayout` と連携して、コンテンツ部分を `DrawerLayout` と重ならない様に移動
* motion_12_drawerlayout.xml
  * `DrawerLayout` 以下に、motion_12_drawer_content, motion_12_drawerlayout_menu を include
* motion_12_drawerlayout_content.xml
  * `MotionLayout` のサブクラスである `DrawerContent` を利用
* motion_12_drawerlayout_menu.xml
  * `DrawerLayout` 内に表示する View を定義
* scene_12_content.xml
  * `DrawerLayout` にコンテンツが重ならない様に、拡大率、マージンを変更
* `DrawerContent`
  * `MotionLayout` のサブクラス
  * `DrawerLayout.DrawerListener` を実装
  * `onDrawerSlide` でオフセット値を `setProgress` で渡す

## DrawerLayout Example (2/2)

* DrawerLayout Example (1/2) の動作 + メニューもモーションを追加
* motion_13_drawerlayout.xml
  * `DrawerLayout` 以下に、motion_12_drawer_content, motion_13_drawerlayout_menu を include
* motion_13_drawerlayout_menu.xml
  * メニューの View を回転、移動

## Side Panel Example

* `DrawerLayout` のようなメニュー表示を、`MotionLayout` のみで実装
* motion_14_side_panel.xml
  * `MockView` を利用
* scene_14.xml
  * `MockView` のサイズを変更
  * コンテンツ部分の拡大率を変更

## Parallax Example

* 4枚の画像をパララックスに移動
* motion_15_parallax.xml
  * `MotionLayout` のサブクラスである `ViewpagerHeader` を利用
* scene_15.xml
  * 各画像の位置を変更。マージンを変更することでパララックスを実現している
* ViewpagerHeader
  * `MotionLayout` を継承
  * `ViewPager.OnPageChangeListener` を実装
  * `onPageScrolled` で現在のページ数とオフセット値を `setProgress` で渡す

## ViewPager Example

* `ViewPager` と連携して、Parallax Example の動作を実現
* motion_16_viewpager.xml
  * motion_15_parallax.xml を include
  * `TabLayout`, `ViewPager` を指定

## ViewPager Lottie Example

* `ViewPager` と連携して、Lottie のアニメーションを動作させる
* motion_23_viewpager.xml
  * `motion_23_parallax.xml` を include
  * `TabLayout`, `ViewPager` の指定は、ViewPager Example と同じ
* motion_23_parallax.xml
  * `ViewpagerHeader` を利用
  * `LottieAnimationView` を定義
* scene_23.xml
  * `progress` を 0 から 1 へ変更
    * `LottieAnimationView` は `setProgress` が定義されている

## Complex Motion Example (1/4)

* CoordinatorLayout Example (2/3) を `CoordinatorLayout` を利用せずに実現
* motion_17_coordination.xml
  * `motion_17_coordinator_header`, `content_scrolling` を include
* motion_17_coordination_header.xml
  * scene_17_header.xml を指定
  * `BoundsImageView` を背景画像の表示に利用
* scene_17.xml
  * ヘッダー部分の領域の高さを変更
* scene_17_header.xml
  * ヘッダー内の View の位置やサイズを変更
* `BoundsImageView`
  * `MockView` の様に対角線を描画

## Complex Motion Example (2/4)

* Complex Motion Example (1/4) の動作 + FAB の表示状態変更
* motion_18_coordination.xml
  * `motion_17_coordinator_header`, `content_scrolling` を include
* scene_18.xml
  * ヘッダー部分に加えて、FAB の位置とアルファ値を変更

## Complex Motion Example (3/4)

* Complex Motion Example (2/4) と同じ動作
* motion_19_coordination.xml
  * `motion_19_coordinator_header`, `content_scrolling` を include
* motion_19_coordination_header.xml
  * motion_17_coordination_header.xml から `Guideline` を削除
* scene_19.xml
  * scene_18.xml と同じ
* scene_19_header.xml
  * scene_17_header.xml とほぼ同じ。`Guideline` への制約がないだけ

## Complex Motion Example (4/4)

* 画面遷移直後にアニメーションを開始 + スワイプで View の位置を変更
* motion_20_reveal.xml
  * `ExampleFlyingBounceHelper` を利用
* scene_20.xml
* `ExampleFlyingBounceHelper`
  * `ConstraintHelper` を継承
  * `updatePreLayout` をオーバライドし、translationX を -2000 から 0 へアニメーションする、`ObjectAnimator` を開始する
  * `KeyAttribute`, `KeyPosition` を指定し、矢印の画像を target に

## Fragment Transition Example (1/2)

* Fragment 表示領域のスワイプで Fragment の切り替え
* main_activity.xml
  * `MotionLayout` を利用
    * `TouchFrameLayout` を指定
* motion_21_second_fragment.xml
  * `FadeIn`, `FadeOut` を利用
* main_scene.xml
  * 背景色を変更
* `TouchFrameLayout`
  * `FrameLayout` を継承
  * `NestedScrollingParent2` を実装
    * オーバーライドしたメソッドでは、親の `NestedScrollingParent2` を呼び出すだけ
* `FragmentExampleActivity`
  * `MotionLayout.TransitionListener` を実装
    * 現在の progress によって、表示する Fragment を切り替える
    * Fragment の切り替えには、replace を利用
    * Fragment 切り替え時に `setCustomAnimations` でアニメーションを指定
  * 画面遷移時に `MainFragment` を表示
* `FadeIn`
  * `MotionLayout` を継承
  * `setProgress` をオーバーライドし、アルファ値を設定する
* `FadeOut`
  * `MotionLayout` を継承
  * `setProgress` をオーバーライドし、アルファ値を設定する

## Fragment Transition Example (2/2)

* main_activity.xml
* `FragmentExample2Activity`
  * `FragmentExampleActivity` とほぼ同じ
    * 遷移する Fragment が SecondFragment か ListFragment の違い
* `ListFragment`
  * `TouchFrameLayout` を利用し、セルが一番上にあるときに下にスワイプしたら閉じれる様にしている
* list_scene.xml
  * `ConstraintSet` は空

## 追加調査結果

### KeyCycle

* アニメーション中にレイアウトのプロパティの波形をコントロールする
* target：対象の View の ID
* framePosition：フレームの位置(0:開始、100:終了)
* waveShape：生成のための波形の形
  * sin, square, triangle, sawtooth, reverseSawtooth, cos, bounce
* wavePeriod：framePosition 周辺でのループ回数
* waveOffset：属性値に追加するオフセット値
  * [疑問]単位が不明
* trantisionPathRotate：View のパスを基準にして回転に適用される cycle
  * [疑問]何に利用するのかが不明
* progress：この View 上で setProgress を呼び出す(ネストされている ConstraintLayout を呼び出すのに利用される)

## 疑問点

* `ImageFilterView` の saturation, contrast, warmth の変化でどう変わるか
* Keyframe の `pathRelative`, `deltaRelative`, `screenRelative` の違い
* `<ConstraintSet>` の定義位置が、`<MotionScene>` 直下、`<Transition>` 直下の定義
* `<KeyCycle>`
  * sin, square, triangle, sawtooth, reverseSawtooth, cos, bounce
* CoordinatorLayout Example (3/3) はダミーの `TextView` は本当にいるの？
* どういう時に `MockView` を使うのか？
  * ラベルと対角線を表示できる View で、レイアウト構築中に一時的な View として利用する
* `Constrain` に指定する属性値は start, end で同じでも、`MotionScene` 以下に指定する必要があるのか？
  * 定義しないとだめ
  * 特に、layout_width, layout_height は指定しないと表示もされないので注意
* Fragment Transition Example (1/2) の `FadeIn`, `FadeOut` はどこから `setProgress` が呼ばれているのか？
  * `MotionHelper#setProgress` は子供の `MotionHelper` に対しても、`setProgress` を呼び出している
* `MotionHelper` の使い道
