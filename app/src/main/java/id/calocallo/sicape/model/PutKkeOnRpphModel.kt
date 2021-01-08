package id.calocallo.sicape.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PutKkeOnRpphModel(
    var id: Int?,
    var no_putkke: String?
) : Parcelable {
    constructor() : this(null, null)
}