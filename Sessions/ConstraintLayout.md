# ConstraintLayout

[What's new with ConstraintLayout and Android Studio design tools (Google I/O '18)](https://www.youtube.com/watch?v=ytZteMo4ETk)

## Layout Editor

* Conversion
* include した layout に簡単に遷移できるようになった

## Sample Data

* tools:showIn ： 利用されるレイアウトを指定して、プレビューに表示することができる
* sample data に dimensions も指定できる
* 定義済みの Sample Data も用意されている
  * tools:sample
    * first_names
    * us_cities
    * etc
  * UI 上から選択できる
    * browse ボタン

## ConstraintLayout 2.0

* ConstraintHelper
  * フラットな階層のまま、View に対するリファレンスを保持する
  * View は複数の helper から参照されることも可能
  * [？]基本的には、タグ付けと動作の設定を許可する
  * Concept
    * Layout Manipulation
    * Post-Layout Manipulation
    * Rendering or Decorating
* Linear
  * app:constraint_referenced_ids
* Flow
  * FlexboxLayout like
* Post-Layout
  * Layers
* Circular Reveal
* Decorators
  * Lava Decorator
    * FAB タップで複数のメニューが表示されるアニメーション
  * Layer
    * 枠線がある ViewGroup に複数の View が配置されるレイアウト
* Bottom Panel
* 組み合わせた例
  * レイアウト内に Decorator を配置している
* Layout Management
  * <ConstraintLayoutStates> で xml で定義できるようになった
  * サイズも指定できるので、サイズが変わったらレイアウトを変えるような定義もできる
* Constraint Layout 2.0
  * MotionLayout
    * ConstraintLayotu のサブクラス
    * MotionScene
      * ConstraintSet に CustomAttribute を設定できる
      * <KeyFrames>
  * Motion Editor
    * Create start Layout
    * Create end Layout
    * Enjoy the animation
    * Add KeyFrames
    * Repeat

## Codelabs

* /chromeos-resizing
* /android-navigation
