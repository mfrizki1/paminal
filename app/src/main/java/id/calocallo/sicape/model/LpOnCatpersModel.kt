package id.calocallo.sicape.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpOnCatpersModel(
    var id: Int?,
    var no_lp: String?,
    var tanggal_buat_laporan: String?
) : Parcelable {
    constructor() : this(null, null, null)
}