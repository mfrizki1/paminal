package id.calocallo.sicape.network.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KetTerlaporReq(
    var id_personel: Int?,
    var isi_keterangan_terlapor: String?,
    var nama_pesonel: String?
):Parcelable {
    constructor() : this(0, "", "")
}