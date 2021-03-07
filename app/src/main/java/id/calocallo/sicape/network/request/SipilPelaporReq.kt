package id.calocallo.sicape.network.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SipilPelaporReq(
    var nama_sipil: String?,
    var agama_sipil: String?,
    var pekerjaan_sipil: String?,
    var kewarganegaraan_sipil: String?,
    var tempat_lahir_pelapor: String?,
    var tanggal_lahir_pelapor: String?,
    var alamat_sipil: String?,
    var jenis_kelamin: String?,
    var no_telp_sipil: String?,
    var nik_sipil: String?
) : Parcelable {
    constructor() : this(null, null, null, null, null, null, null, null, null, null)
}