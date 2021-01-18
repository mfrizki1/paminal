package id.calocallo.sicape.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SkhdOnRpsModel(
    var id: Int?,
    var no_skhd: String?,
    var jenis_skhd: String?,
    var hukuman: String?,
    var tanggal_penetapan: String?
) : Parcelable {
    constructor() : this(null, null, null, null, null)
}