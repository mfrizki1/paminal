package id.calocallo.sicape.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SkttOnSp3(
    var id: Int?,
    var no_sktt: String?
) : Parcelable {
    constructor() : this(null, null)
}