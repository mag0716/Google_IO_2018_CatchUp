# Proguard

## [Effective ProGuard keep rules for smaller applications (Google I/O '18)](https://www.youtube.com/watch?v=x9T5EYE-QWQ)

* アプリのサイズが小さければ、起動も早くなる
* アプリサイズは平均で 32MB
* APK Analyzer で計測
  * Guava が 1.4MB も使っていた
  * AndroidPlot は 180KB
* Proguard + R8
  * `minifyEnabled`
  * 難読化後のビルドエラーの対応
    * `-dontwarn *`
      * これで解決はするけど
* shirnk
  * R8 は AndroidManifest に定義されたクラスは解析し、難読化しないようにする
  * レイアウトリソースに定義された View についても同様
  * リソースファイルについては解析できない
  * アノテーションクラスについても解析できない
  * リフレクションで使うものについては、難読化を無視するようにする必要がある
  * クラス、メソッド名を使っているものにも注意
    * -keep, allowshirinking
    * -keepclassmembers
      * getter, setter のみ無効化する
  * 1MB まで減った
    * Guava, AndroidPlot とも 100KB 程度まで減った
* まとめ
  * Write keep rules alongside with corresponding code
  * Add structure to ease describing reflection
  * Continuously test optimized build
* ライブラリを作る際は
  * Make rules as precise as you can
  * Publish on homepage and add to README
  * Consider using consumerProugurdFiles when building AARs
