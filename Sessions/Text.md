# Text

## [Best practices for text on Android (Google I/O '18)](https://www.youtube.com/watch?v=x-FcOX6ErdI)

* Java
  * TextView
  * Layout, Paint, Canvas
* Native(C++)
  * Minikin
    * measurement, line breaking, hyphnation
  * ICU
  * HarfBuzz
  * FreeType
  * Skia

* Text Measurement
* Line breaking
  * simple
  * balanced
  * hihy_quality
* Hyphenation
  * none
  * normal
  * full
* Line breaking, Hyphenation にコストがかかっている
* setTextLocal
  * LocaleSpan

* Styling text
  * Spans
    * Character spans
    * Paragraph spans
    * BulletSpan
  * SpannableString vs SpannableStringBuilder
    * Span が 250 以上だったら、SpannableStringBuilder の方がパフォーマンスがいい

* Styling internationalized test

12:54 まで
