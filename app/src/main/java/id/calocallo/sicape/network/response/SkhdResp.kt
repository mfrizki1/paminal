package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.LhpOnSkhd
import id.calocallo.sicape.model.LpOnSkhd
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SkhdResp(
    var id: Int?,/**/
    var bidang: String?,/**/
    var no_skhd: String?,/**/
    var lp: LpResp?,/**/
    var no_berkas_perkara: String?,/*!*/
    var tanggal_buat_berkas_perkara: String?,/*!*/
    var tanggal_sidang_disiplin: String?,/*!*/
    var hukuman: String?,/**/
    var tanggal_disampaikan_ke_terhukum: String?,/**/
    var waktu_disampaikan_ke_terhukum: String?,/**/
    var kota_penetapan: String?,/**/
    var tanggal_penetapan: String?,/**/
    var jabatan_yang_menetapkan: String?,/**/
    var kesatuan_yang_menetapkan: String?,/*!*/
    var nama_yang_menetapkan: String?,/**/
    var pangkat_yang_menetapkan: String?,/**/
    var nrp_yang_menetapkan: String?,/**/
    var tembusan: String?,/**/
    var is_ada_dokumen: Int?,/*!*/
    var dokumen: DokResp?,/*!*/
    var user_creator: UserResp?,
    var user_updater: UserResp?,
    var user_deleter: UserResp?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?

    /*var lhp: LhpOnSkhd?,
    var menimbang_p2: String?,
    var memperlihatkan: String?,*/
) : Parcelable {
    constructor() : this(
        null, null, null, null, null,
        null, null, null, null,
        null, null, null, null, null,
        null, null, null, null, null, null,
        null, null, null, null, null, null
    )
}
@Parcelize
data class AddSkhdResp(
    var skhd: SkhdResp?
):Parcelable