package com.github.mag0716.sharedelementtransition

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(val name: String, val color: Int) : Parcelable