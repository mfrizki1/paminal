package id.calocallo.sicape.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpOnWanjakModel(
    var id: String?,
    var no_lp: String?,
    var jenis_pelanggaran: String?,
    var uraian_pelanggaran: String?,
    var tanggal_buat_laporan: String?
) : Parcelable {
    constructor() : this(
        null, null, null, null,
        null
    )
}