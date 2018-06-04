# Paging

## [Android Jetpack: manage infinite lists with RecyclerView and Paging (Google I/O '18)
](https://www.youtube.com/watch?v=BE5bsyGGLf4)

* Server -> DB -> UI
* without Paging
  * AsyncListDiffer
  * Support Library 27.1
  * DiffUtil#ItemCallback を実装する
* with Paging
  * PagedList
    * LiveData で PagedList を返却する様にする
  * DataSourceFactory, DataSource
    * Room の Dao で戻り値の型に使用する
  * PagedListAdapter
    * DiffUtil#ItemCallback の実装はそのまま
  * Placeholders
    * 未取得なデータのセルを表示した場合にプレイスホルダーを表示する
    * デフォルトは true
    * Adapter は null を扱う必要がある
  * RxJava
    * RxPagedListBuilder
* DataSource
  * PositionalDataSource
    * invalidate されたら、すでに読み込み済みのデータは破棄される
  * ItemKeyedDataSource
    * Key タイプと、データタイプを指定する
    * invalidate されたら、現在表示されているセルのデータのみ取得され、取得済みのデータは破棄される
  * PagedKeyedDataSource
    * サーバとの通信時に利用する
    * nextPage, prevPage に API の URL を渡す
    * 前の Page の key が使われ、次の Page のデータが取得される
    * invalidate されると、初回の key がないので、index が 0 のデータが取得され、スクロール位置は破棄される
* Database + Network
  * BoundaryCallback
    * onItemAtEndLoaded
    * LivePagedListBuilder に set する
* Cursor で使うのは間違っている
