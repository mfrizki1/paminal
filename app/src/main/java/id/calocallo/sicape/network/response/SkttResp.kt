package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.LhpOnSkhd
import id.calocallo.sicape.model.LpOnSkhd
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SkttResp(
    var id: Int?,
    var lhp: LhpOnSkhd?,
    var lp: LpOnSkhd?,
    var no_sktt: String?,
    var menimbang: String?,
    var mengingat_p5: String?,
    var tanggal_penetapan: String?,
    var kota_penetapan: String?,
    var jabatan_yang_menetapkan: String?,
    var nama_yang_menetapkan: String?,
    var pangkat_yang_menetapkan: String?,
    var nrp_yang_menetapkan: String?,
    var tembusan: String?,
    var created_at: String?,
    var updated_at: String?
) : Parcelable{
    constructor():this(
        null,null,null,null,null,null,null,
        null,null,null,null,
        null,null,null,null
    )
}