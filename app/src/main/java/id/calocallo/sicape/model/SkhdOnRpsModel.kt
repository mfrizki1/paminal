package id.calocallo.sicape.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SkhdOnRpsModel(
    var id: Int?,
    var no_skhd: String?
) : Parcelable{
    constructor():this(null,null)
}