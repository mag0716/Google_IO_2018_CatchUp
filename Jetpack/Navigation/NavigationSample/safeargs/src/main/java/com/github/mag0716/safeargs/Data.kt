package com.github.mag0716.safeargs

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(val id: Int, val name: String) : Parcelable