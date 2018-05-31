# Android development

## [Modern Android development: Android Jetpack, Kotlin, and more (Google I/O 2018)](https://www.youtube.com/watch?v=IrMw7MEgADk)

* Layout Inspector
* Systrace
  * Profiler
* Memory tracking
* Layout design
  * ConstraintLayout
* Dalvik
  * ART
    * enum を使うべきではないという制約を気にしなくていい
* Java
  * Kotlin
    * KTX
    * Style guide
    * Interop guide
* Layouts
  * AbsoluteLayout -> deprecated
  * LinearLayout -> Simple cases only
  * frameLayout -> OK
  * GridLayout -> better with tools
  * RelativeLayout -> ConstraintLayout 2.0
* AdapterViews
  * RecyclerView
* Fragments
  * support library version
* Activities
  * 可能な限り single Activity
  * Fragment は必須ではない
* Architecture
  * Architecture Components
* Android Lifecycle
  * Architecture Components Lifecycle
* Views and Data
  * Architecture Components ViewModel
* Data
  * Room
* Data Paging
  * Architecture Components Data Paging
* Graphics
  * Open GL ES 3.1/3.2 Vulkan
  * Hardware acceoerated redering
  * ViewDrawable
  * ライブラリ推奨(Glide, Picasso, Lottie)
* Best Codeing Practices
  * Profile your code
  * Avoid work when possible
  * Minimize memory consumption

## [Protips: a fresh look at advanced topics for Android experts (Google I/O '18)
  ](https://www.youtube.com/watch?v=eHjHlujp3Tg)

* prioritize efficiency
* Avoid WakeLocks
* Don't use undocumented APIs
* Store strings and values as resources
* Avoid transmitting / storing contacts & location

* SyncAdapter
  * -> JobScheduler
  * -> WorkManager
* Headless Fragments
  * -> retained Fragment + Loader
  * -> ViewModel
* ContentProvider
  * -> Room
* Location Services
  * LocationServices#getFusedLocationProviderClient
  * -> Oreo : add restriction
    * 1時間に数回しか更新されない
      * Foreground Service
      * Geofences
        * Awareness
* Controlling Media
  * -> ExoPlayer
