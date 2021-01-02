package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpKodeEtikResp(
    var id: Int?,
    var no_lp: String?,
    var listPasal: ArrayList<LpPasalResp>?
):Parcelable{
    constructor():this(0,"",ArrayList())
}