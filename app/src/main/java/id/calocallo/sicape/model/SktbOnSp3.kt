package id.calocallo.sicape.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SktbOnSp3(
    var id: Int?,
    var no_sktb: String?
) : Parcelable {
    constructor() : this(null, null)
}