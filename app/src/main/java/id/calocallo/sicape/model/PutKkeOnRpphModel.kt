package id.calocallo.sicape.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PutKkeOnRpphModel(
    var id: Int?,
    var no_putkke: String?,
    var tanggal_putusan: String?,
    var sanksi_hasil_keputusan: String?
) : Parcelable {
    constructor() : this(null, null, null,null)
}