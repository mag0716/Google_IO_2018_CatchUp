# Architecture Components

## [Android Jetpack: what's new in Architecture Components (Google I/O '18)
](https://www.youtube.com/watch?v=pErTyQpA390)

## Lifecycles

* Activity, Fragment のライフサイクルは分かりにくい
* Optional -> Fundamental
  * AppCompatActivity, Fragment に実装されている
* Community adoption
   * AutoDispose(Uber)
* Fragment view's lifecycle
  * onCreate
    * Pro:One time setup
    * Con:Fails recreate
  * onCreateView
    * Pro:Handles recreate
    * Con:Double registration
  * 解決策：viewLifecycleOwner
    * しかも解放は不要

## LiveData

* DataBinding support LiveData
  * binding.setLifecycleOwner が必要
  * V2 にする必要がある

## Room

* Better multithreading
  * 1.0 では別スレッドで書き込みが実行されていたら、完了まで待機されていた
* WriteAheadLogging
* @Query
  * 動的に SQL を作るのが難しい
    * 解決策：@RawQuery
      * SimpleSQLiteQuery

## ViewModels

## Paging

* Lazy loading for RecyclerView
* Goal
  * Allow efficient paging with minimal complexity
* LivePagedListBuilder
* PagedListAdapter
  * submitList
* RxJava support
  * RxPagedListBuilder
* feature
  * From database
  * From network
  * From database + network

## Navigation

No more Fragment Transitions

## WorkManager

* 実行前に DB に保存される

## Next

* Architecture Components by default
  * Lifecycles, ViewModel, LiveData は必須
  * Room, Navigation, WorkManager　は推奨
  * Paging は特定のケースのみ
