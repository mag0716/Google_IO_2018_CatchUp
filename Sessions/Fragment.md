# Fragment

[Android Jetpack: how to smartly use Fragments in your UI (Google I/O '18)
](https://www.youtube.com/watch?v=WVPH48lUzGY)

## Factoring Activities

* Move loosely related code to separate fragments
* Factoring Fragments
  * Fragments も分離する必要がある

## Architecture Components

* LifecycleObservers
* ViewModel
* Navigation

## Does a child fragmemnt's onCreate run before or after its parent?

* Is the parent an Activity or Fragment? What version?

## Lifecycle

* LIFO callback overriding
* Created by you, not recreated via reflection
* No stateful restoration by the system

## Retained Instance Fragment

* Save handles to expensive data/operations
* Reconnect to those operations after config change
* No UI
-> ViewModels

## ViewModels

* Instances created by factory you provide
* LiveData allows easy(lifecycle-aware) reconnection
* Replace retained instance Fragments

## Fragment Transactions

* default async
  * No reentrant fragment operations
* Drawbacks
  * Observed state does not reflect queued transactions

## Navigation

* Works well with Fragments
* Replace back stack transactions

## Why Fragments

* android.widget -> Mechanism
  * Shows state to the user
  * Reports user interaction events
* android.app -> Policy
  * Defines state to bind windows
  * Responds to user interaction and issues change to model

## Tastes great with Navigation

* Single-Activity apps
* Common app chrome, decoupled destinations
* Transactions and animated managed by Navigation, not by hand
* Can inflate sub-components to help

## DialogFragment

* Interation with another
  * Floating UI
  * System interaction
* Leverage instance state restoration
* Dialogs, bottom sheets
* Transient UIs you don't want to lose

## Options Menu

## Testing Fragments

* FragmentController
* dispatchCreate など

## Loaders

* Rebuilt on top of LiveData and ViewModels

## Where are we going

* Separate desired behavior from incidental behavior
* APIs
  * LoaderManager or LiveData
