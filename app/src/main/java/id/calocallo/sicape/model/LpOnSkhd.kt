package id.calocallo.sicape.model

import android.os.Parcelable
import id.calocallo.sicape.network.response.LpMinResp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpOnSkhd(
    var id: Int?,
    var id_lhp: Int?,
    var lp: LpMinResp?
) : Parcelable {
    constructor() : this(null, null,null)

}