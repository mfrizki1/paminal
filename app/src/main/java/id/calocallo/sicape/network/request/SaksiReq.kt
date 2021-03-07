package id.calocallo.sicape.network.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SaksiReq (
    var nama: String?,
    var tempat_lahir: String?,
    var tanggal_lahir: String?,
    var pekerjaan: String?,
    var alamat: String?,
    var is_korban: Int?
) : Parcelable {
    constructor():this(null,null,null,null,null,null)
}