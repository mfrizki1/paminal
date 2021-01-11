package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpCustomResp(
    var id: Int?,
    var no_lp: String?,
    var jenis_pelanggaran: String?
) : Parcelable {
    constructor() : this(null, null, null)
}