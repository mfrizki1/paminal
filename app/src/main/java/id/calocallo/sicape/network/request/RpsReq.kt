package id.calocallo.sicape.network.request

import android.os.Parcelable
import id.calocallo.sicape.model.SkhdOnRpsModel
import kotlinx.android.parcel.Parcelize

data class RpsReq(
    var id_lp: String?,/*ADD*/
    var nama_dinas: String?,
    var no_nota_dinas: String?,
    var tanggal_nota_dinas: String?,
    var kota_penetapan: String?,
    var tanggal_penetapan: String?,
    var nama_kabid_propam: String?,
    var pangkat_kabid_propam: String?,
    var nrp_kabid_propam: String?,
    var tembusan: String?

) {
    constructor() : this(
        null, null, null, null,
        null, null, null,
        null, null, null
    )
}