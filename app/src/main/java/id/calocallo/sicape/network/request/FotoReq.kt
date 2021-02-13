package id.calocallo.sicape.network.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FotoReq(
    var id_foto_kanan: Int?,
    var id_foto_muka: Int?,
    var id_foto_kiri: Int?
) : Parcelable {
    constructor():this(null, null, null)
}