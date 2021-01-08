package id.calocallo.sicape.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpOnSkhd(
    var id: Int?,
    var no_lp: String?,
    var jenis_pelanggaran: String?
) : Parcelable {
    constructor() : this(null, null,null)

}