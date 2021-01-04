package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RefPenyelidikanResp(
    var id: Int?,
    var id_lp: Int?,
    var no_lp: String?
):Parcelable {
    constructor() : this(0, 0, "")
}