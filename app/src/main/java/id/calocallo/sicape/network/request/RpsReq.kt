package id.calocallo.sicape.network.request

import android.os.Parcelable
import id.calocallo.sicape.model.SkhdOnRpsModel
import kotlinx.android.parcel.Parcelize

data class RpsReq(
    var id_skhd: Int?,
    var no_rps: String?,
    var dasar_pe: String?,
    var isi_rekomendasi: String?,
    var tanggal_penetapan: String?,
    var kota_penetapan: String?,
    var jabatan_yang_menetapkan: String?,
    var nama_yang_menetapkan: String?,
    var pangkat_yang_menetapkan: String?,
    var nrp_yang_menetapkan: String?,
    var tembusan: String?
) {
    constructor() : this(
        null, null, null, null,
        null, null, null,
        null, null, null,
        null
    )
}