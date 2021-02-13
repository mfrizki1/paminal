package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SatKerResp(
    var id: Int?,
    var kesatuan: String?,
    var alamat_kantor: String?,
    var no_telp_kantor: String?,
    var jenis: String?
) : Parcelable {
    constructor() : this(null, null,null,null,null)
}