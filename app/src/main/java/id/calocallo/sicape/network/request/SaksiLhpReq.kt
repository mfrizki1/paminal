package id.calocallo.sicape.network.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SaksiLhpReq(
    var status_saksi: String?,
    var id_personel: Int? = null,
    var nama: String?,
    var tempat_lahir: String?,
    var tanggal_lahir: String?,
    var pekerjaan: String?,
    var alamat: String?,
    var is_korban: Int?,
    var isi_keterangan_saksi: String?
) : Parcelable {
    constructor() : this(
        null, null, null, null, null, null, null, null, null
    )
}