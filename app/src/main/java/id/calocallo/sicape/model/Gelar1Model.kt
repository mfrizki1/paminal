package id.calocallo.sicape.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Gelar1Model(
    var tentang: String?,
    var dasar_dasar: String?,
    var tanggal: String?,
    var pukul: String?,
    var tempat: String?,
    var uraian: String?,
    var hari: String?,
    var id_pemapar: Int?,
    var id_peserta: ArrayList<Int>?,
    var id_pimpinan: Int?
) : Parcelable {
    constructor() : this(null, null, null, null, null, null, null, null, null, null)
}