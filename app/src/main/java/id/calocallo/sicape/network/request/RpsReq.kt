package id.calocallo.sicape.network.request

import android.os.Parcelable
import id.calocallo.sicape.model.SkhdOnRpsModel
import kotlinx.android.parcel.Parcelize

data class RpsReq(
    var id_lp: Int?,/*ADD*/
    var no_rps: String?,/*ADD*/
    var nama_dinas: String?,
    var no_nota_dinas: String?,
    var tanggal_nota_dinas: String?,
    var kota_penetapan: String?,
    var tanggal_penetapan: String?,
    var nama_yang_mengetahui: String?,
    var pangkat_yang_mengetahui: String?,
    var nrp_yang_mengetahui: String?,
    var tembusan: String?
) {
    constructor() : this(
        null, null, null, null, null,
        null, null, null,
        null, null, null
    )
}