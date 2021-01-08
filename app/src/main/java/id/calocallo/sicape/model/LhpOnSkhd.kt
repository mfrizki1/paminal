package id.calocallo.sicape.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LhpOnSkhd (
    var id: Int?,
    var no_lhp: String?
):Parcelable{
    constructor():this(null, null)
}