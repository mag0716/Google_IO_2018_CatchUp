# reference memo

## [Introduction to MotionLayout (part I)](https://medium.com/@camaelon/introduction-to-motionlayout-part-i-29208674b10d)

* MotionLayout は ConstraintLayout 2.0.0 で有効になったクラスで、モーションとウィジェットのアニメーションを手助けする
* [Issue Tracker](https://issuetracker.google.com/issues?q=componentid:323867)

### Why MotionLayout?

* Android ではアニメーションのためにいくつかの方法を提供している
  * Animated Vector Drawable
  * Property Animation framework
  * LayoutTransition animations
  * Layout transitions with TransitionManager
  * CoordinatorLayout
* これらの既存の方法と MotionLayout の違いを記載する
* MotionLayout は名前通り Layout で要素の位置を指定させる。実際には ConstraintLayout のサブクラス
* MotionLayout はレイアウトの遷移と複雑なモーション操作とのギャップを埋めるために作成された
  * Property Animation framework, TransitionManager, CoordinatorLayout がミックスされたようなクラス
  * 2つのレイアウトの遷移を記述することができ、さらにアニメーションさせることもできる
  * さらに、CoordinatorLayout のようなシークでの遷移をサポートしている
  * タッチ操作やキーフレームもサポートされているので簡単にカスタマイズできる
* MotionLayout は完全に宣言的に記述できる
  * 複雑な遷移も xml のみで記述できる
  * もし、コードで描く場合は Property Animation framework がすでに提供されている
* 宣言的に記述することで、Android Studio の GUI で提供することが可能になる  
  * まだ、提供されていない、ConstraintLayout 2.0 が stable / beta になった時に提供される予定
* API level 18 からサポートしており、95% 以上の端末で利用できる

#### Limitations

* MotionLayout は直接の子供である View に対してのみ有効

### When to use it?

* ユーザ操作で UI 要素を移動、リサイズなどが必要な場合に利用する
* ユーザが何をすべきかを理解するのに役立つモーションを提供することが重要だと認識することが重要
* gif や 動画などすでに定義されたアニメーションについては基本は扱うことはしない
  * MotionLayout に含めることは可能

### Adding MotionLayout to your project

* `com.android.support.constraint:constraint-layout`

### Using MotionLayout

* MotionLayout は ConstraintLayout のサブクラスなので、ConstraintLayout の代わりに MotionLayout に置き換えて利用する
* xml 上でのConstraintLayout との主な違いは、実際のレイアウトを含める必要がないということ
  * ？`The main difference between ConstraintLayout and MotionLayout at the XML level is that the actual description of what MotionLayout will do is not necessarily contained in the layout file.`
* 一般的には、レイアウトと MotionScene の情報は分割して定義する

### ConstraintSets

* ConstraintSets を使ったことがなければ https://www.youtube.com/watch?v=OHcfs6rStRo&feature=youtu.be が参考になる
* ConstraintSets は全てのレイアウトの位置のルールをまとめる
  * さらに、レイアウトファイルで複数の ConstraintSets を利用することもできる
* TransitionManager と組み合わせることで、動画にあるようなアニメーションを簡単に作成することができる
* MotionLayout はこの概念を改善、拡張している

### MotionScene

* 一般的なレイアウトファイルとは別に、MotionScene 用の xml ファイルを用意する
* MotionScene のファイルはアニメーションのために以下の指定が必要
  * ConstraintSets
  * ConstraintSets 間の遷移
  * キーフレームやタッチ操作のハンドリングなど

### Example 01 : reference existing layouts

* ConstraintLayout で2つの ConstraintSets を作成する
  * 1つ目は初期位置を表すレイアウトで、2つ目は最後の位置を表すレイアウト
* ConstraintLayout だと、2つの レイアウトで ConstraintSets を初期化し、それらを適用できる
  * このアプローチだと、アニメーション途中で中断できない。また、ユーザの入力で遷移をハンドリングすることはできない
* MotionLayout だと、これらの問題を解決できる
  * レイアウトは再利用することができる
* MotionLayout
  * `app:layoutDescription` に `MotionScene` を定義したファイルを指定する
  * どの View を配置するかは定義するが、どこに配置するかなどは定義しない
* MotionScene
  * `<Transition>` タグを追加する
  * `motion:constraintSetStart`：初期位置のレイアウト
  * `motion:constraintSetEnd`：最終位置のレイアウト
  * `motion:duration`：アニメーション時間

#### OnSwipe handler

* `<Transition>` タグ以下に定義できる
* 指での動きとマッチさせた遷移を可能にする
* `touchAnchorId`：追跡すべき View の ID
* `touchAnchorSide`：View のどの端から指の動きを追跡するのか
* `dragDirection`：指の動きの方向

### Example 02 : self-contained MotionScene

* MotionLayout は直接 ConstraintSets を定義することができる
  * 1つのファイルで様々な ConstraintSets を維持することができる
  * 他の属性やカスタム属性をハンドリングして、機能を追加することができる
  * Android Studio の MotionEditor は、MotionScene ファイルのみをサポートする

#### Interpolated Attributes

* MotionScene ファイルの ConstraintSets で指定された属性は通常のレイアウト属性よりも多くをカバーしている
  * alpha
  * visibility
  * elevation
  * rotation, rotation[X / Y]
  * translation[X / Y / Z]
  * scaleX / Y
* `motion:constraintSetStart`, `motion:constraintSetEnd` に、MotionScene ファイル内で定義した `ConstraintSet` の ID を指定できる
  * `<ConstraintSet>` タグ内に定義する `<Constraint>` には、View のサイズや位置を定義する

### MotionLayout attributes for development

* `app:layoutDescription`：MotionScene のファイルを指定する
* `app:applyMotionScene`：MotionScene を適用するかどうか
* `app:showPaths`：モーションのパスを表示するかどうか。デバッグ用
* `app:progress`：遷移の進捗値を0から1で指定する
* `app:currentState`：現状の ConstraintSet を強制的に指定する

### The End?

* コードは https://github.com/googlesamples/android-ConstraintLayoutExamples

## [Introduction to MotionLayout (part II)](https://medium.com/google-developers/introduction-to-motionlayout-part-ii-a31acc084f59)

### Example 03 : Custom attribute

* 位置に関係しない attribute を指定することができる
* ConstraintSet にカスタム attribute 保持することができる
  * `<CustomAttribute>`
    * `attributeName`：指定した attribute 名の setter, getter を利用される(例：setBackgroundColor)
    * 値には、`customColorValue`, `customIntegerValue`, `customFloatValue`, `customStringValue`, `customDimension`, `customBoolean`
    * 初期状態と最終状態の ConstraintSet 両方に定義する必要がある

### Example 04 : ImageFilterView (1/2)

* `ImageFilterView` と呼ばれるユーティリティクラスが提供されている
* 2つの画像を cross-fade して表示を切り替えるサンプル
* `ImageFilterView` の使い方
  * `<MotionLayout>` 以下に定義
  * `app:altSrc` に別の画像を指定する
* `<MotionScene>` で、`<CustomAttribute>` を定義し、`attributeName` に `crossfade` を指定する
* `ImageFilterView` 内部実装
  * AppCompatImageView のサブクラス
  * 2枚の画像を LayerDrawable で管理
  * cross-fade するための、setCrossfade, getCrossfade が定義されている
  * 色を変更するための、setWarmth, getWarmth, setSaturation, getSaturation, setContrast, getContrast が定義されている

### Example 05 : ImageFilterView (2/2)

* `ImageFilterView` では以下の Custom Attribute で色を変更することができる
  * `saturation`：0 = grayscale, 1 = original, 2 = hyper saturated
  * `contrast`：1 = unchanged, 0 = gray, 2 = high contrast
  * `warmth`：1 = neutral, 2 = warm(red tint), 0.5 = cold(blue tint)

### Keyframes

* MotionLayout では、途中の状態(resting states) は ConstraintSets で実装される
  * 異なる画面サイズに正しく適用することができる
  * 本質的には MotionLayout は ConstraintLayout のように振る舞う
* 止まらずに通り過ぎるが、中間の状態を持ちたいケース
  * 2つ以上の ConstraintSets を定義することもできるが、Keyframes を使うやり方が簡単
  * 例えば、25% の移動量で赤色に変わって、50%の移動量で移動が開始されるなど

### Example 06 : Keyframe (1/2), position

* `KeyPosition` を指定することができる
  * `pathRelative`
  * `deltaRelative`
  * `screenRelative`
  * `<Transition>` 以下に `<KeyFrameSet>` を定義し、その中に定義する
* 移動量が 50% の位置で、画面サイズの 25% 分だけ y 座標の位置を変更している

### Example 07 : Keyframe (2/2), attribute

* `KeyAttribute` を使うことで、attribute の値を指定することもできる
  * サンプルでは、移動量が 50% の位置で、拡大率と回転を操作している
    * 拡大率を 2倍
    * 45度回転
  * `<KeyFrameSet>` 以下に定義する

### Conclusion

* カスタム attribute と keyframe の利点を示すサンプル

## [Introduction to MotionLayout (part III)](https://medium.com/google-developers/introduction-to-motionlayout-part-iii-47cd64d51a5)

* Part I & II では、MotionLayout の基本的な概念を説明
* Part III では既存のアプリでどのように MotionLayout を使うことができるのか、CoordinatorLayout, DrawerLayout, ViewPager と統合するのかについて説明

### Using MotionLayout with CoordinatorLayout

* (CoordinatorLayoutでサンプルの動作を実現できるが、サンプルのために MotionLayout で実装している)
* 既存のレイアウト、画面にモーションの追加をスクラッチから開発することなく可能
* サンプルでは、スクロールで Toolbar の高さが変わり、タイトルの位置が変わる
  * 一般的には、MotionLayout を使って、AppBarLayout 無いのToolbar の要素を置き換える
  * さらに、CoordinatorLayout にアニメーションさせる
  * setProgress を呼び出すことで MotionLayout の移動量をコントロールすることが可能
    * AppBarLayout のオフセットの変更を検知し、setProgress に通知する `MotionLayout` のサブクラスを作成する(CollapsibleToolbar)
* 実装方法
  * `CoordinatorLayout` は通常通りで、`AppBarLayout` 内に定義するコンテンツに `Toolbar` の代わりに `CollapsibleToolbar` を使ったレイアウトを指定する
  * `CollapsibleToolbar` では通常通りに View を配置する
  * `MotionScene` でどのようにアニメーションするのかを定義する
    * テキストの位置、背景色のアルファ値
    * `<Transition>` の `dragDirection` には `dragUp` を指定
  * どこを起点に回転するかは、レイアウト側に `transformPivotX`, `transformPivotY` で指定している

### Using MotionLayout with DrawableLayout

* `CoordinatorLayout` の時と同じように、`DrawableLayout` の移動量を `MotionLayout` に通知するために、`MotionLayout` のサブクラスを作成する必要がある(DrawerContent)
  * onDrawerSlide の slideOffset の値を setProgress で渡すだけ
* サンプルでは `DrawerLayout` 表示でコンテンツ側のサイズが変わり、`DrawerLayout` 内の View がアニメーションする
* 実装方法
  * `DrawerLayout` を定義するレイアウトファイルに、`DrawerContent` を定義する
  * `DrawerContent` にはドロワーに表示する View を定義する
  * `MotionScene` でどのようにアニメーションするのかを定義する
    * ドロワーメニューのテキストの回転、移動
    * コンテンツ側の拡大率、左側のマージン(メニューの幅)
  * コンテンツ側が暗くなるのは、`DrawerLayout` の動作で、`MotionScene` では何も指定していない

### Using MotionLayout with ViewPager

* `CoordinatorLayout`, `DrawerLayout` と同様に ViewPager の位置を `MotionLayout` に渡すために、`MotionLayout` のサブクラスを作成する(ViewpagerHeader)
  * `onPageScrolled` で移動量を setProgress で渡している
  * `progress = (position + positionOffset) / (numPages-1)`

### Using Lottie with MotionLayout

* Lottie のアニメーションを `MotionLayout` と使うことができる
* `LottieAnimationView` は `setProgress` を持っているので、`MotionScene` で指定する

### Conclusion

* 既存のレイアウトで `MotionLayout` を扱う方法を説明した

## [Defining motion paths in MotionLayout](https://medium.com/@camaelon/defining-motion-paths-in-motionlayout-6095b874d37)

### Introduction

* この記事では KeyFrames について詳細を説明する
* KeyFrames は特殊で一般的に利用されるものではない
* アニメーションの定義に以下のような定義が可能
  * Keyframes
  * Position Keyframes
  * Arc Motion
  * Easing
  * Attributes Keyframes
  * Cycle Keyframes & TimeCycle Keyframes
    * part V の記事で詳細が紹介される予定

### Keyframes : a Rendez-vous in Time

* Keyframes はある時間における状態を指定することができる
* いくつかの種類をサポートしている
  * KeyPosition
  * KeyAttribute
  * KeyCycle
  * KeyTimeCycle
* それぞれが独立した指定なのでいずれかのみを指定すればよい

#### Common Attributes

* Keyframes では共通の属性が提供される
  * motion:framePosition
    * 0 - 100
    * Keyframe をいつ適用するのか？
  * motion:target
    * Keyframe が影響を与える View
  * motion:transitionEasing
    * default : linear
    * 指定された時間での状態を計算するための easing curve
  * motion:curveFit
    * default : spline
    * 指定された時間での状態を計算するための interpolation curve
    * デフォルトの spline ではスムーズなアニメーションが実現される

### Position Keyframes

* もっとも利用することの多い属性でアニメーションのパスを変更することができる
  * 直線的なアニメーションを Keyframe を指定することで曲線的なアニメーションに変更できる
  * 複数の Keyframe を指定することで複雑なアニメーションを作成することも可能

#### Why Position Keyframes?

* ConstraintSets でフレキシブルな方法が提供されているにも関わらず、Keyframes が提供されているのにはいくつかの理由がある
  * Keyframes は一時的な状態を表し、ConstraintSet は休止状態を表す
  * Keyframes は ConstraintSet よりも軽量
  * Keyframes はアニメーションのパスの操作を可能とする
    * ？ConstraintSets は他の View に対する相対的な View の位置を指定する
* Note
  * MotionScene に複数の ConstraintSets を定義することが可能
  * 休止状態が複数存在する場合は、Keyframes の代わりに利用することができる
  * ただし、状態の変更はリスナ上で行うためコードから行う必要がある

#### XML Representation

* `<KeyFrameSet>`
  * `<Transition>` 内に定義される
* `<KeyPosition>`
  * `target`
  * `framePosition`
  * `keyPositionType`
  * `percentX`, `percentY`
    * 座標
    * `keyPositionType` によって意味合いが変わる

#### Different coordinate systems

* システムは解像度、画面の向き、言語などの変換を正しくハンドリングする
* このようなシステムで役立つ Keyframe の位置を指定するために固定の位置のみでなく指定された座標系(keyPositionType)で表現することができる
* `keyPositionType`
  * `parentRelative`
  * `deltaRelative`
  * `pathRelative`
* Note
  * keyPositionType は独立しているので、個別に位置を定義することが可能

##### parentRelative

* 親のコンテナからの相対位置で表現する
  * 左下が (0,0)
  * 右上が (1,1)
* 一般的には大きなモーションが必要な時に利用する
* 移動する View の開始位置と終了位置ではなく、親のサイズだけを元にしているので、最適ではない位置で終わる場合がある

##### deltaRelative

* 開始と終了位置を元に定義され、開始位置と終了位置の距離
  * 開始位置が (0,0)
  * 終了位置が (1,1)
* `parentRelative` と同じように直感的な座標系
  * ウィジェットを水平または垂直の動きで開始または終了させたい場合に便利
* 開始位置と終了位置の距離で定義されるので、距離が小さい場合は、Keyframe が影響しない場合がある
  * 例えば開始と終了が同じ高さの場合に `percentY` をしても効果はない

##### pathRelative

* 開始と終了との直線経路に対する相対的な位置として表現する
  * 開始位置が (0,0)
  * 終了位置が (1,0)
* マイナス値もサポートされている
* `deltaRelative` で表現できない場合に利用する
* 特殊な座標系だが、時には役立つ
  * S のような曲線の表現など

### Arc Motion

* マテリアルデザインでは一般的に利用される
* Keyframes を指定して実現するのは1つのやり方
* ConstraintLayout 2.0.0-alpha2 では、もっと簡単に実現する方法がある
  * `motion:pathMotionArc`
    * 開始位置の ConstraintSet に指定すると、Arc Motion になる
  * `startHorizontal`
    * 最初に水平方向に動いて Arc Motion を実現する
  * `startVertical`
    * 最初に垂直方向に動いて Arc Motion を実現する
  * 合わせて KeyPosition を利用して複雑なアニメーションを作成することもできる
    * `flip`
      * KeyPosition で指定した位置から Arc Motion の方向を変更する(S 字のような曲線)
    * `none`
      * KeyPosition で指定した位置から Arc Motion を無効化する

### Easing

* アニメーションはパスだけではなくタイミングも重要
* KeyPosition では移動速度などを変更することはできるが、開始、終了位置については変更できない
  * これを変更するのが、`motion:transitionEasing`
    * ConstraintSets か KeyFrame に適用する
      * cubic : (x1,y1,x2,y2)
      * standard, accelerate, decelerate
        * マテリアルデザインの定義に類似している

#### Standard easing

* 一般的に非タッチ駆動のアニメーションで利用される
* 開始、終了位置を停止させるのに有効

#### Accelerate Easing

* View を画面外に移動するときに利用する

#### Decelerate Easing

* View を画面外から移動するときに利用する

### KeyAttribute
