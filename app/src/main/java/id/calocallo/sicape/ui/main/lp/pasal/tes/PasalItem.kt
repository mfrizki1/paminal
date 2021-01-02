package com.zanyastudios.test

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PasalItem(
    var id: Int?,
    var nama_pasal: String?,
    var detail_pasal: String?

) : Parcelable