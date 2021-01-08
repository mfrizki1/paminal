package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.LhpOnSkhd
import id.calocallo.sicape.model.LpOnSkhd
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SkhdResp(
    var id: Int?,
    var lhp: LhpOnSkhd?,
    var lp: LpOnSkhd?,
    var bidang: String?,
    var no_skhd: String?,
    var menimbang_p2: String?,
    var memperlihatkan: String?,
    var hukuman: String?,
    var tanggal_disampaikan_ke_terhukum: String?,
    var waktu_disampaikan_ke_terhukum: String?,
    var tanggal_penetapan: String?,
    var kota_penetapan: String?,
    var jabatan_yang_menetapkan: String?,
    var nama_yang_menetapkan: String?,
    var pangkat_yang_menetapkan: String?,
    var nrp_yang_menetapkan: String?,
    var tembusan: String?,
    var created_at: String?,
    var updated_at: String?
) : Parcelable {
    constructor() : this(
        null, LhpOnSkhd(), LpOnSkhd(), "", "", "", "", "", "",
        "", "", "", "", "",
        "", "", "", "", ""
    )
}