package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.PutKkeOnRpphModel
import id.calocallo.sicape.model.SkhdOnRpsModel
import id.calocallo.sicape.model.SktbOnSp3
import id.calocallo.sicape.model.SkttOnSp3
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sp3Resp(
    var id: Int?,
    var sktb: SktbOnSp3?,
    var sktt: SkttOnSp3?,
    var no_sp4: String?,
    var mengingat_p4: String?,
    var mengingat_p5: String?,
    var menetapkan_p1: String?,
    var kota_keluar: String?,
    var tanggal_keluar: String?,
    var tembusan: String?,
    var nama_akreditor: String?,
    var pangkat_akreditor: String?,
    var nrp_akreditor: String?,
    var created_at: String?,
    var updated_at: String?

) : Parcelable {
    constructor() : this(
        null, null, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null
    )
}