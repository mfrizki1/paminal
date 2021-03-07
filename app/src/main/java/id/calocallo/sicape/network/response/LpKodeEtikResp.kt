package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpKodeEtikResp(
    var id: Int?,
    var no_lp: String?,
    var listPasalDilanggar: ArrayList<PasalDilanggarResp>?
):Parcelable{
    constructor():this(0,"",ArrayList())
}