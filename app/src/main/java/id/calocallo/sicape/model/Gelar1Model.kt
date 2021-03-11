package id.calocallo.sicape.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Gelar1Model(
    var dugaan: String?,
    var dasar: String?,
    var tanggal: String?,
    var pukul: String?,
    var waktu_mulai: String?,
    var waktu_selesai: String?,
    var tempat: String?,
    var nama_pimpinan: String?,
    var nrp_pimpinan: String?,
    var pangkat_pemapar: String?,
    var nama_pemapar: String?,
    var kronologis_kasus: String?,
    var no_surat_perintah_penyidikan: String?,
    var nama_notulen: String?,
    var pangkat_notulen: String?,
    var nrp_notulen: String?
) : Parcelable {
    constructor() : this(
        null, null, null,
        null,
        null,
        null, null, null, null, null, null, null, null, null, null, null
    )
}