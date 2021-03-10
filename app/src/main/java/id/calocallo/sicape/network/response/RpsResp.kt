package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.SkhdOnRpsModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RpsResp(
    var lp: LpMinResp?,
    var nama_dinas: String?,
    var no_nota_dinas: String?,
    var tanggal_nota_dinas: String?,
    var is_ada_dokumen: Int?,
    var dokumen: DokResp?,
    var user_creator: UserResp?,
    var user_updater: UserResp?,
    var user_deleter: UserResp?,

    var id: Int?,
    var no_rps: String?,
    var isi_rekomendasi: String?,
    var tanggal_penetapan: String?,
    var kota_penetapan: String?,
    var jabatan_yang_menetapkan: String?,
    var nama_kabid_propam: String?,
    var pangkat_kabid_propam: String?,
    var nrp_kabid_propam: String?,
    var tembusan: String?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?
) : Parcelable {
    constructor() : this(
        null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null,
        null, null, null, null
    )
}
