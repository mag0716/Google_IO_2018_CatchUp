# Support Library

## [Android Jetpack: what’s new in Android Support Library (Google I/O 2018)
](https://www.youtube.com/watch?v=jdKUm8tGogw)

* Finer-grained artifacts
  * support-core-ui
  * support-compat
  * support-core-utils
* Refined versioning
  * 28.0.0 -> 1.0.0
  * Per-artifact versioning & release
* Updated packaging
  * androidx.<feature>:<feature>-<sub-feature>
  * -v7, -v4 の様な名前は削除される
* Jetifier
  * third-party のライブラリをマイグレーションするツール
* What's new 28.0.0-alpha1
  * RecyclerView Selection
    * state_activeted で選択時の状態を指定する
  * RecyclerView ListAdapter
    * Operates on imuutable lists
    * DiffUtil を簡単に使える
    * AsyncListDiffer
  * androidx.webkit
    * WebView apk が使われ、API 21+
  * Browser Library
    * Custom tabs support
  * Browser actions
    * Chrome v66+
  * HeifWriter
    * API 28+ で backport は準備中
* Slices
  * Templated
  * Interactive
    * inline actions, deep-links, sliders, toggles なおが使える
  * Updatable
    * Live data
  * API 19+
  * AndroidManifest に <provider> タグを追加
  * SliceProvider を実装したクラスを作成し、onBindSlice を実装する
  * ListBuilder, GridRowBuilder などを使って UI を作成する
* Material components
  * color, type, shape がカスタマイズできるようになった
  * 新しい Components が追加された
  * Animation が更新された
  * Theme.MaterialComponents.Light
  * TextInputLayout, TextInputEditText
  * Matterial Button
  * BottomAppBar
  * Bottom Navigation
  * MaterialCardView
