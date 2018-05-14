# Frictionless Android testing: write once, run everywhere (Google I/O '18)

[Frictionless Android testing: write once, run everywhere (Google I/O '18)
](https://www.youtube.com/watch?v=wYMIadv9iF8)

* e2e Tests?
* given, when, then
* mockito
* Robolectric
* Espresso

## Android Test

* Jetpack の一部
* Includes existing libraries
* New APIs and Kotlin
* Available on/off DevicePolicyManager
* Open source

* Given(NEW)
  * `buildMotionEvent()setAction(MotionEvent.ACTION_DOWN)`
  * デバイスがなくても使える
* Then(NEW)
  * fluent assertion
  * `assertThat(view).isVisible()`

## Project Nitrogen

* A single entry point for tests
* Setup
  * On-device, Off-device, Cloud
  * Text data
  * Fixture Scripts
  * Android Test Orchestrator
  * Test Services
* Execute
  * Collection
  * Isolation
  * Orchestration
* Report
  * Unified Format
  * Output data
  * Scoping

* Firebase Test Lab
* Google Cloud
* Real Device, Virtual Device
* Simulated Device(Robolectric)
* custom(in-house device Lab)

* 今年中に Open Source になるらしい

## Resource

* code-labs.io/codelabs/android-testing
* code-labs.io/codelabs/bazel-android-intro(NEW)
* github.com/googlesamples/android-testing
* developer.android.com/testing
