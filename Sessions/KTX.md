# KTX

[Android Jetpack: sweetening Kotlin development with Android KTX (Google I/O '18)](https://www.youtube.com/watch?v=st1XVfkDWqk)

* ViewGroup#forEachIndexed
  * lambda
  * zero overhead abstraction
* systemService
  * reified
* updatePadding
  * 引数にラベルが指定されている
* Rect
  * operator, componentN
  * 分解宣言
* isDigitsOnly
* Color
  * plus operator
  * 1.0 から ColorUtils.compositeColors が利用される

* core-ktx -> core -> Android framework
* fragment-ktx -> fragment
* palette-ktx -> palette
* etc

## KTX Principles

* [OK]Adapt existing functionality and redirect features upstream
* [OK]Default to inline unless code size or allocation is prohibitive
* [OK]Leverage features unique to Kotlin
* [NG]Code golf APIs to be as short as possible
* [NG]Optimize for single and/or specific use case

## fragment KTX

* transaction1

## Building Kotlin-friendly libraries

* Port public API or entire library to Kotlin
* Ship sibling artifact with Kotlin extensions
  * どのように展開されるのか？
* KEEP-110 annotations
  * @ExtensionFunctioin
  * @ExtensionProperty
  * @DefaultValue
  * @KtName
