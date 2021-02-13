package id.calocallo.sicape.network.request

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PekerjaanODinasReq(
    var pekerjaan: String?,
    var instansi: String?,
//    var thnLama: String?,
    var tahun_awal: String?,
    var tahun_akhir: String?,
    var dalam_rangka: String?,
    var keterangan: String?
) : Parcelable {
    constructor() : this(null, null, null, null, null, null)
}