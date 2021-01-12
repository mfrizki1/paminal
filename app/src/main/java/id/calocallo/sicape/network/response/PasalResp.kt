package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PasalResp(
    var id: Int?,
    var id_pasal: Int?,
    var nama_pasal: String?,
    var isi_pasal: String?
) : Parcelable {
    constructor() : this(null, null, "", "")
}